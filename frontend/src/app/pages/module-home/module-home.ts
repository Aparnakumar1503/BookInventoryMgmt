import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ModuleService } from '../../core/services/module.service';
import { EndpointCardComponent } from '../../shared/component/endpoint-card/endpoint-card';

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
}
