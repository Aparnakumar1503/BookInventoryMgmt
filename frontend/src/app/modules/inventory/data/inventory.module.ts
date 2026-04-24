import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const INVENTORY_MODULE: ModuleConfig = {
  id: 'inventory',
  name: 'Inventory Management',
  tagline: 'Inventory copy lookup, availability queries, CRUD operations, purchase marking, and condition management APIs.',
  description: 'This module manages inventory copies and book-condition operations, including the available-copy query used during checkout flow demonstrations.',
  owner: TEAMMATES[2],
  endpoints: [
    {
      id: 'list-inventory',
      name: 'List Inventory',
      method: 'GET',
      path: '/api/v1/inventory',
      description: 'Fetch all inventory records.'
    },
    {
      id: 'get-inventory',
      name: 'Get Inventory',
      method: 'GET',
      path: '/api/v1/inventory/{inventoryId}',
      description: 'Fetch one inventory record by ID.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
    },
    {
      id: 'get-inventory-by-book',
      name: 'Get Inventory By ISBN',
      method: 'GET',
      path: '/api/v1/inventory/books/{isbn}',
      description: 'Fetch all inventory copies for a book ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
    },
    {
      id: 'get-available-inventory-by-book',
      name: 'Get Available Inventory',
      method: 'GET',
      path: '/api/v1/inventory/books/{isbn}/available',
      description: 'Fetch only available inventory copies for a book ISBN using the custom query endpoint.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
    },
    {
      id: 'create-inventory',
      name: 'Create Inventory',
      method: 'POST',
      path: '/api/v1/inventory',
      description: 'Create a new inventory copy.',
      body: {
        title: 'Inventory Payload',
        fields: [
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
          { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6' }
        ]
      }
    },
    {
      id: 'update-inventory',
      name: 'Update Inventory',
      method: 'PUT',
      path: '/api/v1/inventory/{inventoryId}',
      description: 'Update an existing inventory copy.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }],
      body: {
        title: 'Updated Inventory Payload',
        fields: [
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
          { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6' },
          { key: 'purchased', label: 'Purchased', type: 'boolean' }
        ]
      }
    },
    {
      id: 'purchase-inventory',
      name: 'Purchase Inventory',
      method: 'PUT',
      path: '/api/v1/inventory/{inventoryId}/purchase',
      description: 'Mark an inventory copy as purchased.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
    },
    {
      id: 'delete-inventory',
      name: 'Delete Inventory',
      method: 'DELETE',
      path: '/api/v1/inventory/{inventoryId}',
      description: 'Delete an inventory copy by ID.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
    },
    {
      id: 'list-book-conditions',
      name: 'List Book Conditions',
      method: 'GET',
      path: '/api/v1/book-conditions',
      description: 'Fetch all book condition references.'
    },
    {
      id: 'get-book-condition',
      name: 'Get Book Condition',
      method: 'GET',
      path: '/api/v1/book-conditions/{rank}',
      description: 'Fetch one book condition by rank.',
      pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }]
    },
    {
      id: 'create-book-condition',
      name: 'Create Book Condition',
      method: 'POST',
      path: '/api/v1/book-conditions',
      description: 'Create a new book condition reference.',
      body: {
        title: 'Book Condition Payload',
        fields: [
          { key: 'ranks', label: 'Rank', type: 'number', required: true, placeholder: '6' },
          { key: 'description', label: 'Description', type: 'text', required: true, placeholder: 'Like New' },
          { key: 'fullDescription', label: 'Full Description', type: 'textarea', placeholder: 'Nearly unused copy with clean pages.' },
          { key: 'price', label: 'Price', type: 'number', required: true, placeholder: '399.99' }
        ]
      }
    },
    {
      id: 'update-book-condition',
      name: 'Update Book Condition',
      method: 'PUT',
      path: '/api/v1/book-conditions/{rank}',
      description: 'Update an existing book condition reference.',
      pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }],
      body: {
        title: 'Updated Book Condition Payload',
        fields: [
          { key: 'ranks', label: 'Rank', type: 'number', required: true, placeholder: '6' },
          { key: 'description', label: 'Description', type: 'text', required: true, placeholder: 'Like New' },
          { key: 'fullDescription', label: 'Full Description', type: 'textarea', placeholder: 'Updated details for this condition.' },
          { key: 'price', label: 'Price', type: 'number', required: true, placeholder: '399.99' }
        ]
      }
    },
    {
      id: 'delete-book-condition',
      name: 'Delete Book Condition',
      method: 'DELETE',
      path: '/api/v1/book-conditions/{rank}',
      description: 'Delete a book condition reference by rank.',
      pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }]
    }
  ]
};
