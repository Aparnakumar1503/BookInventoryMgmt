import { Component, inject } from '@angular/core';
import { ModuleRegistryService } from '../../core/services/module-registry.service';
import { ExplorerShellComponent } from '../../shared/components/explorer-shell/explorer-shell.component';

@Component({
  selector: 'app-authors',
  imports: [ExplorerShellComponent],
  templateUrl: './authors.component.html',
  styleUrl: './authors.component.scss'
})
export class AuthorsComponent {
  readonly module = inject(ModuleRegistryService).getModule('authors')!;
}
