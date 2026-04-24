import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ExplorerEndpoint } from '../models/explorer.model';
import { RequestResult } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class BaseApiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiBaseUrl;

  execute(
    endpoint: ExplorerEndpoint,
    payload: {
      pathParams: Record<string, string | number>;
      queryParams: Record<string, string | number>;
      body: Record<string, unknown> | null;
    }
  ): Observable<RequestResult> {
    const start = performance.now();
    const url = `${this.apiUrl}${this.resolvePath(endpoint.path, payload.pathParams)}`;
    const params = Object.entries(payload.queryParams).reduce((httpParams, [key, value]) => {
      if (value === '' || value === null || value === undefined) {
        return httpParams;
      }

      return httpParams.set(key, String(value));
    }, new HttpParams());

    const request = endpoint.method === 'GET' || endpoint.method === 'DELETE'
      ? this.http.request<unknown>(endpoint.method, url, { params, observe: 'response' })
      : this.http.request<unknown>(endpoint.method, url, { params, body: payload.body ?? {}, observe: 'response' });

    return request.pipe(
      map((response) => this.toResult(response, start)),
      catchError((error) => of(this.toError(error, url, start)))
    );
  }

  private resolvePath(path: string, pathParams: Record<string, string | number>): string {
    return Object.entries(pathParams).reduce(
      (currentPath, [key, value]) => currentPath.replace(`{${key}}`, encodeURIComponent(String(value))),
      path
    );
  }

  private toResult(response: HttpResponse<unknown>, start: number): RequestResult {
    return {
      status: response.status,
      ok: response.ok,
      url: response.url ?? '',
      body: response.body,
      durationMs: Math.round(performance.now() - start),
      timestamp: new Date().toISOString()
    };
  }

  private toError(error: unknown, fallbackUrl: string, start: number): RequestResult {
    const typedError = error as {
      status?: number;
      url?: string;
      error?: unknown;
      message?: string;
    };

    return {
      status: typedError.status ?? 0,
      ok: false,
      url: typedError.url ?? fallbackUrl,
      body: typedError.error ?? typedError.message ?? 'Unexpected request failure',
      durationMs: Math.round(performance.now() - start),
      timestamp: new Date().toISOString()
    };
  }
}
