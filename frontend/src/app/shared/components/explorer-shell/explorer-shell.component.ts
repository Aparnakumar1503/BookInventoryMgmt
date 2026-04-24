import { Component, Input, OnInit, computed, inject, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule, UntypedFormGroup, Validators } from '@angular/forms';
import { BaseApiService } from '../../../core/services/base-api.service';
import { NotificationService } from '../../../core/services/notification.service';
import { RequestResult } from '../../../core/models/api-response.model';
import { ExplorerEndpoint, ExplorerField, ExplorerModule } from '../../../core/models/explorer.model';
import { DataTableComponent } from '../data-table/data-table.component';
import { MethodPillComponent } from '../method-pill/method-pill.component';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { StatCardComponent } from '../stat-card/stat-card.component';
import { StatusIndicatorComponent } from '../status-indicator/status-indicator.component';

@Component({
  selector: 'app-explorer-shell',
  imports: [
    ReactiveFormsModule,
    SidebarComponent,
    MethodPillComponent,
    StatusIndicatorComponent,
    StatCardComponent,
    DataTableComponent
  ],
  templateUrl: './explorer-shell.component.html',
  styleUrl: './explorer-shell.component.scss'
})
export class ExplorerShellComponent implements OnInit {
  private readonly apiService = inject(BaseApiService);
  private readonly notificationService = inject(NotificationService);

  @Input({ required: true }) module!: ExplorerModule;

  readonly activeEndpoint = signal<ExplorerEndpoint | null>(null);
  readonly result = signal<RequestResult | null>(null);

  readonly pathGroup = new UntypedFormGroup({});
  readonly queryGroup = new UntypedFormGroup({});
  readonly bodyGroup = new UntypedFormGroup({});

  readonly summary = computed(() => this.buildSummary());
  readonly tableColumns = computed(() => this.buildColumns());
  readonly tableRows = computed(() => this.buildRows());

  ngOnInit(): void {
    this.selectEndpoint(this.module.endpoints[0]);
  }

  selectEndpoint(endpoint: ExplorerEndpoint): void {
    this.activeEndpoint.set(endpoint);
    this.result.set(null);
    this.resetGroup(this.pathGroup, endpoint.pathParams ?? []);
    this.resetGroup(this.queryGroup, endpoint.queryParams ?? []);
    this.resetGroup(this.bodyGroup, endpoint.bodyFields ?? []);
  }

  submit(): void {
    const endpoint = this.activeEndpoint();
    if (!endpoint) {
      return;
    }

    if (this.pathGroup.invalid || this.bodyGroup.invalid || this.queryGroup.invalid) {
      this.pathGroup.markAllAsTouched();
      this.queryGroup.markAllAsTouched();
      this.bodyGroup.markAllAsTouched();
      this.notificationService.error('Complete the required fields before sending the request.');
      return;
    }

    this.apiService.execute(endpoint, {
      pathParams: this.toPayload(this.pathGroup),
      queryParams: this.toPayload(this.queryGroup),
      body: Object.keys(this.bodyGroup.controls).length ? this.toPayload(this.bodyGroup) : null
    }).subscribe((result) => {
      this.result.set(result);
      this.notificationService.info(result.ok ? 'Response received.' : 'Request completed with an error response.');
    });
  }

  controlInvalid(group: UntypedFormGroup, key: string): boolean {
    const control = group.controls[key];
    return Boolean(control?.invalid && (control.dirty || control.touched));
  }

  private resetGroup(group: UntypedFormGroup, fields: ExplorerField[]): void {
    for (const key of Object.keys(group.controls)) {
      group.removeControl(key);
    }

    for (const field of fields) {
      group.addControl(
        field.key,
        new FormControl('', {
          nonNullable: true,
          validators: field.required ? [Validators.required] : []
        })
      );
    }
  }

  private toPayload(group: UntypedFormGroup): Record<string, string | number> {
    return Object.entries(group.getRawValue()).reduce<Record<string, string | number>>((payload, [key, rawValue]) => {
      const value = String(rawValue ?? '');

      if (value === '') {
        return payload;
      }

      payload[key] = /^-?\d+(\.\d+)?$/.test(value) ? Number(value) : value;
      return payload;
    }, {});
  }

  private buildRows(): Record<string, unknown>[] {
    const body = this.result()?.body;
    if (Array.isArray(body)) {
      return body.filter((item): item is Record<string, unknown> => typeof item === 'object' && item !== null);
    }

    if (typeof body === 'object' && body !== null) {
      for (const value of Object.values(body)) {
        if (Array.isArray(value)) {
          return value.filter((item): item is Record<string, unknown> => typeof item === 'object' && item !== null);
        }
      }

      return [body as Record<string, unknown>];
    }

    return [];
  }

  private buildColumns(): string[] {
    const firstRow = this.tableRows()[0];
    return firstRow ? Object.keys(firstRow).slice(0, 5) : [];
  }

  private buildSummary(): { label: string; value: string | number }[] {
    const rows = this.tableRows();
    if (!rows.length) {
      return [
        { label: 'Status', value: this.result()?.ok ? 'Success' : 'Ready' },
        { label: 'Rows', value: 0 }
      ];
    }

    const distinctValues = new Set<string>();
    for (const row of rows) {
      for (const value of Object.values(row)) {
        if (typeof value === 'string' && value.trim()) {
          distinctValues.add(value);
        }
      }
    }

    return [
      { label: `Total ${this.module.title.split(' ')[0]}`, value: rows.length },
      { label: 'Distinct Values', value: distinctValues.size }
    ];
  }
}
