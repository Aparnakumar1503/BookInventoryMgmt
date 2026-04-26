import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const USERS_MODULE: ModuleConfig = {
  id: 'users',
  name: 'User Access Management',
  tagline: 'Authentication, user administration, and role assignment APIs.',
  description: 'This module focuses on authentication, protected user CRUD, and role assignment workflows used by the administration flow.',
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
        { key: 'page', label: 'Page', type: 'number', placeholder: '1', min: 1, minMessage: 'Page must be 1 or greater.' },
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
    }
  ]
};
