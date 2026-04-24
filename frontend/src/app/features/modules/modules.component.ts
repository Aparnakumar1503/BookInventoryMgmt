import { Component, inject } from '@angular/core';
import { DataCardComponent } from '../../shared/components/data-card/data-card.component';
import { ModuleRegistryService } from '../../core/services/module-registry.service';

@Component({
  selector: 'app-modules',
  imports: [DataCardComponent],
  templateUrl: './modules.component.html',
  styleUrl: './modules.component.scss'
})
export class ModulesComponent {
  readonly modules = inject(ModuleRegistryService).getModules();
}
