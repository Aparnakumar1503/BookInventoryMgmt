import { Component, inject } from '@angular/core';
import { ModuleRegistryService } from '../../core/services/module-registry.service';
import { ExplorerShellComponent } from '../../shared/components/explorer-shell/explorer-shell.component';

@Component({
  selector: 'app-orders',
  imports: [ExplorerShellComponent],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent {
  readonly module = inject(ModuleRegistryService).getModule('orders')!;
}
