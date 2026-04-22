import { Injectable, signal } from '@angular/core';
import { ApiCallResult } from '../models/response.model';

const LAST_RESPONSE_KEY = 'book-inventory:last-response';
const LAST_MODULE_KEY = 'book-inventory:last-module';

@Injectable({ providedIn: 'root' })
export class StorageService {
  private readonly lastResponseSignal = signal<ApiCallResult | null>(this.read<ApiCallResult>(LAST_RESPONSE_KEY));

  readonly lastResponse = this.lastResponseSignal.asReadonly();

  setLastResponse(result: ApiCallResult, moduleId: string): void {
    this.lastResponseSignal.set(result);
    this.write(LAST_RESPONSE_KEY, result);
    this.write(LAST_MODULE_KEY, moduleId);
  }

  getLastModuleId(): string | null {
    return this.read<string>(LAST_MODULE_KEY);
  }

  clearLastResponse(): void {
    this.lastResponseSignal.set(null);
    localStorage.removeItem(LAST_RESPONSE_KEY);
    localStorage.removeItem(LAST_MODULE_KEY);
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
      this.lastResponseSignal.set(null);
    }
  }
}
