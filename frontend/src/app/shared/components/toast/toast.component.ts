import { Component, Input, inject } from '@angular/core';
import { NotificationService } from '../../../core/services/notification.service';

interface ToastMessage {
  id: number;
  type: 'success' | 'error' | 'info';
  message: string;
}

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.scss'
})
export class ToastComponent {
  private readonly notificationService = inject(NotificationService);

  @Input({ required: true }) messages: readonly ToastMessage[] = [];

  dismiss(id: number): void {
    this.notificationService.dismiss(id);
  }
}
