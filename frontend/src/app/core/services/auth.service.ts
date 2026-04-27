import { HttpClient } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiEnvelope, AuthenticatedUser } from '../models/auth.model';

const USER_KEY = 'book-inventory:user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly baseUrl = environment.apiBaseUrl;

  private readonly userSignal = signal<AuthenticatedUser | null>(this.read<AuthenticatedUser>(USER_KEY));

  readonly currentUser = this.userSignal.asReadonly();
  readonly isAuthenticated = computed(() => Boolean(this.userSignal()));

  login(username: string, password: string): Observable<AuthenticatedUser> {
    return this.http.post<ApiEnvelope<AuthenticatedUser>>(`${this.baseUrl}/api/v1/auth/login`, { username, password }).pipe(
      map((response) => response.data),
      tap((user) => this.persist(user))
    );
  }

  restoreSession(): Observable<AuthenticatedUser | null> {
    return this.http.get<ApiEnvelope<AuthenticatedUser>>(`${this.baseUrl}/api/v1/auth/me`).pipe(
      map((response) => response.data),
      tap((user) => {
        this.userSignal.set(user);
        this.write(USER_KEY, user);
      }),
      catchError(() => {
        this.clearSession();
        return of(null);
      })
    );
  }

  logout(redirect = true): void {
    this.http.post<ApiEnvelope<string>>(`${this.baseUrl}/api/v1/auth/logout`, {}).pipe(
      catchError(() => of(null))
    ).subscribe({
      next: () => this.finishLogout(redirect)
    });
  }

  canAccessModule(moduleId: string): boolean {
    const user = this.userSignal();
    return Boolean(user?.modules.includes(moduleId));
  }

  private persist(user: AuthenticatedUser): void {
    this.userSignal.set(user);
    this.write(USER_KEY, user);
  }

  private clearSession(): void {
    this.userSignal.set(null);
    localStorage.removeItem(USER_KEY);
  }

  private finishLogout(redirect: boolean): void {
    this.clearSession();
    if (redirect) {
      void this.router.navigate(['/']);
    }
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
