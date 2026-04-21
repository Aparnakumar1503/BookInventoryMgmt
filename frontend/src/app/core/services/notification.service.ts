import { Injectable, signal } from '@angular/core';
import { ToastMessage } from '../models/response.model';

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private nextId = 1;
  private readonly messagesSignal = signal<readonly ToastMessage[]>([]);

  readonly messages = this.messagesSignal.asReadonly();

  success(message: string): void {
    this.push('success', message);
  }

  error(message: string): void {
    this.push('error', message);
  }

  info(message: string): void {
    this.push('info', message);
  }

  dismiss(id: number): void {
    this.messagesSignal.update((messages) => messages.filter((message) => message.id !== id));
  }

  private push(type: ToastMessage['type'], message: string): void {
    const toast: ToastMessage = { id: this.nextId, type, message };
    this.nextId += 1;
    this.messagesSignal.update((messages) => [...messages, toast]);
    window.setTimeout(() => this.dismiss(toast.id), 3500);
  }
}
