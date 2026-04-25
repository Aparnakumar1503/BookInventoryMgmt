import { Component, computed, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import { finalize } from 'rxjs';
import { EndpointConfig, EndpointField, EndpointRequestPayload } from '../../../../core/models/endpoint.model';
import { ApiCallResult } from '../../../../core/models/response.model';
import { ApiService } from '../../../../core/services/api.service';
import { ModuleService } from '../../../../core/services/module.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { StorageService } from '../../../../core/services/storage.service';
import { EndpointGroup, groupEndpoints } from '../../../../core/utils/endpoint-groups';
import { LoadingSpinnerComponent } from '../../../../shared/ui/loading-spinner/loading-spinner';
import { ResponseViewerComponent } from '../../../../shared/ui/response-viewer/response-viewer';

type FieldValue = string | number | boolean | null;
type FieldControls = Record<string, FormControl<FieldValue>>;

@Component({
  selector: 'app-endpoint-form',
  imports: [ReactiveFormsModule, RouterLink, RouterLinkActive, LoadingSpinnerComponent, ResponseViewerComponent],
  templateUrl: './endpoint-form.html',
  styleUrl: './endpoint-form.css'
})
export class EndpointFormComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly apiService = inject(ApiService);
  private readonly moduleService = inject(ModuleService);
  private readonly notificationService = inject(NotificationService);
  private readonly storageService = inject(StorageService);

  readonly moduleId = this.route.snapshot.paramMap.get('moduleId') ?? '';
  readonly endpointId = this.route.snapshot.paramMap.get('endpointId') ?? '';
  readonly module = this.moduleService.getModule(this.moduleId);
  readonly endpoint = this.moduleService.getEndpoint(this.moduleId, this.endpointId);
  readonly endpointGroups = computed<EndpointGroup[]>(() => this.module ? groupEndpoints(this.module.endpoints) : []);
  readonly isLoading = signal(false);
  readonly response = signal<ApiCallResult | null>(null);
  readonly isPrefillLoading = signal(false);
  readonly prefillStatus = signal('');
  readonly isRecordLocked = signal(false);
  readonly lockedRecordMessage = signal('');
  readonly backendErrors = signal<{
    pathParams: Record<string, string>;
    queryParams: Record<string, string>;
    body: Record<string, string>;
  }>({
    pathParams: {},
    queryParams: {},
    body: {}
  });

  readonly pathFields = computed(() => this.endpoint?.pathParams ?? []);
  readonly queryFields = computed(() => this.endpoint?.queryParams ?? []);
  readonly bodyFields = computed(() => this.endpoint?.body?.fields ?? []);
  readonly viewMode = signal<'preview' | 'table' | 'json'>('preview');
  readonly responseReason = computed(() => {
    const body = this.response()?.body;
    if (body && typeof body === 'object' && !Array.isArray(body) && 'message' in body) {
      const message = (body as { message?: unknown }).message;
      return typeof message === 'string' ? message : '';
    }

    if (typeof body === 'string') {
      return body;
    }

    return '';
  });
  readonly responseBody = computed<Record<string, unknown> | null>(() => {
    const body = this.response()?.body;
    return this.isRecord(body) ? body : null;
  });
  readonly responseUrl = computed(() => this.response()?.url || this.endpoint?.path || '');
  readonly responseData = computed<unknown>(() => this.responseBody()?.['data'] ?? null);
  readonly paginatedData = computed<Record<string, unknown> | null>(() => {
    const data = this.responseData();
    return this.isRecord(data) && Array.isArray(data['items']) ? data : null;
  });
  readonly tableRows = computed<Record<string, unknown>[]>(() => {
    const paginated = this.paginatedData();
    return this.normalizeRows(paginated ? paginated['items'] : this.responseData());
  });
  readonly tableColumns = computed<string[]>(() => {
    const rows = this.tableRows();
    if (!rows.length) {
      return [];
    }

    return Array.from(
      rows.reduce((keys, row) => {
        Object.keys(row).forEach((key) => keys.add(key));
        return keys;
      }, new Set<string>())
    );
  });
  readonly authorPreviewItems = computed(() => {
    const rows = this.tableRows();
    const firstRow = rows[0];
    if (!firstRow || !('authorId' in firstRow) || !('firstName' in firstRow) || !('lastName' in firstRow)) {
      return [];
    }

    return rows.map((item) => ({
      authorId: Number(item['authorId'] ?? 0),
      fullName: `${String(item['firstName'] ?? '')} ${String(item['lastName'] ?? '')}`.trim(),
      firstName: String(item['firstName'] ?? ''),
      lastName: String(item['lastName'] ?? ''),
      photo: String(item['photo'] ?? '')
    }));
  });
  private lastPrefillSignature = '';

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

    if (this.isRecordLocked()) {
      this.notificationService.error(this.lockedRecordMessage() || 'This record cannot be updated.');
      return;
    }

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.clearBackendErrors();
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
    this.clearBackendErrors();

    this.apiService.execute(this.endpoint, payload)
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe((result) => {
        this.response.set(result);
        this.storageService.setLastResponse(result, {
          moduleId: this.moduleId,
          endpointId: this.endpointId,
          payload
        });
        this.viewMode.set('preview');

        if (result.ok) {
          this.prefillStatus.set('');
          this.notificationService.success('API request completed successfully.');
          return;
        }

        this.applyBackendErrors(result);
        this.notificationService.error(this.buildErrorMessage(result));
      });
  }

  hasError(section: 'pathParams' | 'queryParams' | 'body', key: string): boolean {
    const control = this.form.controls[section].controls[key];
    return Boolean(
      (control?.invalid && (control.dirty || control.touched))
      || this.backendErrors()[section][key]
    );
  }

  getErrorMessage(section: 'pathParams' | 'queryParams' | 'body', key: string): string {
    const backendMessage = this.backendErrors()[section][key];
    if (backendMessage) {
      return backendMessage;
    }

    const control = this.form.controls[section].controls[key];
    if (control?.hasError('required')) {
      return this.findField(section, key)?.requiredMessage ?? 'Required field';
    }

    if (control?.hasError('min')) {
      const field = this.findField(section, key);
      return field?.minMessage ?? `${field?.label ?? 'Value'} must be at least ${field?.min}.`;
    }

    if (control?.hasError('max')) {
      const field = this.findField(section, key);
      return field?.maxMessage ?? `${field?.label ?? 'Value'} must be at most ${field?.max}.`;
    }

    if (control?.hasError('minlength')) {
      const field = this.findField(section, key);
      return field?.minLengthMessage ?? `${field?.label ?? 'Value'} must be at least ${field?.minLength} characters.`;
    }

    if (control?.hasError('maxlength')) {
      const field = this.findField(section, key);
      return field?.maxLengthMessage ?? `${field?.label ?? 'Value'} must be at most ${field?.maxLength} characters.`;
    }

    if (control?.hasError('pattern')) {
      const field = this.findField(section, key);
      return field?.patternMessage ?? `${field?.label ?? 'Value'} format is invalid.`;
    }

    return 'Invalid value';
  }

  supportsPrefill(): boolean {
    return Boolean(this.endpoint?.method === 'PUT' && this.endpoint?.prefill && this.bodyFields().length);
  }

  canLoadExistingRecord(): boolean {
    return this.supportsPrefill() && this.areRequiredPathParamsFilled();
  }

  onPathParamInput(): void {
    if (!this.supportsPrefill()) {
      return;
    }

    if (this.currentPrefillSignature() !== this.lastPrefillSignature) {
      this.prefillStatus.set('');
      this.unlockBodyControls();
      this.clearBodyValues();
    }
  }

  maybeLoadExistingRecord(): void {
    if (!this.canLoadExistingRecord()) {
      return;
    }

    const signature = this.currentPrefillSignature();
    if (signature && signature === this.lastPrefillSignature) {
      return;
    }

    this.loadExistingRecord();
  }

  loadExistingRecord(): void {
    if (!this.endpoint?.prefill || !this.canLoadExistingRecord() || this.isPrefillLoading()) {
      return;
    }

    const fetchEndpoint = this.moduleService.getEndpoint(this.moduleId, this.endpoint.prefill.endpointId);
    if (!fetchEndpoint) {
      this.notificationService.error('Matching fetch endpoint configuration was not found.');
      return;
    }

    const requestPayload: EndpointRequestPayload = {
      pathParams: this.buildPrefillPathParams(),
      queryParams: {},
      body: null
    };

    this.isPrefillLoading.set(true);
    this.prefillStatus.set('Loading current data...');

    this.apiService.execute(fetchEndpoint, requestPayload)
      .pipe(finalize(() => this.isPrefillLoading.set(false)))
      .subscribe((result) => {
        this.response.set(result);
        this.storageService.setLastResponse(result, {
          moduleId: this.moduleId,
          endpointId: fetchEndpoint.id,
          payload: requestPayload
        });
        this.viewMode.set('preview');

        if (!result.ok) {
          this.prefillStatus.set('Could not load current data.');
          this.notificationService.error(this.buildErrorMessage(result));
          return;
        }

        const record = this.extractResponseData(result.body);
        if (!record || typeof record !== 'object' || Array.isArray(record)) {
          this.prefillStatus.set('No current data was returned.');
          this.notificationService.error('No matching record data was returned by the server.');
          return;
        }

        const resolvedRecord = record as Record<string, unknown>;
        this.patchBodyFromRecord(resolvedRecord);
        this.applyPrefillLockState(resolvedRecord);
        if (this.isRecordLocked()) {
          const lockedResponse = this.buildLockedRecordResponse();
          this.response.set(lockedResponse);
          this.storageService.setLastResponse(lockedResponse, {
            moduleId: this.moduleId,
            endpointId: this.endpointId,
            payload: requestPayload
          });
          this.viewMode.set('json');
        }
        this.lastPrefillSignature = this.currentPrefillSignature();
        const statusMessage = this.isRecordLocked()
          ? this.lockedRecordMessage()
          : 'Current data loaded. Review and update the fields below.';
        this.prefillStatus.set(statusMessage);
        this.notificationService.info(this.isRecordLocked() ? 'Current record loaded as read-only.' : 'Current record loaded.');
      });
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
      const validators: ValidatorFn[] = [];

      if (field.required) {
        validators.push(Validators.required);
      }

      if (field.type === 'number' && field.min !== undefined) {
        validators.push(Validators.min(field.min));
      }

      if (field.type === 'number' && field.max !== undefined) {
        validators.push(Validators.max(field.max));
      }

      if (field.minLength !== undefined) {
        validators.push(Validators.minLength(field.minLength));
      }

      if (field.maxLength !== undefined) {
        validators.push(Validators.maxLength(field.maxLength));
      }

      if (field.pattern) {
        validators.push(Validators.pattern(field.pattern));
      }

      group[field.key] = new FormControl<FieldValue>(
        field.defaultValue ?? (field.type === 'boolean' ? false : ''),
        { nonNullable: false, validators }
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

  private areRequiredPathParamsFilled(): boolean {
    return this.pathFields().every((field) => {
      const value = this.pathParamsGroup.controls[field.key]?.value;
      if (!field.required) {
        return true;
      }

      return value !== null && value !== undefined && String(value).trim() !== '';
    });
  }

  private currentPrefillSignature(): string {
    return this.pathFields()
      .map((field) => `${field.key}:${String(this.pathParamsGroup.controls[field.key]?.value ?? '')}`)
      .join('|');
  }

  private buildPrefillPathParams(): Record<string, string | number | boolean> {
    const mapping = this.endpoint?.prefill?.pathParamMap ?? {};

    return this.pathFields().reduce<Record<string, string | number | boolean>>((params, field) => {
      const targetKey = mapping[field.key] ?? field.key;
      const value = this.pathParamsGroup.controls[field.key]?.value;

      if (value !== null && value !== undefined && String(value).trim() !== '') {
        params[targetKey] = value;
      }

      return params;
    }, {});
  }

  private buildErrorMessage(result: ApiCallResult): string {
    const body = result.body;

    if (typeof body === 'string' && body.trim()) {
      return body;
    }

    if (body && typeof body === 'object') {
      const payload = body as {
        message?: unknown;
        data?: unknown;
      };

      const fieldErrors = this.formatFieldErrors(payload.data);
      if (fieldErrors) {
        return payload.message && typeof payload.message === 'string'
          ? `${payload.message}: ${fieldErrors}`
          : fieldErrors;
      }

      if (typeof payload.message === 'string' && payload.message.trim()) {
        return payload.message;
      }
    }

    return `API request returned an error${result.status ? ` (${result.status})` : ''}.`;
  }

  private formatFieldErrors(data: unknown): string {
    if (!data || typeof data !== 'object' || Array.isArray(data)) {
      return '';
    }

    const entries = Object.entries(data as Record<string, unknown>)
      .filter(([, value]) => typeof value === 'string' && value.trim())
      .map(([key, value]) => `${this.toLabel(key)}: ${value as string}`);

    return entries.join(' | ');
  }

  private toLabel(key: string): string {
    return key
      .replace(/([a-z0-9])([A-Z])/g, '$1 $2')
      .replace(/[._]/g, ' ')
      .replace(/\s+/g, ' ')
      .trim()
      .replace(/^./, (value) => value.toUpperCase());
  }

  private extractResponseData(body: unknown): unknown {
    if (body && typeof body === 'object' && !Array.isArray(body) && 'data' in body) {
      return (body as { data?: unknown }).data;
    }

    return body;
  }

  private patchBodyFromRecord(record: Record<string, unknown>): void {
    const fieldMap = this.endpoint?.prefill?.bodyFieldMap ?? {};

    for (const field of this.bodyFields()) {
      const sourceKey = fieldMap[field.key] ?? field.key;
      const rawValue = record[sourceKey];
      const control = this.bodyGroup.controls[field.key];

      if (!control) {
        continue;
      }

      if (rawValue === undefined || rawValue === null) {
        control.setValue(field.type === 'boolean' ? false : '');
        continue;
      }

      control.setValue(rawValue as FieldValue);
    }
  }

  private applyPrefillLockState(record: Record<string, unknown>): void {
    const lockField = this.endpoint?.prefill?.lockWhenFieldTrue;
    if (!lockField) {
      this.unlockBodyControls();
      return;
    }

    const shouldLock = Boolean(record[lockField]);
    if (!shouldLock) {
      this.unlockBodyControls();
      return;
    }

    const message = this.endpoint?.prefill?.lockedMessage ?? 'This record cannot be updated.';
    this.isRecordLocked.set(true);
    this.lockedRecordMessage.set(message);

    Object.values(this.bodyGroup.controls).forEach((control) => control.disable({ emitEvent: false }));
  }

  private buildLockedRecordResponse(): ApiCallResult {
    const message = this.endpoint?.prefill?.lockedMessage ?? 'This record cannot be updated.';

    return {
      status: 409,
      ok: false,
      url: this.endpoint?.path ?? null,
      receivedAt: new Date().toISOString(),
      body: {
        statusCode: 409,
        message,
        data: null,
        timestamp: new Date().toISOString()
      }
    };
  }

  setViewMode(mode: 'preview' | 'table' | 'json'): void {
    this.viewMode.set(mode);
  }

  formatColumnLabel(column: string): string {
    return column.replace(/([a-z0-9])([A-Z])/g, '$1 $2').replace(/[_-]+/g, ' ').replace(/\b\w/g, (char) => char.toUpperCase());
  }

  renderCellValue(row: Record<string, unknown>, column: string): string {
    const value = row[column];
    if (value === null || value === undefined || value === '') return '-';
    if (typeof value === 'object') return JSON.stringify(value);
    return String(value);
  }

  trackByColumn(index: number, column: string): string {
    return `${index}-${column}`;
  }

  previewInitials(value: string): string {
    return value.split(/\s+/).filter(Boolean).slice(0, 2).map((part) => part[0]?.toUpperCase() ?? '').join('') || 'AU';
  }

  private applyBackendErrors(result: ApiCallResult): void {
    const body = result.body;
    if (!body || typeof body !== 'object' || Array.isArray(body)) {
      this.clearBackendErrors();
      return;
    }

    const payload = body as { data?: unknown };
    if (!payload.data || typeof payload.data !== 'object' || Array.isArray(payload.data)) {
      this.clearBackendErrors();
      return;
    }

    const pathKeys = new Set(this.pathFields().map((field) => field.key));
    const queryKeys = new Set(this.queryFields().map((field) => field.key));
    const bodyKeys = new Set(this.bodyFields().map((field) => field.key));

    const nextErrors = {
      pathParams: {} as Record<string, string>,
      queryParams: {} as Record<string, string>,
      body: {} as Record<string, string>
    };

    for (const [rawKey, rawValue] of Object.entries(payload.data as Record<string, unknown>)) {
      if (typeof rawValue !== 'string' || !rawValue.trim()) {
        continue;
      }

      const key = this.normalizeErrorKey(rawKey);

      if (bodyKeys.has(key)) {
        nextErrors.body[key] = rawValue;
        continue;
      }

      if (pathKeys.has(key)) {
        nextErrors.pathParams[key] = rawValue;
        continue;
      }

      if (queryKeys.has(key)) {
        nextErrors.queryParams[key] = rawValue;
      }
    }

    this.backendErrors.set(nextErrors);
  }

  private clearBackendErrors(): void {
    this.backendErrors.set({
      pathParams: {},
      queryParams: {},
      body: {}
    });
  }

  private clearBodyValues(): void {
    for (const field of this.bodyFields()) {
      const control = this.bodyGroup.controls[field.key];
      if (!control) {
        continue;
      }

      control.setValue(field.defaultValue ?? (field.type === 'boolean' ? false : ''));
    }

    this.lastPrefillSignature = '';
    this.backendErrors.update((current) => ({
      ...current,
      body: {}
    }));
  }

  private unlockBodyControls(): void {
    this.isRecordLocked.set(false);
    this.lockedRecordMessage.set('');
    Object.values(this.bodyGroup.controls).forEach((control) => control.enable({ emitEvent: false }));
  }

  private normalizeErrorKey(key: string): string {
    const segments = key.split('.');
    return segments[segments.length - 1] ?? key;
  }

  private findField(section: 'pathParams' | 'queryParams' | 'body', key: string): EndpointField | undefined {
    const fields = section === 'pathParams'
      ? this.pathFields()
      : section === 'queryParams'
        ? this.queryFields()
        : this.bodyFields();

    return fields.find((field) => field.key === key);
  }

  private normalizeRows(value: unknown): Record<string, unknown>[] {
    if (Array.isArray(value)) return value.filter((item): item is Record<string, unknown> => this.isRecord(item));
    if (this.isRecord(value)) return [value];
    return [];
  }

  private isRecord(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null && !Array.isArray(value);
  }
}
