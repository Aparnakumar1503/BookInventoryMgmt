export interface ApiResponse<T> {
  data: T;
  message?: string;
  success?: boolean;
}

export interface RequestResult {
  status: number;
  ok: boolean;
  url: string;
  body: unknown;
  durationMs: number;
  timestamp: string;
}
