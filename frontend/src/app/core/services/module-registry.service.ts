import { Injectable, inject } from '@angular/core';
import { AuthorService } from './author.service';
import { BookService } from './book.service';
import { InventoryService } from './inventory.service';
import { OrderService } from './order.service';
import { ReviewService } from './review.service';
import { UserService } from './user.service';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class ModuleRegistryService {
  private readonly modules: ExplorerModule[] = [
    inject(AuthorService).getModule(),
    inject(UserService).getModule(),
    inject(BookService).getModule(),
    inject(ReviewService).getModule(),
    inject(OrderService).getModule(),
    inject(InventoryService).getModule()
  ];

  getModules(): ExplorerModule[] {
    return this.modules;
  }

  getModule(moduleId: string | null): ExplorerModule | undefined {
    return this.modules.find((module) => module.id === moduleId);
  }
}
