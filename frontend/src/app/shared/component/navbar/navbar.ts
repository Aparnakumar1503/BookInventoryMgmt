import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleService } from '../../../core/services/module.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html'
})
export class NavbarComponent {
  private readonly moduleService = inject(ModuleService);

  readonly modules = this.moduleService.getModules();
}
