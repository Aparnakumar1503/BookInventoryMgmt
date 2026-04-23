import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const ORDERS_MODULE: ModuleConfig = {
  id: 'orders',
  name: 'Cart and Order Processing',
  tagline: 'User cart browsing, item management, and checkout endpoints.',
  description: 'This module demonstrates user cart operations and order checkout flows, each protected for the owner responsible for order processing.',
  owner: TEAMMATES[3],
  endpoints: [
    {
      id: 'get-cart',
      name: 'Get Cart',
      method: 'GET',
      path: '/api/v1/cart/{userId}',
      description: 'Fetch the shopping cart for a user.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }]
    },
    {
      id: 'add-cart-item',
      name: 'Add Cart Item',
      method: 'POST',
      path: '/api/v1/cart/{userId}/items/{isbn}',
      description: 'Add one book to a user cart.',
      pathParams: [
        { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' },
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }
      ]
    },
    {
      id: 'delete-cart-item',
      name: 'Delete Cart Item',
      method: 'DELETE',
      path: '/api/v1/cart/{userId}/items/{isbn}',
      description: 'Delete one book from a user cart.',
      pathParams: [
        { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' },
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }
      ]
    },
    {
      id: 'checkout-order',
      name: 'Checkout Order',
      method: 'POST',
      path: '/api/v1/orders/checkout/{userId}',
      description: 'Checkout a selected inventory copy for a user.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }],
      body: {
        title: 'Checkout Payload',
        fields: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
      }
    },
    {
      id: 'get-orders',
      name: 'Get Orders By User',
      method: 'GET',
      path: '/api/v1/orders/user/{userId}',
      description: 'Fetch all purchases for a user.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }]
    }
  ]
};