export interface AuthenticatedUser {
  readonly username: string;
  readonly firstName: string;
  readonly lastName: string;
  readonly role: string;
  readonly authorities: readonly string[];
  readonly modules: readonly string[];
}

export interface AuthTokenPayload {
  readonly tokenType: string;
  readonly accessToken: string;
  readonly expiresAt: number;
  readonly user: AuthenticatedUser;
}

export interface ApiEnvelope<T> {
  readonly statusCode: number;
  readonly message: string;
  readonly data: T;
  readonly timestamp: string;
}
