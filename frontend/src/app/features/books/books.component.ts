import { Component, inject } from '@angular/core';
import { ModuleRegistryService } from '../../core/services/module-registry.service';
import { ExplorerShellComponent } from '../../shared/components/explorer-shell/explorer-shell.component';

@Component({
  selector: 'app-books',
  imports: [ExplorerShellComponent],
  templateUrl: './books.component.html',
  styleUrl: './books.component.scss'
})
export class BooksComponent {
  readonly module = inject(ModuleRegistryService).getModule('books')!;
}
