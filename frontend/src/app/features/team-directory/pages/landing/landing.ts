import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ModuleService } from '../../../../core/services/module.service';
import { TeammateCardComponent } from '../../../../shared/ui/teammate-card/teammate-card';

@Component({
  selector: 'app-landing',
  imports: [RouterLink, TeammateCardComponent],
  templateUrl: './landing.html',
  styleUrl: './landing.css'
})
export class LandingComponent {
  private readonly moduleService = inject(ModuleService);

  readonly teammates = this.moduleService.getTeammates();
  readonly modules = this.moduleService.getModules();
  readonly stats = this.moduleService.getStats();
}
