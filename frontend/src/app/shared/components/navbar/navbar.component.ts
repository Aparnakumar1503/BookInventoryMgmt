import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  private readonly router = inject(Router);
  readonly showBack = computed(() => this.router.url !== '/');

  goBack(): void {
    if (this.router.url.startsWith('/modules/')) {
      void this.router.navigate(['/modules']);
      return;
    }

    void this.router.navigate(['/']);
  }
}
