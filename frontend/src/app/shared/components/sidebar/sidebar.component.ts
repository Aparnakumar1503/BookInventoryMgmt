import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ExplorerEndpoint, ExplorerModule } from '../../../core/models/explorer.model';
import { MethodPillComponent } from '../method-pill/method-pill.component';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, MethodPillComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  @Input({ required: true }) module!: ExplorerModule;
  @Input({ required: true }) activeEndpointId!: string;
  @Output() endpointSelected = new EventEmitter<ExplorerEndpoint>();

  groupedEndpoints(): { title: string; items: ExplorerEndpoint[] }[] {
    const groups = new Map<string, ExplorerEndpoint[]>();

    for (const endpoint of this.module.endpoints) {
      const list = groups.get(endpoint.group) ?? [];
      list.push(endpoint);
      groups.set(endpoint.group, list);
    }

    return Array.from(groups.entries()).map(([title, items]) => ({ title, items }));
  }
}
