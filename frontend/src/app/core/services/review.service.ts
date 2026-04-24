import { Injectable } from '@angular/core';
import { ExplorerModule } from '../models/explorer.model';

@Injectable({ providedIn: 'root' })
export class ReviewService {
  getModule(): ExplorerModule {
    return {
      id: 'reviews',
      title: 'Review Management',
      subtitle: 'Book reviews and reviewer records.',
      description: 'Inspect review endpoints and submit sample review payloads with live output.',
      route: '/reviews',
      icon: '⭐',
      accent: 'gold',
      loginHint: { username: 'admin', password: 'admin123' },
      endpoints: [
        { id: 'book-reviews', title: 'Book Reviews', method: 'GET', path: '/books/{isbn}/reviews', description: 'Fetch reviews by ISBN.', group: 'Retrieve', pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '978-0000000001' }] },
        { id: 'create-review', title: 'Create Review', method: 'POST', path: '/books/{isbn}/reviews', description: 'Create a review.', group: 'Manage', pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true }], bodyFields: [{ key: 'reviewerId', label: 'Reviewer ID', type: 'number', required: true }, { key: 'rating', label: 'Rating', type: 'number', required: true }, { key: 'comments', label: 'Comments', type: 'textarea' }] },
        { id: 'all-reviewers', title: 'All Reviewers', method: 'GET', path: '/reviewers', description: 'Fetch all reviewers.', group: 'Reviewers' }
      ]
    };
  }
}
