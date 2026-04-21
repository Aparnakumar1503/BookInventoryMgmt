import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './core/services/auth.service';
import { NotificationService } from './core/services/notification.service';
import { NavbarComponent } from './shared/component/navbar/navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html'
})
export class App {
  constructor(
    readonly notificationService: NotificationService,
    private readonly authService: AuthService
  ) {
    if (this.authService.isAuthenticated()) {
      this.authService.restoreSession().subscribe({ error: () => this.authService.logout(false) });
    }
  }
}
