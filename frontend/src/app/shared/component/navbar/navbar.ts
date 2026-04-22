import { Component, computed, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ModuleService } from '../../../core/services/module.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html'
})
export class NavbarComponent {
  private readonly moduleService = inject(ModuleService);
  private readonly authService = inject(AuthService);

  readonly currentUser = this.authService.currentUser;
  readonly modules = computed(() => this.moduleService.getModulesByIds(this.currentUser()?.modules ?? []));

  logout(): void {
    this.authService.logout();
  }
}
