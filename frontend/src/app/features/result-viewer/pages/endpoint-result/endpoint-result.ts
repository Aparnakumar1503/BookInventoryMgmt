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

@Component({
  selector: 'app-endpoint-result',
  imports: [RouterLink, ResponseViewerComponent, DecimalPipe],
  templateUrl: './endpoint-result.html'
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
  readonly viewMode = signal<ViewMode>('table');

  readonly endpoint = computed(() => {
    const context = this.requestContext();
    return context ? this.moduleService.getEndpoint(context.moduleId, context.endpointId) ?? null : null;
  });

  readonly responseBody = computed<ApiEnvelope | null>(() => {
    const body = this.response()?.body;
    return this.isRecord(body) ? body as ApiEnvelope : null;
  });

  readonly responseData = computed<unknown>(() => this.responseBody()?.data ?? null);

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

  readonly previewSupported = computed(() => this.previewType() !== null);

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

  readonly rolePreviewItems = computed(() => this.previewType() === 'roles'
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

  bookAccent(book: BookPreviewItem): string {
    const seed = book.title.length + book.isbn.length;
    const variants = ['from-blue-700 via-sky-600 to-cyan-500', 'from-emerald-700 via-teal-600 to-lime-500', 'from-amber-700 via-orange-600 to-rose-500', 'from-slate-700 via-blue-800 to-cyan-600'];
    return variants[seed % variants.length] ?? variants[0];
  }

  bookInitials(title: string): string {
    return title.split(/\s+/).filter(Boolean).slice(0, 2).map((part) => part[0]?.toUpperCase() ?? '').join('') || 'BK';
  }

  previewInitials(value: string): string {
    return value.split(/\s+/).filter(Boolean).slice(0, 2).map((part) => part[0]?.toUpperCase() ?? '').join('') || 'BV';
  }

  ratingStars(rating: number): string[] {
    const filled = Math.max(0, Math.min(5, Math.round(rating / 2)));
    return Array.from({ length: 5 }, (_, index) => index < filled ? '*' : 'o');
  }

  inventoryTone(purchased: boolean): string {
    return purchased ? 'bg-emerald-100 text-emerald-800' : 'bg-amber-100 text-amber-800';
  }

  private reloadPage(page: number): void {
    const context = this.requestContext();
    const endpoint = this.endpoint();
    const paginated = this.paginatedData();
    if (!context || !endpoint || !paginated) return;

    const payload: EndpointRequestPayload = {
      ...context.payload,
      queryParams: { ...context.payload.queryParams, page, size: paginated.size || 10 }
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
}
