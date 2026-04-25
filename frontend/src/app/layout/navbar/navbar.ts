import { Component, computed, inject, signal } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { filter, startWith } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { ModuleService } from '../../core/services/module.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.html'
})
export class NavbarComponent {
  private readonly authService = inject(AuthService);
  private readonly moduleService = inject(ModuleService);
  private readonly router = inject(Router);

  readonly currentUser = this.authService.currentUser;
  readonly menuOpen = signal(false);
  readonly accessibleModules = computed(() => {
    const user = this.currentUser();
    if (!user) {
      return [];
    }

    return user.modules
      .map((moduleId) => this.moduleService.getModule(moduleId))
      .filter((module): module is NonNullable<typeof module> => Boolean(module));
  });

  constructor() {
    this.router.events
      .pipe(
        filter((event): event is NavigationEnd => event instanceof NavigationEnd),
        startWith(null)
      )
      .subscribe(() => this.closeMenu());
  }

  logout(): void {
    this.menuOpen.set(false);
    this.authService.logout();
  }

  toggleMenu(): void {
    this.menuOpen.update((value) => !value);
  }

  closeMenu(): void {
    this.menuOpen.set(false);
  }
}
