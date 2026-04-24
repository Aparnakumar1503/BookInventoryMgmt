import { Injectable } from '@angular/core';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  getModule(): ExplorerModule {
    return {
      id: 'orders',
      title: 'Order Management',
      subtitle: 'Cart items, checkout, and purchase history.',
      description: 'Browse cart endpoints, submit checkout requests, and inspect order responses.',
      route: '/orders',
      icon: '🛒',
      accent: 'peach',
      loginHint: { username: 'admin', password: 'admin123' },
      endpoints: [
        { id: 'get-cart', title: 'Get Cart', method: 'GET', path: '/cart/{userId}', description: 'Fetch one user cart.', group: 'Cart', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1' }] },
        { id: 'add-to-cart', title: 'Add Cart Item', method: 'POST', path: '/cart/{userId}/items/{isbn}', description: 'Add an item to cart.', group: 'Cart', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true }, { key: 'isbn', label: 'ISBN', type: 'text', required: true }] },
        { id: 'checkout', title: 'Checkout', method: 'POST', path: '/orders/checkout/{userId}', description: 'Checkout one inventory item.', group: 'Orders', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true }], bodyFields: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true }] },
        { id: 'purchase-history', title: 'Purchase History', method: 'GET', path: '/orders/user/{userId}', description: 'Fetch order history.', group: 'Orders', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true }] }
      ]
    };
  }
}
