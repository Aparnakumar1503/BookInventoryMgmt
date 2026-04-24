import { Component, inject } from '@angular/core';
import { ModuleRegistryService } from '../../core/services/module-registry.service';
import { ExplorerShellComponent } from '../../shared/components/explorer-shell/explorer-shell.component';

@Component({
  selector: 'app-inventory',
  imports: [ExplorerShellComponent],
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.scss'
})
export class InventoryComponent {
  readonly module = inject(ModuleRegistryService).getModule('inventory')!;
}
