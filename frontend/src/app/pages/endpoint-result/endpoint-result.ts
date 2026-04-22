import { DecimalPipe } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { EndpointRequestPayload } from '../../core/models/endpoint.model';
import { ApiCallResult, StoredRequestContext } from '../../core/models/response.model';
import { ApiService } from '../../core/services/api.service';
import { ModuleService } from '../../core/services/module.service';
import { NotificationService } from '../../core/services/notification.service';
import { StorageService } from '../../core/services/storage.service';
import { ResponseViewerComponent } from '../../shared/component/response-viewer/response-viewer';

type ViewMode = 'table' | 'preview' | 'json';

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
    if (paginated) {
      return this.normalizeRows(paginated.items);
    }

    return this.normalizeRows(this.responseData());
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

  readonly canPaginate = computed(() => Boolean(
    this.response()?.ok &&
    this.endpoint()?.method === 'GET' &&
    this.paginatedData() &&
    this.requestContext()
  ));

  readonly previewSupported = computed(() => {
    const endpointId = this.requestContext()?.endpointId;
    return endpointId === 'list-books' || endpointId === 'get-book';
  });

  readonly bookPreviewItems = computed<BookPreviewItem[]>(() => {
    if (!this.previewSupported()) {
      return [];
    }

    const paginated = this.paginatedData();
    const source = paginated ? paginated.items : this.responseData();
    return this.normalizeRows(source)
      .filter((item) => typeof item['title'] === 'string' && typeof item['isbn'] === 'string')
      .map((item) => ({
        isbn: String(item['isbn'] ?? ''),
        title: String(item['title'] ?? 'Untitled'),
        description: String(item['description'] ?? 'No description available.'),
        edition: String(item['edition'] ?? 'N/A'),
        categoryName: String(item['categoryName'] ?? item['categoryId'] ?? 'Uncategorized'),
        publisherName: String(item['publisherName'] ?? item['publisherId'] ?? 'Unknown publisher')
      }));
  });

  readonly activeViewLabel = computed(() => {
    if (this.viewMode() === 'preview') {
      return 'Preview UI';
    }

    return this.viewMode() === 'json' ? 'Raw JSON' : 'Table';
  });

  constructor() {
    if (!this.previewSupported()) {
      this.viewMode.set('table');
    }
  }

  setViewMode(mode: ViewMode): void {
    if (mode === 'preview' && !this.previewSupported()) {
      return;
    }

    this.viewMode.set(mode);
  }

  goToPreviousPage(): void {
    const page = this.paginatedData()?.page ?? 0;
    if (page > 0) {
      this.reloadPage(page - 1);
    }
  }

  goToNextPage(): void {
    const paginated = this.paginatedData();
    if (paginated?.hasNext) {
      this.reloadPage(paginated.page + 1);
    }
  }

  formatColumnLabel(column: string): string {
    return column
      .replace(/([a-z0-9])([A-Z])/g, '$1 $2')
      .replace(/[_-]+/g, ' ')
      .replace(/\b\w/g, (char) => char.toUpperCase());
  }

  renderCellValue(row: Record<string, unknown>, column: string): string {
    const value = row[column];
    if (value === null || value === undefined || value === '') {
      return '—';
    }

    if (typeof value === 'object') {
      return JSON.stringify(value);
    }

    return String(value);
  }

  trackByColumn(index: number, column: string): string {
    return `${index}-${column}`;
  }

  trackByBook(index: number, book: BookPreviewItem): string {
    return `${index}-${book.isbn}`;
  }

  bookAccent(book: BookPreviewItem): string {
    const seed = book.title.length + book.isbn.length;
    const variants = [
      'from-blue-600 via-cyan-500 to-teal-400',
      'from-emerald-600 via-teal-500 to-lime-400',
      'from-amber-600 via-orange-500 to-rose-400',
      'from-slate-700 via-blue-700 to-cyan-500'
    ];

    return variants[seed % variants.length] ?? variants[0];
  }

  bookInitials(title: string): string {
    return title
      .split(/\s+/)
      .filter(Boolean)
      .slice(0, 2)
      .map((part) => part[0]?.toUpperCase() ?? '')
      .join('') || 'BK';
  }

  private reloadPage(page: number): void {
    const context = this.requestContext();
    const endpoint = this.endpoint();
    const paginated = this.paginatedData();

    if (!context || !endpoint || !paginated) {
      return;
    }

    const payload: EndpointRequestPayload = {
      ...context.payload,
      queryParams: {
        ...context.payload.queryParams,
        page,
        size: paginated.size || 10
      }
    };

    this.isLoadingPage.set(true);
    this.apiService.execute(endpoint, payload)
      .pipe(finalize(() => this.isLoadingPage.set(false)))
      .subscribe((result) => {
        this.response.set(result);

        if (result.ok) {
          const nextContext: StoredRequestContext = {
            ...context,
            payload
          };
          this.requestContext.set(nextContext);
          this.storageService.setLastResponse(result, nextContext);
          return;
        }

        this.notificationService.error('Could not load the requested page.');
      });
  }

  private normalizeRows(value: unknown): Record<string, unknown>[] {
    if (Array.isArray(value)) {
      return value.filter((item): item is Record<string, unknown> => this.isRecord(item));
    }

    if (this.isRecord(value)) {
      return [value];
    }

    return [];
  }

  private isRecord(value: unknown): value is Record<string, unknown> {
    return typeof value === 'object' && value !== null && !Array.isArray(value);
  }

  private toNumber(value: unknown): number {
    return typeof value === 'number' ? value : Number(value ?? 0);
  }
}
