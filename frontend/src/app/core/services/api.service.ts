import { HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EndpointConfig, EndpointRequestPayload } from '../models/endpoint.model';
import { ApiCallResult } from '../models/response.model';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiBaseUrl;

  execute(endpoint: EndpointConfig, payload: EndpointRequestPayload): Observable<ApiCallResult> {
    const url = `${this.baseUrl}${this.resolvePath(endpoint.path, payload.pathParams)}`;
    const params = this.toHttpParams(payload.queryParams);
    const options = { observe: 'response' as const, params };

    const request$ = endpoint.method === 'GET' || endpoint.method === 'DELETE'
      ? this.http.request<unknown>(endpoint.method, url, options)
      : this.http.request<unknown>(endpoint.method, url, { ...options, body: payload.body ?? {} });

    return request$.pipe(
      map((response) => this.toResult(response)),
      catchError((error: unknown) => of(this.toErrorResult(error, url)))
    );
  }

  private resolvePath(path: string, pathParams: Record<string, string | number | boolean>): string {
    return Object.entries(pathParams).reduce(
      (resolvedPath, [key, value]) => resolvedPath.replace(`{${key}}`, encodeURIComponent(String(value))),
      path
    );
  }

  private toHttpParams(queryParams: Record<string, string | number | boolean>): HttpParams {
    return Object.entries(queryParams).reduce((params, [key, value]) => {
      if (value === '' || value === null || value === undefined) {
        return params;
      }

      return params.set(key, String(value));
    }, new HttpParams());
  }

  private toResult(response: HttpResponse<unknown>): ApiCallResult {
    return {
      status: response.status,
      ok: response.ok,
      url: response.url,
      body: response.body,
      receivedAt: new Date().toISOString()
    };
  }

  private toErrorResult(error: unknown, fallbackUrl: string): ApiCallResult {
    if (error instanceof HttpErrorResponse) {
      const message = error.status === 0
        ? 'Network error. Check that Spring Boot is running on port 8182 and CORS is enabled after backend restart.'
        : this.hasStructuredErrorBody(error.error)
          ? error.error
          : error.status === 401
            ? 'Unauthorized. Sign in again in this browser before retrying the endpoint.'
            : error.status === 403
              ? 'Forbidden. This account does not have access to the selected module endpoint.'
              : error.error || error.message;

      return {
        status: error.status,
        ok: false,
        url: error.url ?? fallbackUrl,
        body: message,
        receivedAt: new Date().toISOString()
      };
    }

    return {
      status: 0,
      ok: false,
      url: fallbackUrl,
      body: 'Unexpected request failure',
      receivedAt: new Date().toISOString()
    };
  }

  private hasStructuredErrorBody(value: unknown): value is { statusCode?: number; message?: string; data?: unknown } {
    return typeof value === 'object' && value !== null
      && ('statusCode' in value || 'message' in value || 'data' in value);
  }
}
