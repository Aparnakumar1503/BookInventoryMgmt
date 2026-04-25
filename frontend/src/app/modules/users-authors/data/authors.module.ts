import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const AUTHORS_MODULE: ModuleConfig = {
  id: 'authors',
  name: 'Author and Mapping Management',
  tagline: 'Author CRUD and book-author relationship APIs.',
  description: 'This module keeps author records, photo metadata, and ISBN-author mapping workflows in one simpler workspace.',
  owner: TEAMMATES[0],
  endpoints: [
    {
      id: 'list-authors',
      name: 'List Authors',
      method: 'GET',
      path: '/api/v1/authors',
      description: 'Fetch all authors.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '0', min: 0, minMessage: 'Page must be 0 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'get-author',
      name: 'Get Author',
      method: 'GET',
      path: '/api/v1/authors/{authorId}',
      description: 'Fetch an author by author ID.',
      pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Author ID must be greater than 0.' }]
    },
    {
      id: 'create-author',
      name: 'Create Author',
      method: 'POST',
      path: '/api/v1/authors',
      description: 'Create a new author record.',
      body: {
        title: 'Author Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true, placeholder: 'Steve', minLength: 2, maxLength: 50, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'First name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true, placeholder: 'Jacobs', minLength: 1, maxLength: 50, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'Last name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'photo', label: 'Photo URL', type: 'text', placeholder: 'https://example.com/author.jpg', maxLength: 255 }
        ]
      }
    },
    {
      id: 'update-author',
      name: 'Update Author',
      method: 'PUT',
      path: '/api/v1/authors/{authorId}',
      description: 'Update an existing author record.',
      pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Author ID must be greater than 0.' }],
      prefill: {
        endpointId: 'get-author'
      },
      body: {
        title: 'Updated Author Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true, minLength: 2, maxLength: 50, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'First name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true, minLength: 1, maxLength: 50, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'Last name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'photo', label: 'Photo URL', type: 'text', maxLength: 255 }
        ]
      }
    },
    {
      id: 'delete-author',
      name: 'Delete Author',
      method: 'DELETE',
      path: '/api/v1/authors/{authorId}',
      description: 'Delete an author by ID.',
      pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1', min: 1, minMessage: 'Author ID must be greater than 0.' }]
    },
    {
      id: 'map-book-author',
      name: 'Map Book To Author',
      method: 'POST',
      path: '/api/v1/books/{isbn}/authors/{authorId}',
      description: 'Attach an author to a book ISBN.',
      pathParams: [
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 },
        { key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '9', min: 1, minMessage: 'Author ID must be greater than 0.' }
      ],
      body: {
        title: 'Mapping Payload',
        fields: [
          {
            key: 'primaryAuthor',
            label: 'Primary Author',
            type: 'select',
            options: [
              { label: 'Yes', value: 'Y' },
              { label: 'No', value: 'N' }
            ],
            defaultValue: 'Y'
          }
        ]
      }
    },
    {
      id: 'remove-book-author',
      name: 'Remove Book Author',
      method: 'DELETE',
      path: '/api/v1/books/{isbn}/authors/{authorId}',
      description: 'Remove a mapped author from a book.',
      pathParams: [
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4', minLength: 3, maxLength: 20 },
        { key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '9', min: 1, minMessage: 'Author ID must be greater than 0.' }
      ]
    }
  ]
};
