import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EndpointConfig } from '../../../../core/models/endpoint.model';
import { ModuleService } from '../../../../core/services/module.service';
import { EndpointCardComponent } from '../../../../shared/ui/endpoint-card/endpoint-card';

interface EndpointGroup {
  title: string;
  endpoints: readonly EndpointConfig[];
}

@Component({
  selector: 'app-module-home',
  imports: [EndpointCardComponent, RouterLink],
  templateUrl: './module-home.html'
})
export class ModuleHomeComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly moduleService = inject(ModuleService);

  readonly moduleId = computed(() => this.route.snapshot.paramMap.get('id') ?? '');
  readonly module = computed(() => this.moduleService.getModule(this.moduleId()));
  readonly endpointGroups = computed<EndpointGroup[]>(() => {
    const selectedModule = this.module();
    if (!selectedModule) {
      return [];
    }

    const groups = new Map<string, EndpointConfig[]>();

    selectedModule.endpoints.forEach((endpoint) => {
      const title = this.resolveGroupTitle(endpoint.path);
      const collection = groups.get(title) ?? [];
      collection.push(endpoint);
      groups.set(title, collection);
    });

    return Array.from(groups.entries()).map(([title, endpoints]) => ({ title, endpoints }));
  });

  private resolveGroupTitle(path: string): string {
    if (path.includes('/books') && path.includes('/authors')) return 'Book-Author Mapping';
    if (path.includes('/books') && path.includes('/reviews')) return 'Review APIs';
    if (path.includes('/books')) return 'Book APIs';
    if (path.includes('/authors')) return 'Author APIs';
    if (path.includes('/publishers')) return 'Publisher APIs';
    if (path.includes('/categories')) return 'Category APIs';
    if (path.includes('/states')) return 'Reference APIs';
    if (path.includes('/inventory')) return 'Inventory APIs';
    if (path.includes('/book-conditions')) return 'Book Condition APIs';
    if (path.includes('/users') && path.includes('/roles')) return 'Role APIs';
    if (path.includes('/users')) return 'User APIs';
    if (path.includes('/roles')) return 'Role APIs';
    if (path.includes('/orders')) return 'Order APIs';
    if (path.includes('/cart')) return 'Shopping Cart APIs';
    if (path.includes('/reviewers')) return 'Reviewer APIs';
    return 'Module APIs';
  }
}
