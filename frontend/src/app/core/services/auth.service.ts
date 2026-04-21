import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, map, of, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiEnvelope, AuthenticatedUser, AuthTokenPayload } from '../models/auth.model';

const TOKEN_KEY = 'book-inventory:token';
const EXPIRES_AT_KEY = 'book-inventory:expires-at';
const USER_KEY = 'book-inventory:user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly baseUrl = environment.apiBaseUrl;

  private readonly tokenSignal = signal<string | null>(this.read<string>(TOKEN_KEY));
  private readonly expiresAtSignal = signal<number | null>(this.read<number>(EXPIRES_AT_KEY));
  private readonly userSignal = signal<AuthenticatedUser | null>(this.read<AuthenticatedUser>(USER_KEY));

  readonly token = this.tokenSignal.asReadonly();
  readonly currentUser = this.userSignal.asReadonly();
  readonly isAuthenticated = computed(() => {
    const token = this.tokenSignal();
    const expiresAt = this.expiresAtSignal();
    return Boolean(token && expiresAt && expiresAt > Date.now());
  });

  login(username: string, password: string): Observable<AuthTokenPayload> {
    return this.http.post<ApiEnvelope<AuthTokenPayload>>(`${this.baseUrl}/api/v1/auth/login`, { username, password }).pipe(
      map((response) => response.data),
      tap((payload) => this.persist(payload))
    );
  }

  restoreSession(): Observable<AuthenticatedUser | null> {
    if (!this.isAuthenticated()) {
      this.clearSession();
      return of(null);
    }

    return this.http.get<ApiEnvelope<AuthenticatedUser>>(`${this.baseUrl}/api/v1/auth/me`).pipe(
      map((response) => response.data),
      tap((user) => {
        this.userSignal.set(user);
        this.write(USER_KEY, user);
      })
    );
  }

  logout(redirect = true): void {
    this.clearSession();
    if (redirect) {
      void this.router.navigate(['/']);
    }
  }

  canAccessModule(moduleId: string): boolean {
    const user = this.userSignal();
    return Boolean(user?.modules.includes(moduleId));
  }

  private persist(payload: AuthTokenPayload): void {
    this.tokenSignal.set(payload.accessToken);
    this.expiresAtSignal.set(payload.expiresAt);
    this.userSignal.set(payload.user);

    this.write(TOKEN_KEY, payload.accessToken);
    this.write(EXPIRES_AT_KEY, payload.expiresAt);
    this.write(USER_KEY, payload.user);
  }

  private clearSession(): void {
    this.tokenSignal.set(null);
    this.expiresAtSignal.set(null);
    this.userSignal.set(null);

    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(EXPIRES_AT_KEY);
    localStorage.removeItem(USER_KEY);
  }

  private read<T>(key: string): T | null {
    try {
      const rawValue = localStorage.getItem(key);
      return rawValue ? (JSON.parse(rawValue) as T) : null;
    } catch {
      return null;
    }
  }

  private write<T>(key: string, value: T): void {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch {
      this.clearSession();
    }
  }
}
