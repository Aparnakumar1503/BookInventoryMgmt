import { Component, computed, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { ModuleService } from '../../core/services/module.service';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);
  private readonly moduleService = inject(ModuleService);
  private readonly notificationService = inject(NotificationService);

  readonly isSubmitting = signal(false);
  readonly moduleId = this.route.snapshot.paramMap.get('moduleId') ?? '';
  readonly module = computed(() => this.moduleService.getModule(this.moduleId));

  readonly form = new FormGroup({
    username: new FormControl(this.module()?.owner.username ?? '', { nonNullable: true, validators: [Validators.required] }),
    password: new FormControl('password', { nonNullable: true, validators: [Validators.required] })
  });

  signIn(): void {
    const selectedModule = this.module();
    if (this.form.invalid || !selectedModule) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    this.isSubmitting.set(true);

    this.authService.login(value.username, value.password)
      .pipe(finalize(() => this.isSubmitting.set(false)))
      .subscribe({
        next: () => {
          this.notificationService.success('Login successful.');
          void this.router.navigate(['/member', selectedModule.id]);
        },
        error: () => this.notificationService.error('Login failed. Check the username and password.')
      });
  }

  fillDemo(): void {
    const selectedModule = this.module();
    if (!selectedModule) {
      return;
    }

    this.form.patchValue({
      username: selectedModule.owner.username,
      password: 'password'
    });
  }
}
