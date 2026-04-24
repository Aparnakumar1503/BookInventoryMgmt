import { Injectable } from '@angular/core';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class InventoryService {
  getModule(): ExplorerModule {
    return {
      id: 'inventory',
      title: 'Inventory Management',
      subtitle: 'Stock records and condition tracking.',
      description: 'Monitor inventory copies, create stock entries, and update purchase status.',
      route: '/inventory',
      icon: '📦',
      accent: 'sand',
      loginHint: { username: 'admin', password: 'admin123' },
      endpoints: [
        { id: 'all-inventory', title: 'All Inventory', method: 'GET', path: '/inventory', description: 'Fetch all inventory.', group: 'Retrieve' },
        { id: 'inventory-by-id', title: 'Get Inventory', method: 'GET', path: '/inventory/{inventoryId}', description: 'Fetch one inventory record.', group: 'Retrieve', pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }] },
        { id: 'inventory-by-book', title: 'By ISBN', method: 'GET', path: '/inventory/books/{isbn}', description: 'Fetch inventory by book ISBN.', group: 'Retrieve', pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true }] },
        { id: 'create-inventory', title: 'Create Inventory', method: 'POST', path: '/inventory', description: 'Create one inventory item.', group: 'Manage', bodyFields: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true }, { key: 'ranks', label: 'Condition Rank', type: 'number', required: true }] }
      ]
    };
  }
}
