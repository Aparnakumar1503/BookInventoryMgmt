import { Component, computed, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleService } from '../../../../core/services/module.service';

@Component({
  selector: 'app-overview',
  imports: [RouterLink],
  templateUrl: './overview.html',
  styleUrl: './overview.css'
})
export class OverviewComponent {
  private readonly moduleService = inject(ModuleService);

  readonly teammates = this.moduleService.getTeammates();
  readonly stats = this.moduleService.getStats();
  readonly coverageRows = computed(() =>
    this.moduleService.getModules().map((module, index) => ({
      index: index + 1,
      ownerName: module.owner.name,
      ownerUsername: module.owner.username,
      ownerRole: module.owner.role,
      moduleName: module.name,
      moduleSummary: module.tagline,
      endpointCount: module.endpoints.length
    }))
  );
}
