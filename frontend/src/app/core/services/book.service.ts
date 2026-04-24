import { Injectable } from '@angular/core';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class BookService {
  getModule(): ExplorerModule {
    return {
      id: 'books',
      title: 'Book Management',
      subtitle: 'Books, categories, and publishers.',
      description: 'Maintain the catalog and supporting reference data in one module explorer.',
      route: '/books',
      icon: '📘',
      accent: 'sky',
      loginHint: { username: 'admin', password: 'admin123' },
      endpoints: [
        { id: 'all-books', title: 'All Books', method: 'GET', path: '/books', description: 'Fetch all books.', group: 'Books' },
        { id: 'get-book', title: 'Get Book', method: 'GET', path: '/books/{isbn}', description: 'Fetch a book by ISBN.', group: 'Books', pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '978-0000000001' }] },
        { id: 'create-book', title: 'Create Book', method: 'POST', path: '/books', description: 'Create a new book.', group: 'Books', bodyFields: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true }, { key: 'title', label: 'Title', type: 'text', required: true }, { key: 'description', label: 'Description', type: 'textarea' }] },
        { id: 'all-categories', title: 'All Categories', method: 'GET', path: '/categories', description: 'Fetch all categories.', group: 'References' },
        { id: 'all-publishers', title: 'All Publishers', method: 'GET', path: '/publishers', description: 'Fetch all publishers.', group: 'References' },
        { id: 'create-publisher', title: 'Create Publisher', method: 'POST', path: '/publishers', description: 'Create a publisher.', group: 'References', bodyFields: [{ key: 'name', label: 'Name', type: 'text', required: true }, { key: 'city', label: 'City', type: 'text' }, { key: 'stateCode', label: 'State Code', type: 'text' }] }
      ]
    };
  }
}
