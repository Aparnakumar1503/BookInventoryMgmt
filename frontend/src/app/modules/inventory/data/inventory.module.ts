import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const INVENTORY_MODULE: ModuleConfig = {
  id: 'inventory',
  name: 'Inventory Management',
  tagline: 'Inventory copy lookup, creation, purchase marking, and condition reference APIs.',
  description: 'This module manages book inventory copies, custom availability queries, and the condition reference data used across the inventory workflow.',
  owner: TEAMMATES[2],
  endpoints: [
    {
      id: 'list-inventory',
      name: 'List Inventory',
      method: 'GET',
      path: '/api/v1/inventory',
      description: 'Fetch all inventory records.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-inventory',
      name: 'Get Inventory',
      method: 'GET',
      path: '/api/v1/inventory/{inventoryId}',
      description: 'Fetch one inventory record by ID.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Inventory ID must be greater than 0.' }]
    },
    {
      id: 'get-inventory-by-book',
      name: 'Get Inventory By ISBN',
      method: 'GET',
      path: '/api/v1/inventory/books/{isbn}',
      description: 'Fetch all inventory copies for a book ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }],
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-available-inventory-by-book',
      name: 'Get Available Inventory',
      method: 'GET',
      path: '/api/v1/inventory/books/{isbn}/available',
      description: 'Fetch only the available inventory copies for a book ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }],
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
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
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 },
          { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6', min: 1, minMessage: 'Condition rank must be greater than 0.' }
        ]
      }
    },
    {
      id: 'update-inventory',
      name: 'Update Inventory',
      method: 'PUT',
      path: '/api/v1/inventory/{inventoryId}',
      description: 'Update an existing inventory copy.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Inventory ID must be greater than 0.' }],
      prefill: {
        endpointId: 'get-inventory',
        lockWhenFieldTrue: 'purchased',
        lockedMessage: 'This inventory copy has already been purchased and cannot be updated.'
      },
      body: {
        title: 'Updated Inventory Payload',
        fields: [
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 },
          { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6', min: 1, minMessage: 'Condition rank must be greater than 0.' }
        ]
      }
    },
    {
      id: 'purchase-inventory',
      name: 'Purchase Inventory',
      method: 'PUT',
      path: '/api/v1/inventory/{inventoryId}/purchase',
      description: 'Mark an inventory copy as purchased.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Inventory ID must be greater than 0.' }]
    },
    {
      id: 'delete-inventory',
      name: 'Delete Inventory',
      method: 'DELETE',
      path: '/api/v1/inventory/{inventoryId}',
      description: 'Delete an inventory copy by ID.',
      pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Inventory ID must be greater than 0.' }]
    },
    {
      id: 'list-book-conditions',
      name: 'List Book Conditions',
      method: 'GET',
      path: '/api/v1/book-conditions',
      description: 'Fetch all book condition references.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-book-condition',
      name: 'Get Book Condition',
      method: 'GET',
      path: '/api/v1/book-conditions/{rank}',
      description: 'Fetch one book condition by rank.',
      pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6', min: 1, minMessage: 'Rank must be greater than 0.' }]
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
          { key: 'ranks', label: 'Rank', type: 'number', required: true, placeholder: '6', min: 1, minMessage: 'Rank must be greater than 0.' },
          { key: 'description', label: 'Description', type: 'text', required: true, placeholder: 'Like New', maxLength: 100 },
          { key: 'fullDescription', label: 'Full Description', type: 'textarea', placeholder: 'Brand new or like new condition.', maxLength: 255 },
          { key: 'price', label: 'Price', type: 'number', required: true, placeholder: '30', min: 0.01, minMessage: 'Price must be greater than 0.' }
        ]
      }
    },
    {
      id: 'update-book-condition',
      name: 'Update Book Condition',
      method: 'PUT',
      path: '/api/v1/book-conditions/{rank}',
      description: 'Update an existing book condition reference.',
      pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6', min: 1, minMessage: 'Rank must be greater than 0.' }],
      prefill: {
        endpointId: 'get-book-condition',
        bodyFieldMap: {
          description: 'description',
          fullDescription: 'fullDescription',
          price: 'price'
        }
      },
      body: {
        title: 'Updated Book Condition Payload',
        fields: [
          { key: 'description', label: 'Description', type: 'text', required: true, placeholder: 'Like New', maxLength: 100 },
          { key: 'fullDescription', label: 'Full Description', type: 'textarea', placeholder: 'Brand new or like new condition.', maxLength: 255 },
          { key: 'price', label: 'Price', type: 'number', required: true, placeholder: '30', min: 0.01, minMessage: 'Price must be greater than 0.' }
        ]
      }
    },
    {
      id: 'delete-book-condition',
      name: 'Delete Book Condition',
      method: 'DELETE',
      path: '/api/v1/book-conditions/{rank}',
      description: 'Delete a book condition reference by rank.',
      pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6', min: 1, minMessage: 'Rank must be greater than 0.' }]
    }
  ]
};
