export interface ApiCallResult {
  readonly status: number;
  readonly ok: boolean;
  readonly url: string | null;
  readonly body: unknown;
  readonly receivedAt: string;
}

export interface StoredRequestContext {
  readonly moduleId: string;
  readonly endpointId: string;
  readonly payload: {
    readonly pathParams: Record<string, string | number | boolean>;
    readonly queryParams: Record<string, string | number | boolean>;
    readonly body: Record<string, string | number | boolean> | null;
  };
}

export interface ToastMessage {
  readonly id: number;
  readonly type: 'success' | 'error' | 'info';
  readonly message: string;
}
