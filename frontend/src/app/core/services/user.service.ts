import { Injectable } from '@angular/core';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  getModule(): ExplorerModule {
    return {
      id: 'users',
      title: 'User Management',
      subtitle: 'Accounts, roles, and librarian access.',
      description: 'Create users, assign roles, and manage account records with a live response preview.',
      route: '/users',
      icon: '👤',
      accent: 'lavender',
      loginHint: { username: 'admin', password: 'admin123' },
      endpoints: [
        { id: 'all-users', title: 'All Users', method: 'GET', path: '/users', description: 'Fetch all users.', group: 'Retrieve' },
        { id: 'get-user', title: 'Get User', method: 'GET', path: '/users/{userId}', description: 'Fetch one user by id.', group: 'Retrieve', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1' }] },
        { id: 'create-user', title: 'Create User', method: 'POST', path: '/users', description: 'Create a user.', group: 'Manage', bodyFields: [{ key: 'username', label: 'Username', type: 'text', required: true, placeholder: 'reader01' }, { key: 'password', label: 'Password', type: 'text', required: true, placeholder: 'admin123' }, { key: 'role', label: 'Role', type: 'select', required: true, options: [{ label: 'ADMIN', value: 'ADMIN' }, { label: 'USER', value: 'USER' }, { label: 'LIBRARIAN', value: 'LIBRARIAN' }] }] },
        { id: 'update-user', title: 'Update User', method: 'PUT', path: '/users/{userId}', description: 'Update an existing user.', group: 'Manage', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1' }], bodyFields: [{ key: 'username', label: 'Username', type: 'text', required: true }, { key: 'role', label: 'Role', type: 'select', required: true, options: [{ label: 'ADMIN', value: 'ADMIN' }, { label: 'USER', value: 'USER' }, { label: 'LIBRARIAN', value: 'LIBRARIAN' }] }] },
        { id: 'delete-user', title: 'Delete User', method: 'DELETE', path: '/users/{userId}', description: 'Delete one user.', group: 'Manage', pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1' }] }
      ]
    };
  }
}
