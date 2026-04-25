import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleService } from '../../core/services/module.service';

@Component({
  selector: 'app-modules',
  imports: [RouterLink],
  templateUrl: './modules.component.html',
  styleUrl: './modules.component.scss'
})
export class ModulesComponent {
  private readonly moduleService = inject(ModuleService);

  readonly modules = this.moduleService.getModules();
  readonly stats = this.moduleService.getStats();
}
