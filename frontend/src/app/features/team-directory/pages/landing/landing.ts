import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from '../../../../core/services/auth.service';
import { ModuleService } from '../../../../core/services/module.service';
import { NotificationService } from '../../../../core/services/notification.service';

@Component({
  selector: 'app-landing',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './landing.html',
  styleUrl: './landing.css'
})
export class LandingComponent {
  private readonly moduleService = inject(ModuleService);
  private readonly authService = inject(AuthService);
  private readonly notificationService = inject(NotificationService);
  private readonly router = inject(Router);

  readonly teammates = this.moduleService.getTeammates();
  readonly isSubmitting = signal(false);

  readonly loginForm = new FormGroup({
    username: new FormControl<string>('Aparna', { nonNullable: true, validators: [Validators.required] }),
    password: new FormControl<string>('password', { nonNullable: true, validators: [Validators.required] })
  });

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { username, password } = this.loginForm.getRawValue();
    this.isSubmitting.set(true);

    this.authService.login(username, password)
      .pipe(finalize(() => this.isSubmitting.set(false)))
      .subscribe({
        next: (user) => {
          this.notificationService.success('Login successful.');
          const destination = user.modules[0] ?? 'books';
          void this.router.navigate(['/member', destination]);
        },
        error: () => this.notificationService.error('Login failed. Check the username and password.')
      });
  }

  fillDemo(username: string): void {
    this.loginForm.patchValue({ username, password: 'password' });
  }
}
