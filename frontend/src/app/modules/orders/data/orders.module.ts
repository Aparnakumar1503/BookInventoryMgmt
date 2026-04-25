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
      id: 'list-cart',
      name: 'List All Cart Items',
      method: 'GET',
      path: '/api/v1/cart',
      description: 'Fetch all cart items across users.'
    },
    {
      id: 'get-cart',
      name: 'Get Cart',
      method: 'GET',
      path: '/api/v1/cart/{userId}',
      description: 'Fetch the shopping cart for a user.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' }]
    },
    {
      id: 'add-cart-item',
      name: 'Add Cart Item',
      method: 'POST',
      path: '/api/v1/cart',
      description: 'Add one book to a user cart.',
      body: {
        title: 'Cart Payload',
        fields: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' },
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }
        ]
      }
    },
    {
      id: 'delete-cart-item',
      name: 'Delete Cart Item',
      method: 'DELETE',
      path: '/api/v1/cart/{userId}/{isbn}',
      description: 'Delete one book from a user cart.',
      pathParams: [
        { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' },
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }
      ]
    },
    {
      id: 'list-orders',
      name: 'List All Orders',
      method: 'GET',
      path: '/api/v1/orders',
      description: 'Fetch all purchase records.'
    },
    {
      id: 'checkout-order',
      name: 'Create Purchase',
      method: 'POST',
      path: '/api/v1/orders',
      description: 'Create a purchase record for a user and inventory copy.',
      body: {
        title: 'Purchase Payload',
        fields: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' },
          { key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Inventory ID must be greater than 0.' }
        ]
      }
    },
    {
      id: 'get-orders',
      name: 'Get Orders By User',
      method: 'GET',
      path: '/api/v1/orders/user/{userId}',
      description: 'Fetch all purchases for a user.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' }]
    },
    {
      id: 'delete-order',
      name: 'Delete Order',
      method: 'DELETE',
      path: '/api/v1/orders/{userId}/{inventoryId}',
      description: 'Delete one purchase record by user and inventory ID.',
      pathParams: [
        { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' },
        { key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000', min: 1, minMessage: 'Inventory ID must be greater than 0.' }
      ]
    }
  ]
};
