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
      id: 'login',
      name: 'Login',
      method: 'POST',
      path: '/api/v1/auth/login',
      description: 'Authenticate a user and return a JWT token payload.',
      body: {
        title: 'Login Payload',
        fields: [
          { key: 'username', label: 'Username', type: 'text', required: true, placeholder: 'aparna', minLength: 1, maxLength: 50 },
          { key: 'password', label: 'Password', type: 'text', required: true, placeholder: 'Password@123', minLength: 1, maxLength: 100 }
        ]
      }
    },
    {
      id: 'auth-me',
      name: 'Authenticated User',
      method: 'GET',
      path: '/api/v1/auth/me',
      description: 'Fetch the authenticated user details for the active JWT token.'
    },
    {
      id: 'create-user',
      name: 'Create User',
      method: 'POST',
      path: '/api/v1/users',
      description: 'Create a new application user.',
      body: {
        title: 'User Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true, placeholder: 'Mary', minLength: 2, maxLength: 20, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'First name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true, placeholder: 'Burblemonger', minLength: 1, maxLength: 30, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'Last name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'phoneNumber', label: 'Phone Number', type: 'text', required: true, placeholder: '9835558865', pattern: '^\\d{10}$', patternMessage: 'Phone number must be exactly 10 digits.' },
          { key: 'userName', label: 'User Name', type: 'text', required: true, placeholder: 'mary_example', minLength: 4, maxLength: 50, pattern: '^[A-Za-z0-9._]+$', patternMessage: 'Username can contain only letters, numbers, dots, and underscores.' },
          { key: 'password', label: 'Password', type: 'text', required: true, placeholder: 'Mary@1234', minLength: 8, maxLength: 100, pattern: '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$', patternMessage: 'Password must contain uppercase, lowercase, number, and special character.' },
          { key: 'roleNumber', label: 'Role Number', type: 'number', required: true, placeholder: '2', min: 1, minMessage: 'Role number must be greater than 0.' }
        ]
      }
    },
    {
      id: 'get-user',
      name: 'Get User',
      method: 'GET',
      path: '/api/v1/users/{userId}',
      description: 'Fetch one user by user ID.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' }]
    },
    {
      id: 'update-user',
      name: 'Update User',
      method: 'PUT',
      path: '/api/v1/users/{userId}',
      description: 'Update an existing user profile and role.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' }],
      prefill: {
        endpointId: 'get-user'
      },
      body: {
        title: 'Updated User Payload',
        fields: [
          { key: 'firstName', label: 'First Name', type: 'text', required: true, minLength: 2, maxLength: 20, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'First name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'lastName', label: 'Last Name', type: 'text', required: true, minLength: 1, maxLength: 30, pattern: "^[A-Za-z][A-Za-z '-]*$", patternMessage: 'Last name must contain only letters, spaces, apostrophes, or hyphens.' },
          { key: 'phoneNumber', label: 'Phone Number', type: 'text', required: true, pattern: '^\\d{10}$', patternMessage: 'Phone number must be exactly 10 digits.' },
          { key: 'userName', label: 'User Name', type: 'text', required: true, minLength: 4, maxLength: 50, pattern: '^[A-Za-z0-9._]+$', patternMessage: 'Username can contain only letters, numbers, dots, and underscores.' },
          { key: 'password', label: 'Password', type: 'text', required: true, minLength: 8, maxLength: 100, pattern: '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$', patternMessage: 'Password must contain uppercase, lowercase, number, and special character.' },
          { key: 'roleNumber', label: 'Role Number', type: 'number', required: true, min: 1, minMessage: 'Role number must be greater than 0.' }
        ]
      }
    },
    {
      id: 'delete-user',
      name: 'Delete User',
      method: 'DELETE',
      path: '/api/v1/users/{userId}',
      description: 'Delete a user by user ID.',
      pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' }]
    },
    {
      id: 'list-roles',
      name: 'List Roles',
      method: 'GET',
      path: '/api/v1/roles',
      description: 'Fetch all available roles.',
      queryParams: [
        { key: 'page', label: 'Page', type: 'number', placeholder: '0', min: 0, minMessage: 'Page must be 0 or greater.' },
        { key: 'size', label: 'Size', type: 'number', placeholder: '10', min: 1, minMessage: 'Size must be greater than 0.' }
      ]
    },
    {
      id: 'assign-role',
      name: 'Assign Role',
      method: 'PUT',
      path: '/api/v1/users/{userId}/roles/{roleId}',
      description: 'Assign a new role to a specific user.',
      pathParams: [
        { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001', min: 1, minMessage: 'User ID must be greater than 0.' },
        { key: 'roleId', label: 'Role ID', type: 'number', required: true, placeholder: '4', min: 1, minMessage: 'Role ID must be greater than 0.' }
      ]
    },
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
