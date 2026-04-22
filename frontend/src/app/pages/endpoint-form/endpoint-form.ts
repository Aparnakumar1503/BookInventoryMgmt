import { Component, computed, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { EndpointConfig, EndpointField, EndpointRequestPayload } from '../../core/models/endpoint.model';
import { ApiCallResult } from '../../core/models/response.model';
import { ApiService } from '../../core/services/api.service';
import { ModuleService } from '../../core/services/module.service';
import { NotificationService } from '../../core/services/notification.service';
import { StorageService } from '../../core/services/storage.service';
import { LoadingSpinnerComponent } from '../../shared/component/loading-spinner/loading-spinner';
import { ResponseViewerComponent } from '../../shared/component/response-viewer/response-viewer';

type FieldValue = string | number | boolean | null;
type FieldControls = Record<string, FormControl<FieldValue>>;

@Component({
  selector: 'app-endpoint-form',
  imports: [ReactiveFormsModule, RouterLink, LoadingSpinnerComponent, ResponseViewerComponent],
  templateUrl: './endpoint-form.html'
})
export class EndpointFormComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly apiService = inject(ApiService);
  private readonly moduleService = inject(ModuleService);
  private readonly notificationService = inject(NotificationService);
  private readonly storageService = inject(StorageService);

  readonly moduleId = this.route.snapshot.paramMap.get('moduleId') ?? '';
  readonly endpointId = this.route.snapshot.paramMap.get('endpointId') ?? '';
  readonly module = this.moduleService.getModule(this.moduleId);
  readonly endpoint = this.moduleService.getEndpoint(this.moduleId, this.endpointId);
  readonly isLoading = signal(false);
  readonly response = signal<ApiCallResult | null>(null);

  readonly pathFields = computed(() => this.endpoint?.pathParams ?? []);
  readonly queryFields = computed(() => this.endpoint?.queryParams ?? []);
  readonly bodyFields = computed(() => this.endpoint?.body?.fields ?? []);

  readonly form = new FormGroup({
    pathParams: this.createGroup(this.pathFields()),
    queryParams: this.createGroup(this.queryFields()),
    body: this.createGroup(this.bodyFields())
  });

  get pathParamsGroup(): FormGroup<FieldControls> {
    return this.form.controls.pathParams;
  }

  get queryParamsGroup(): FormGroup<FieldControls> {
    return this.form.controls.queryParams;
  }

  get bodyGroup(): FormGroup<FieldControls> {
    return this.form.controls.body;
  }

  submit(): void {
    if (!this.endpoint) {
      this.notificationService.error('Endpoint configuration was not found.');
      return;
    }

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.notificationService.error('Please complete the required fields.');
      return;
    }

    const payload: EndpointRequestPayload = {
      pathParams: this.collect(this.pathParamsGroup, this.pathFields(), false),
      queryParams: this.collect(this.queryParamsGroup, this.queryFields(), true),
      body: this.bodyFields().length ? this.collect(this.bodyGroup, this.bodyFields(), true) : null
    };

    this.isLoading.set(true);
    this.response.set(null);

    this.apiService.execute(this.endpoint, payload)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe((result) => {
        this.response.set(result);

        if (result.ok) {
          this.storageService.setLastResponse(result, this.moduleId);
          this.notificationService.success('API request completed successfully.');
          void this.router.navigate(['/result']);
          return;
        }

        this.notificationService.error('API request returned an error.');
      });
  }

  hasError(section: 'pathParams' | 'queryParams' | 'body', key: string): boolean {
    const control = this.form.controls[section].controls[key];
    return Boolean(control?.invalid && (control.dirty || control.touched));
  }

  inputType(field: EndpointField): string {
    if (field.type === 'email' || field.type === 'date' || field.type === 'number') {
      return field.type;
    }

    return 'text';
  }

  methodClass(method: string): string {
    const classes: Record<string, string> = {
      GET: 'bg-blue-100 text-blue-800',
      POST: 'bg-teal-100 text-teal-800',
      PUT: 'bg-amber-100 text-amber-800',
      PATCH: 'bg-violet-100 text-violet-800',
      DELETE: 'bg-rose-100 text-rose-800'
    };

    return classes[method] ?? 'bg-slate-100 text-slate-800';
  }

  private createGroup(fields: readonly EndpointField[]): FormGroup<FieldControls> {
    const controls = fields.reduce<FieldControls>((group, field) => {
      group[field.key] = new FormControl<FieldValue>(
        field.defaultValue ?? (field.type === 'boolean' ? false : ''),
        field.required ? { nonNullable: false, validators: [Validators.required] } : { nonNullable: false }
      );
      return group;
    }, {});

    return new FormGroup<FieldControls>(controls);
  }

  private collect(
    group: FormGroup<FieldControls>,
    fields: readonly EndpointField[],
    omitEmpty: boolean
  ): Record<string, string | number | boolean> {
    return fields.reduce<Record<string, string | number | boolean>>((payload, field) => {
      const value = group.controls[field.key]?.value;

      if (omitEmpty && (value === '' || value === null)) {
        return payload;
      }

      payload[field.key] = field.type === 'number' && value !== null && value !== '' ? Number(value) : value ?? '';
      return payload;
    }, {});
  }
}
