import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import { ModuleService } from '../../../../core/services/module.service';
import { EndpointGroup, groupEndpoints } from '../../../../core/utils/endpoint-groups';

@Component({
  selector: 'app-module-home',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './module-home.html',
  styleUrl: './module-home.css'
})
export class ModuleHomeComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly moduleService = inject(ModuleService);

  readonly moduleId = computed(() => this.route.snapshot.paramMap.get('id') ?? '');
  readonly module = computed(() => this.moduleService.getModule(this.moduleId()));
  readonly endpointGroups = computed<EndpointGroup[]>(() => {
    const selectedModule = this.module();
    return selectedModule ? groupEndpoints(selectedModule.endpoints, selectedModule.id) : [];
  });

  methodClass(method: string): string {
    const classes: Record<string, string> = {
      GET: 'bg-blue-100 text-blue-800',
      POST: 'bg-teal-100 text-teal-800',
      PUT: 'bg-amber-100 text-amber-800',
      PATCH: 'bg-violet-100 text-violet-800',
      DELETE: 'bg-rose-100 text-rose-800'
    };

    return classes[method] ?? 'bg-slate-100 text-slate-800';
  }
}
