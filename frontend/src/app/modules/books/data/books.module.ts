import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const BOOKS_MODULE: ModuleConfig = {
  id: 'books',
  name: 'Book Catalog and References',
  tagline: 'Book CRUD, search filters, publishers, categories, and state reference APIs.',
  description: 'This module covers the full catalog experience from book CRUD through reference data reads and publisher maintenance.',
  owner: TEAMMATES[1],
  endpoints: [
    {
      id: 'list-books',
      name: 'List Books',
      method: 'GET',
      path: '/api/v1/books',
      description: 'Fetch all books or filter by category and publisher.',
      queryParams: [
        { key: 'categoryId', label: 'Category ID', type: 'number', placeholder: '3', min: 1, minMessage: 'Category ID must be greater than 0.' },
        { key: 'publisherId', label: 'Publisher ID', type: 'number', placeholder: '7', min: 1, minMessage: 'Publisher ID must be greater than 0.' },
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-book',
      name: 'Get Book',
      method: 'GET',
      path: '/api/v1/books/{isbn}',
      description: 'Fetch one book by ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }]
    },
    {
      id: 'create-book',
      name: 'Create Book',
      method: 'POST',
      path: '/api/v1/books',
      description: 'Create a new book.',
      body: {
        title: 'Book Payload',
        fields: [
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 },
          { key: 'title', label: 'Title', type: 'text', required: true, placeholder: 'Master Java', minLength: 2, maxLength: 100 },
          { key: 'description', label: 'Description', type: 'textarea', placeholder: 'Book description', maxLength: 500 },
          { key: 'categoryId', label: 'Category ID', type: 'number', required: true, placeholder: '3', min: 1, minMessage: 'Category ID must be greater than 0.' },
          { key: 'edition', label: 'Edition', type: 'text', required: true, placeholder: '1', minLength: 1, maxLength: 20 },
          { key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '7', min: 1, minMessage: 'Publisher ID must be greater than 0.' }
        ]
      }
    },
    {
      id: 'update-book',
      name: 'Update Book',
      method: 'PUT',
      path: '/api/v1/books/{isbn}',
      description: 'Update an existing book.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }],
      prefill: {
        endpointId: 'get-book'
      },
      body: {
        title: 'Updated Book Payload',
        fields: [
          { key: 'title', label: 'Title', type: 'text', required: true, minLength: 2, maxLength: 100 },
          { key: 'description', label: 'Description', type: 'textarea', maxLength: 500 },
          { key: 'categoryId', label: 'Category ID', type: 'number', required: true, min: 1, minMessage: 'Category ID must be greater than 0.' },
          { key: 'edition', label: 'Edition', type: 'text', required: true, minLength: 1, maxLength: 20 },
          { key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, min: 1, minMessage: 'Publisher ID must be greater than 0.' }
        ]
      }
    },
    {
      id: 'delete-book',
      name: 'Delete Book',
      method: 'DELETE',
      path: '/api/v1/books/{isbn}',
      description: 'Delete a book by ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 }]
    },
    {
      id: 'list-categories',
      name: 'List Categories',
      method: 'GET',
      path: '/api/v1/categories',
      description: 'Fetch all categories.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-category',
      name: 'Get Category',
      method: 'GET',
      path: '/api/v1/categories/{categoryId}',
      description: 'Fetch a category by ID.',
      pathParams: [{ key: 'categoryId', label: 'Category ID', type: 'number', required: true, placeholder: '3', min: 1, minMessage: 'Category ID must be greater than 0.' }]
    },
    {
      id: 'list-publishers',
      name: 'List Publishers',
      method: 'GET',
      path: '/api/v1/publishers',
      description: 'Fetch all publishers.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-publisher',
      name: 'Get Publisher',
      method: 'GET',
      path: '/api/v1/publishers/{publisherId}',
      description: 'Fetch one publisher by ID.',
      pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Publisher ID must be greater than 0.' }]
    },
    {
      id: 'create-publisher',
      name: 'Create Publisher',
      method: 'POST',
      path: '/api/v1/publishers',
      description: 'Create a publisher.',
      body: {
        title: 'Publisher Payload',
        fields: [
          { key: 'name', label: 'Name', type: 'text', required: true, placeholder: 'New Press', minLength: 2, maxLength: 100 },
          { key: 'city', label: 'City', type: 'text', placeholder: 'Rochester', maxLength: 100 },
          { key: 'stateCode', label: 'State Code', type: 'text', required: true, placeholder: 'NY', minLength: 2, maxLength: 10 }
        ]
      }
    },
    {
      id: 'update-publisher',
      name: 'Update Publisher',
      method: 'PUT',
      path: '/api/v1/publishers/{publisherId}',
      description: 'Update a publisher.',
      pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Publisher ID must be greater than 0.' }],
      prefill: {
        endpointId: 'get-publisher'
      },
      body: {
        title: 'Updated Publisher Payload',
        fields: [
          { key: 'name', label: 'Name', type: 'text', required: true, minLength: 2, maxLength: 100 },
          { key: 'city', label: 'City', type: 'text', maxLength: 100 },
          { key: 'stateCode', label: 'State Code', type: 'text', required: true, minLength: 2, maxLength: 10 }
        ]
      }
    },
    {
      id: 'delete-publisher',
      name: 'Delete Publisher',
      method: 'DELETE',
      path: '/api/v1/publishers/{publisherId}',
      description: 'Delete a publisher.',
      pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Publisher ID must be greater than 0.' }]
    },
    {
      id: 'list-states',
      name: 'List States',
      method: 'GET',
      path: '/api/v1/states',
      description: 'Fetch all state reference values.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-state',
      name: 'Get State',
      method: 'GET',
      path: '/api/v1/states/{code}',
      description: 'Fetch a state by code.',
      pathParams: [{ key: 'code', label: 'State Code', type: 'text', required: true, placeholder: 'NY', minLength: 2, maxLength: 10 }]
    }
  ]
};
