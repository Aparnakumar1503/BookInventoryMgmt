import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificationService } from './core/services/notification.service';
import { NavbarComponent } from './shared/component/navbar/navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html'
})
export class App {
  constructor(readonly notificationService: NotificationService) {}
}
