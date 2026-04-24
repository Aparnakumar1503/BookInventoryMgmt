import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleRegistryService } from '../../core/services/module-registry.service';

@Component({
  selector: 'app-welcome',
  imports: [RouterLink],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent {
  readonly modules = inject(ModuleRegistryService).getModules();
}
