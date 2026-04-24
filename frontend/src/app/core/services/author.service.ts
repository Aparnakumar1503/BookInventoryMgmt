import { Injectable } from '@angular/core';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class AuthorService {
  getModule(): ExplorerModule {
    return {
      id: 'authors',
      title: 'Author Management',
      subtitle: 'Manage book authors and search filters.',
      description: 'Create author profiles, search authors, and explore live responses from the author API.',
      route: '/authors',
      icon: '🗂️',
      accent: 'mint',
      loginHint: { username: 'admin', password: 'admin123' },
      endpoints: [
        { id: 'all-authors', title: 'All Authors', method: 'GET', path: '/authors', description: 'Fetch all authors.', group: 'Retrieve' },
        { id: 'get-author', title: 'Get Author', method: 'GET', path: '/authors/{authorId}', description: 'Fetch one author by id.', group: 'Retrieve', pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }] },
        { id: 'search-by-name', title: 'Search by Name', method: 'GET', path: '/authors/search', description: 'Search authors by name.', group: 'Retrieve', queryParams: [{ key: 'name', label: 'Name', type: 'text', required: true, placeholder: 'Jane' }] },
        { id: 'by-nationality', title: 'By Nationality', method: 'GET', path: '/authors/nationality/{nationality}', description: 'Find authors by nationality.', group: 'Retrieve', pathParams: [{ key: 'nationality', label: 'Nationality', type: 'text', required: true, placeholder: 'British' }] },
        { id: 'create-author', title: 'Create Author', method: 'POST', path: '/authors', description: 'Create a new author.', group: 'Manage', bodyFields: [{ key: 'fullName', label: 'Full Name', type: 'text', required: true, placeholder: 'George Orwell' }, { key: 'email', label: 'Email', type: 'text', placeholder: 'george@orwell.com' }, { key: 'nationality', label: 'Nationality', type: 'text', placeholder: 'British' }] },
        { id: 'update-author', title: 'Update Author', method: 'PUT', path: '/authors/{authorId}', description: 'Update an author.', group: 'Manage', pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }], bodyFields: [{ key: 'fullName', label: 'Full Name', type: 'text', required: true }, { key: 'email', label: 'Email', type: 'text' }, { key: 'nationality', label: 'Nationality', type: 'text' }] },
        { id: 'delete-author', title: 'Delete Author', method: 'DELETE', path: '/authors/{authorId}', description: 'Delete an author.', group: 'Manage', pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }] }
      ]
    };
  }
}
