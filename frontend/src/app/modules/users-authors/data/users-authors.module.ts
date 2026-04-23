import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const USERS_AUTHORS_MODULE: ModuleConfig = {
  id: 'users-authors',
  name: 'User and Author Management',
  tagline: 'User onboarding, role administration, author CRUD, and book-author mapping APIs.',
  description: 'This module demonstrates protected admin-facing user and role APIs together with author management and ISBN-author mapping operations.',
  owner: TEAMMATES[0],
  endpoints: [
    {
      id: 'create-user',
      name: 'Create User',
      method: 'POST',
      path: '/api/v1/users',
      description: 'Create a new application user.',
      body: {
        title: 'User Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true, placeholder: 'Mary' },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true, placeholder: 'Burblemonger' },
          { key: 'phoneNumber', label: 'Phone Number', type: 'text', placeholder: '(983) 555-8865' },
          { key: 'userName', label: 'User Name', type: 'text', required: true, placeholder: 'mary@example.com' },
          { key: 'password', label: 'Password', type: 'text', required: true, placeholder: 'password' },
          { key: 'roleNumber', label: 'Role Number', type: 'number', required: true, placeholder: '2' }
        ]
      }
    },
    {
      id: 'get-user',
      name: 'Get User',
      method: 'GET',
      path: '/api/v1/users/{userId}',
      description: 'Fetch one user by user ID.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }]
    },
    {
      id: 'update-user',
      name: 'Update User',
      method: 'PUT',
      path: '/api/v1/users/{userId}',
      description: 'Update an existing user profile and role.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }],
      body: {
        title: 'Updated User Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true },
          { key: 'phoneNumber', label: 'Phone Number', type: 'text' },
          { key: 'userName', label: 'User Name', type: 'text', required: true },
          { key: 'password', label: 'Password', type: 'text', required: true },
          { key: 'roleNumber', label: 'Role Number', type: 'number', required: true }
        ]
      }
    },
    {
      id: 'list-roles',
      name: 'List Roles',
      method: 'GET',
      path: '/api/v1/roles',
      description: 'Fetch all available roles.'
    },
    {
      id: 'assign-role',
      name: 'Assign Role',
      method: 'PUT',
      path: '/api/v1/users/{userId}/roles/{roleId}',
      description: 'Assign a new role to a specific user.',
      pathParams: [
        { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' },
        { key: 'roleId', label: 'Role ID', type: 'number', required: true, placeholder: '4' }
      ]
    },
    {
      id: 'list-authors',
      name: 'List Authors',
      method: 'GET',
      path: '/api/v1/authors',
      description: 'Fetch all authors.'
    },
    {
      id: 'get-author',
      name: 'Get Author',
      method: 'GET',
      path: '/api/v1/authors/{authorId}',
      description: 'Fetch an author by author ID.',
      pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }]
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
          { key: 'firstName', label: 'First Name', type: 'text', required: true, placeholder: 'Steve' },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true, placeholder: 'Jacobs' },
          { key: 'photo', label: 'Photo', type: 'text', placeholder: 'profile.jpg' }
        ]
      }
    },
    {
      id: 'update-author',
      name: 'Update Author',
      method: 'PUT',
      path: '/api/v1/authors/{authorId}',
      description: 'Update an existing author record.',
      pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }],
      body: {
        title: 'Updated Author Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true },
          { key: 'photo', label: 'Photo', type: 'text' }
        ]
      }
    },
    {
      id: 'delete-author',
      name: 'Delete Author',
      method: 'DELETE',
      path: '/api/v1/authors/{authorId}',
      description: 'Delete an author by ID.',
      pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }]
    },
    {
      id: 'map-book-author',
      name: 'Map Book To Author',
      method: 'POST',
      path: '/api/v1/books/{isbn}/authors/{authorId}',
      description: 'Attach an author to a book ISBN.',
      pathParams: [
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
        { key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '9' }
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
        { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
        { key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '9' }
      ]
    }
  ]
};
