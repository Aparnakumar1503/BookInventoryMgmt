export interface ApiCallResult {
  readonly status: number;
  readonly ok: boolean;
  readonly url: string | null;
  readonly body: unknown;
  readonly receivedAt: string;
}

export interface ToastMessage {
  readonly id: number;
  readonly type: 'success' | 'error' | 'info';
  readonly message: string;
}
