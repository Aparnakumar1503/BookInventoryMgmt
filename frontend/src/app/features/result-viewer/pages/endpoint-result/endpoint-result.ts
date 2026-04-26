import { DecimalPipe } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { EndpointRequestPayload } from '../../../../core/models/endpoint.model';
import { ApiCallResult, StoredRequestContext } from '../../../../core/models/response.model';
import { ApiService } from '../../../../core/services/api.service';
import { ModuleService } from '../../../../core/services/module.service';
import { NotificationService } from '../../../../core/services/notification.service';
import { StorageService } from '../../../../core/services/storage.service';
import { ResponseViewerComponent } from '../../../../shared/ui/response-viewer/response-viewer';

type ViewMode = 'table' | 'preview' | 'json';
type PreviewType =
  | 'books'
  | 'authors'
  | 'users'
  | 'reviewers'
  | 'reviews'
  | 'inventory'
  | 'conditions'
  | 'cart'
  | 'orders'
  | 'publishers'
  | 'categories'
  | 'states'
  | 'roles'
  | null;

interface ApiEnvelope {
  statusCode?: number;
  message?: string;
  data?: unknown;
  timestamp?: string;
}

interface PaginatedData {
  items: unknown[];
  page: number;
  size: number;
  totalItems: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}

interface BookPreviewItem {
  isbn: string;
  title: string;
  description: string;
  edition: string;
  categoryName: string;
  publisherName: string;
}

interface RolePreviewItem {
  roleNumber: number;
  permRole: string;
}

interface ErrorViewModel {
  status: number;
  title: string;
  heading: string;
  exceptionName: string | null;
  summary: string;
  details: string[];
  timestampLabel: string;
  requestLabel: string;
  footerMessage: string;
}

interface NotModifiedViewModel {
  status: number;
  title: string;
  summary: string;
  details: string[];
  timestampLabel: string;
  requestLabel: string;
  footerMessage: string;
}

interface ActionSuccessViewModel {
  status: number;
  title: string;
  summary: string;
  details: string[];
  timestampLabel: string;
  requestLabel: string;
  footerMessage: string;
}

@Component({
  selector: 'app-endpoint-result',
  imports: [RouterLink, ResponseViewerComponent, DecimalPipe],
  templateUrl: './endpoint-result.html',
  styleUrl: './endpoint-result.css'
})
export class EndpointResultComponent {
  private readonly storageService = inject(StorageService);
  private readonly moduleService = inject(ModuleService);
  private readonly apiService = inject(ApiService);
  private readonly notificationService = inject(NotificationService);

  readonly response = signal<ApiCallResult | null>(this.storageService.lastResponse());
  readonly moduleId = this.storageService.getLastModuleId();
  readonly requestContext = signal<StoredRequestContext | null>(this.storageService.getLastRequestContext());
  readonly isLoadingPage = signal(false);
  readonly viewMode = signal<ViewMode>('preview');
  readonly pageSizeOptions = [6, 9, 12] as const;

  readonly endpoint = computed(() => {
    const context = this.requestContext();
    return context ? this.moduleService.getEndpoint(context.moduleId, context.endpointId) ?? null : null;
  });

  readonly responseBody = computed<ApiEnvelope | null>(() => {
    const body = this.response()?.body;
    return this.isRecord(body) ? body as ApiEnvelope : null;
  });

  readonly responseData = computed<unknown>(() => this.responseBody()?.data ?? null);
  readonly isNotModifiedResponse = computed(() => {
    const result = this.response();
    const status = result?.status || this.toNumber(this.responseBody()?.statusCode);
    return status === 304;
  });
  readonly isErrorResponse = computed(() => Boolean(this.response() && !this.response()?.ok));
  readonly showErrorState = computed(() => this.isErrorResponse() && !this.isNotModifiedResponse());
  readonly errorViewModel = computed<ErrorViewModel | null>(() => {
    const result = this.response();
    if (!result || result.ok || this.isNotModifiedResponse()) {
      return null;
    }

    const status = result.status || this.toNumber(this.responseBody()?.statusCode);
    const body = this.responseBody();
    const rawMessage = typeof body?.message === 'string' && body.message.trim()
      ? body.message.trim()
      : 'The request was unsuccessful.';
    const detailEntries = this.errorDetails(body?.data);
    const exceptionName = this.exceptionName(body?.data);
    const title = exceptionName || `${status || 'Error'} - ${this.statusLabel(status)}`;

    return {
      status,
      title,
      heading: exceptionName
        ? `${exceptionName} · ${status || 'error'} ${this.statusLabel(status).toLowerCase()}`
        : `Request failed - ${status || 'error'} ${this.statusLabel(status).toLowerCase()}`,
      exceptionName,
      summary: rawMessage,
      details: detailEntries.length
        ? detailEntries
        : ['Check the request values and verify the endpoint details are correct.'],
      timestampLabel: this.formatTimestamp(result.receivedAt),
      requestLabel: `${this.endpoint()?.method ?? 'REQUEST'} ${result.url ?? this.endpoint()?.path ?? ''}`.trim(),
      footerMessage: this.footerMessage(status)
    };
  });
  readonly notModifiedViewModel = computed<NotModifiedViewModel | null>(() => {
    const result = this.response();
    if (!result || !this.isNotModifiedResponse()) {
      return null;
    }

    const body = this.responseBody();
    const status = result.status || this.toNumber(body?.statusCode);
    const rawMessage = typeof body?.message === 'string' && body.message.trim()
      ? body.message.trim()
      : 'No changes were detected, so the existing record was kept as-is.';

    return {
      status,
      title: 'No Data Updated',
      summary: rawMessage,
      details: [
        'The submitted values match the current record.',
        'No update was needed, so the server returned Not Modified.'
      ],
      timestampLabel: this.formatTimestamp(result.receivedAt),
      requestLabel: `${this.endpoint()?.method ?? 'REQUEST'} ${result.url ?? this.endpoint()?.path ?? ''}`.trim(),
      footerMessage: 'Request completed successfully. No fields were changed, so the existing data was preserved.'
    };
  });
  readonly actionSuccessViewModel = computed<ActionSuccessViewModel | null>(() => {
    const result = this.response();
    const body = this.responseBody();
    if (!result?.ok || this.isNotModifiedResponse() || this.paginatedData() || this.tableRows().length) {
      return null;
    }

    const status = result.status || this.toNumber(body?.statusCode);
    const summary = typeof body?.message === 'string' && body.message.trim()
      ? body.message.trim()
      : 'The request completed successfully.';
    const method = this.endpoint()?.method ?? 'REQUEST';
    const actionTitle = method === 'DELETE'
      ? 'Delete Completed'
      : method === 'PUT'
        ? 'Update Completed'
        : method === 'POST'
          ? 'Request Completed'
          : 'Success';

    return {
      status,
      title: actionTitle,
      summary,
      details: method === 'DELETE'
        ? ['The selected record or mapping was removed successfully.']
        : ['The operation finished successfully and there is no row data to display.'],
      timestampLabel: this.formatTimestamp(result.receivedAt),
      requestLabel: `${method} ${result.url ?? this.endpoint()?.path ?? ''}`.trim(),
      footerMessage: 'Request completed successfully.'
    };
  });

  readonly paginatedData = computed<PaginatedData | null>(() => {
    const data = this.responseData();
    if (!this.isRecord(data) || !Array.isArray(data['items'])) {
      return null;
    }

    return {
      items: data['items'],
      page: this.toNumber(data['page']),
      size: this.toNumber(data['size']),
      totalItems: this.toNumber(data['totalItems']),
      totalPages: this.toNumber(data['totalPages']),
      hasNext: Boolean(data['hasNext']),
      hasPrevious: Boolean(data['hasPrevious'])
    };
  });

  readonly tableRows = computed<Record<string, unknown>[]>(() => {
    const paginated = this.paginatedData();
    return this.normalizeRows(paginated ? paginated.items : this.responseData());
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

  readonly previewType = computed<PreviewType>(() => {
    const row = this.tableRows()[0];
    if (!row) return null;
    if (this.hasKeys(row, ['title', 'isbn'])) return 'books';
    if (this.hasKeys(row, ['authorId', 'firstName', 'lastName'])) return 'authors';
    if (this.hasKeys(row, ['userId', 'userName', 'roleNumber'])) return 'users';
    if (this.hasKeys(row, ['reviewerID', 'name'])) return 'reviewers';
    if (this.hasKeys(row, ['rating', 'comments'])) return 'reviews';
    if (this.hasKeys(row, ['inventoryId', 'isbn', 'ranks'])) return 'inventory';
    if (this.hasKeys(row, ['ranks', 'description', 'price'])) return 'conditions';
    if (this.hasKeys(row, ['userId', 'isbn'])) return 'cart';
    if (this.hasKeys(row, ['userId', 'inventoryId'])) return 'orders';
    if (this.hasKeys(row, ['publisherId', 'name', 'stateCode'])) return 'publishers';
    if (this.hasKeys(row, ['catId', 'catDescription'])) return 'categories';
    if (this.hasKeys(row, ['stateCode', 'stateName'])) return 'states';
    if (this.hasKeys(row, ['roleNumber', 'permRole'])) return 'roles';
    return null;
  });

  readonly canPaginate = computed(() => Boolean(
    this.response()?.ok &&
    this.endpoint()?.method === 'GET' &&
    this.paginatedData() &&
    this.requestContext()
  ));

  readonly previewSupported = computed(() => this.isErrorResponse() || this.previewType() !== null || this.isNotModifiedResponse());
  readonly currentPageSize = computed(() => this.paginatedData()?.size || 0);
  readonly shownCount = computed(() => this.tableRows().length);

  readonly bookPreviewItems = computed<BookPreviewItem[]>(() => this.previewType() === 'books'
    ? this.tableRows().map((item) => ({
        isbn: String(item['isbn'] ?? ''),
        title: String(item['title'] ?? 'Untitled'),
        description: String(item['description'] ?? 'No description available.'),
        edition: String(item['edition'] ?? 'N/A'),
        categoryName: String(item['categoryName'] ?? item['categoryId'] ?? 'Uncategorized'),
        publisherName: String(item['publisherName'] ?? item['publisherId'] ?? 'Unknown publisher')
      }))
    : []);

  readonly authorPreviewItems = computed(() => this.previewType() === 'authors'
    ? this.tableRows().map((item) => ({
        authorId: Number(item['authorId'] ?? 0),
        fullName: `${String(item['firstName'] ?? '')} ${String(item['lastName'] ?? '')}`.trim(),
        firstName: String(item['firstName'] ?? ''),
        lastName: String(item['lastName'] ?? ''),
        photo: String(item['photo'] ?? '')
      }))
    : []);

  readonly userPreviewItems = computed(() => this.previewType() === 'users'
    ? this.tableRows().map((item) => ({
        userId: Number(item['userId'] ?? 0),
        fullName: `${String(item['firstName'] ?? '')} ${String(item['lastName'] ?? '')}`.trim(),
        userName: String(item['userName'] ?? ''),
        phoneNumber: String(item['phoneNumber'] ?? 'Not provided'),
        roleNumber: Number(item['roleNumber'] ?? 0),
        permRole: String(item['permRole'] ?? 'Unknown role')
      }))
    : []);

  readonly reviewerPreviewItems = computed(() => this.previewType() === 'reviewers'
    ? this.tableRows().map((item) => ({
        reviewerID: Number(item['reviewerID'] ?? 0),
        name: String(item['name'] ?? 'Unnamed reviewer'),
        employedBy: String(item['employedBy'] ?? 'Independent')
      }))
    : []);

  readonly reviewPreviewItems = computed(() => this.previewType() === 'reviews'
    ? this.tableRows().map((item) => ({
        id: Number(item['id'] ?? 0),
        isbn: String(item['isbn'] ?? ''),
        reviewerID: Number(item['reviewerID'] ?? 0),
        rating: Number(item['rating'] ?? 0),
        comments: String(item['comments'] ?? 'No comments provided.')
      }))
    : []);

  readonly inventoryPreviewItems = computed(() => this.previewType() === 'inventory'
    ? this.tableRows().map((item) => ({
        inventoryId: Number(item['inventoryId'] ?? 0),
        isbn: String(item['isbn'] ?? ''),
        ranks: Number(item['ranks'] ?? 0),
        purchased: Boolean(item['purchased'])
      }))
    : []);

  readonly conditionPreviewItems = computed(() => this.previewType() === 'conditions'
    ? this.tableRows().map((item) => ({
        ranks: Number(item['ranks'] ?? 0),
        description: String(item['description'] ?? ''),
        fullDescription: String(item['fullDescription'] ?? ''),
        price: Number(item['price'] ?? 0)
      }))
    : []);

  readonly cartPreviewItems = computed(() => this.previewType() === 'cart'
    ? this.tableRows().map((item) => ({
        userId: Number(item['userId'] ?? 0),
        isbn: String(item['isbn'] ?? '')
      }))
    : []);

  readonly orderPreviewItems = computed(() => this.previewType() === 'orders'
    ? this.tableRows().map((item) => ({
        userId: Number(item['userId'] ?? 0),
        inventoryId: Number(item['inventoryId'] ?? 0)
      }))
    : []);

  readonly publisherPreviewItems = computed(() => this.previewType() === 'publishers'
    ? this.tableRows().map((item) => ({
        publisherId: Number(item['publisherId'] ?? 0),
        name: String(item['name'] ?? ''),
        city: String(item['city'] ?? 'Unknown city'),
        stateCode: String(item['stateCode'] ?? ''),
        stateName: String(item['stateName'] ?? '')
      }))
    : []);

  readonly categoryPreviewItems = computed(() => this.previewType() === 'categories'
    ? this.tableRows().map((item) => ({
        catId: Number(item['catId'] ?? 0),
        catDescription: String(item['catDescription'] ?? '')
      }))
    : []);

  readonly statePreviewItems = computed(() => this.previewType() === 'states'
    ? this.tableRows().map((item) => ({
        stateCode: String(item['stateCode'] ?? ''),
        stateName: String(item['stateName'] ?? '')
      }))
    : []);

  readonly rolePreviewItems = computed<RolePreviewItem[]>(() => this.previewType() === 'roles'
    ? this.tableRows().map((item) => ({
        roleNumber: Number(item['roleNumber'] ?? 0),
        permRole: String(item['permRole'] ?? '')
      }))
    : []);

  readonly activeViewLabel = computed(() => this.viewMode() === 'preview'
    ? 'Preview UI'
    : this.viewMode() === 'json'
      ? 'Raw JSON'
      : 'Table');

  constructor() {
    if (!this.previewSupported()) {
      this.viewMode.set('table');
    }
  }

  setViewMode(mode: ViewMode): void {
    if (mode === 'preview' && !this.previewSupported()) return;
    this.viewMode.set(mode);
  }

  goToPreviousPage(): void {
    const page = this.paginatedData()?.page ?? 0;
    if (page > 0) this.reloadPage(page - 1);
  }

  goToNextPage(): void {
    const paginated = this.paginatedData();
    if (paginated?.hasNext) this.reloadPage(paginated.page + 1);
  }

  updatePageSize(size: number): void {
    const paginated = this.paginatedData();
    if (!paginated || paginated.size === size) {
      return;
    }

    this.reloadPage(0, size);
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

  trackByBook(index: number, book: BookPreviewItem): string {
    return `${index}-${book.isbn}`;
  }

  trackByValue(index: number, item: { [key: string]: string | number | boolean }): string {
    return `${index}-${Object.values(item).join('-')}`;
  }

  trackByRole(index: number, role: RolePreviewItem): string {
    return `${index}-${role.roleNumber}-${role.permRole}`;
  }

  bookAccent(book: BookPreviewItem): string {
    const seed = book.title.length + book.isbn.length;
    const variants = ['from-blue-700 via-sky-600 to-cyan-500', 'from-emerald-700 via-teal-600 to-lime-500', 'from-amber-700 via-orange-600 to-rose-500', 'from-slate-700 via-blue-800 to-cyan-600'];
    return variants[seed % variants.length] ?? variants[0];
  }

  bookInitials(title: string): string {
    return title.split(/\s+/).filter(Boolean).slice(0, 2).map((part) => part[0]?.toUpperCase() ?? '').join('') || 'BK';
  }

  previewInitials(value: string): string {
    const parts = value.split(/\s+/).filter(Boolean);
    if (!parts.length) {
      return 'BV';
    }

    if (parts.length === 1) {
      return parts[0].slice(0, 2).toUpperCase() || 'BV';
    }

    return parts.slice(0, 2).map((part) => part[0]?.toUpperCase() ?? '').join('') || 'BV';
  }

  previewLeadValue(row: Record<string, unknown>): string {
    const entries = Object.values(row)
      .map((value) => value === null || value === undefined ? '' : String(value).trim())
      .filter((value) => value.length > 0);

    return entries[0] || 'Item';
  }

  previewHeroValue(row: Record<string, unknown>): string {
    const preferredKeys = [
      'permRole',
      'stateCode',
      'catDescription',
      'name',
      'title',
      'description',
      'userName',
      'isbn'
    ];

    for (const key of preferredKeys) {
      const value = row[key];
      if (value !== null && value !== undefined) {
        const normalized = String(value).trim();
        if (normalized) {
          return normalized;
        }
      }
    }

    return this.previewLeadValue(row);
  }

  roleTone(role: RolePreviewItem): 'guest' | 'registered' | 'owner' | 'admin' {
    const normalized = role.permRole.toLowerCase();
    if (normalized.includes('admin')) return 'admin';
    if (normalized.includes('store')) return 'owner';
    if (normalized.includes('registered')) return 'registered';
    return 'guest';
  }

  roleLabel(role: RolePreviewItem): string {
    const normalized = role.permRole.toLowerCase();
    if (normalized.includes('registered')) return 'Registered User';
    if (normalized.includes('store')) return 'Store Owner';
    return role.permRole || 'Role';
  }

  roleBadge(role: RolePreviewItem): string {
    const tone = this.roleTone(role);
    if (tone === 'admin') return 'FULL ACCESS';
    if (tone === 'owner') return 'ELEVATED ACCESS';
    if (tone === 'registered') return 'STANDARD ACCESS';
    return 'LIMITED ACCESS';
  }

  ratingStars(rating: number): string[] {
    const filled = Math.max(0, Math.min(5, Math.round(rating / 2)));
    return Array.from({ length: 5 }, (_, index) => index < filled ? '*' : 'o');
  }

  inventoryTone(purchased: boolean): string {
    return purchased ? 'bg-emerald-100 text-emerald-800' : 'bg-amber-100 text-amber-800';
  }

  private reloadPage(page: number, sizeOverride?: number): void {
    const context = this.requestContext();
    const endpoint = this.endpoint();
    const paginated = this.paginatedData();
    if (!context || !endpoint || !paginated) return;

    const nextSize = sizeOverride ?? paginated.size ?? 10;
    const payload: EndpointRequestPayload = {
      ...context.payload,
      queryParams: { ...context.payload.queryParams, page, size: nextSize }
    };

    this.isLoadingPage.set(true);
    this.apiService.execute(endpoint, payload)
      .pipe(finalize(() => this.isLoadingPage.set(false)))
      .subscribe((result) => {
        this.response.set(result);
        if (result.ok) {
          const nextContext: StoredRequestContext = { ...context, payload };
          this.requestContext.set(nextContext);
          this.storageService.setLastResponse(result, nextContext);
          return;
        }
        this.notificationService.error('Could not load the requested page.');
      });
  }

  private normalizeRows(value: unknown): Record<string, unknown>[] {
    if (Array.isArray(value)) return value.filter((item): item is Record<string, unknown> => this.isRecord(item));
    if (this.isRecord(value)) return [value];
    return [];
  }

  private isRecord(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null && !Array.isArray(value);
  }

  private hasKeys(row: Record<string, unknown>, keys: string[]): boolean {
    return keys.every((key) => key in row);
  }

  private toNumber(value: unknown): number {
    return typeof value === 'number' ? value : Number(value ?? 0);
  }

  private statusLabel(status: number): string {
    const labels: Record<number, string> = {
      400: 'Bad Request',
      401: 'Unauthorized',
      403: 'Forbidden',
      404: 'Not Found',
      409: 'Conflict',
      422: 'Unprocessable Entity',
      500: 'Internal Server Error'
    };

    return labels[status] ?? 'Request Failed';
  }

  private errorDetails(data: unknown): string[] {
    if (!this.isRecord(data)) {
      return [];
    }

    return Object.entries(data)
      .filter(([key, value]) => key !== 'exception' && value !== null && value !== undefined && String(value).trim().length > 0)
      .map(([key, value]) => `${this.formatColumnLabel(key)}: ${String(value).trim()}`);
  }

  private exceptionName(data: unknown): string | null {
    if (!this.isRecord(data)) {
      return null;
    }

    const value = data['exception'];
    if (typeof value !== 'string' || !value.trim()) {
      return null;
    }

    return value.trim();
  }

  private formatTimestamp(value: string): string {
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) {
      return value;
    }

    return new Intl.DateTimeFormat('en-IN', {
      year: 'numeric',
      month: 'short',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false
    }).format(date);
  }

  private footerMessage(status: number): string {
    if (status === 404) {
      return 'The server could not find the requested resource. Verify the ID and try again.';
    }

    if (status === 400 || status === 422) {
      return 'The request data was rejected by the server. Review the inputs and try again.';
    }

    if (status === 401 || status === 403) {
      return 'Access to this resource was blocked. Check the current user permissions and try again.';
    }

    if (status === 409) {
      return 'The request conflicts with existing data. Review the current record state and retry.';
    }

    return 'The request could not be completed successfully. Review the details and try again.';
  }
}
