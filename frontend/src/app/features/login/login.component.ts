import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { ModuleRegistryService } from '../../core/services/module-registry.service';

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
  private readonly registry = inject(ModuleRegistryService);

  readonly module = this.registry.getModule(this.route.snapshot.paramMap.get('moduleId'));
  readonly form = new FormGroup({
    username: new FormControl(this.module?.loginHint.username ?? 'admin', { nonNullable: true, validators: [Validators.required] }),
    password: new FormControl(this.module?.loginHint.password ?? 'admin123', { nonNullable: true, validators: [Validators.required] })
  });

  signIn(): void {
    if (this.form.invalid || !this.module) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    this.authService.login(value.username, value.password).subscribe(() => {
      void this.router.navigateByUrl(this.module?.route ?? '/modules');
    });
  }
}
