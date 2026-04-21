import { Component, inject } from '@angular/core';
import { ModuleService } from '../../core/services/module.service';
import { TeammateCardComponent } from '../../shared/component/teammate-card/teammate-card';

@Component({
  selector: 'app-landing',
  imports: [TeammateCardComponent],
  templateUrl: './landing.html'
})
export class LandingComponent {
  private readonly moduleService = inject(ModuleService);

  readonly teammates = this.moduleService.getTeammates();
  readonly stats = this.moduleService.getStats();
}
