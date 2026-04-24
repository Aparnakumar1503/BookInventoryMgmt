import { Component, inject } from '@angular/core';
import { ModuleRegistryService } from '../../core/services/module-registry.service';
import { ExplorerShellComponent } from '../../shared/components/explorer-shell/explorer-shell.component';

@Component({
  selector: 'app-reviews',
  imports: [ExplorerShellComponent],
  templateUrl: './reviews.component.html',
  styleUrl: './reviews.component.scss'
})
export class ReviewsComponent {
  readonly module = inject(ModuleRegistryService).getModule('reviews')!;
}
