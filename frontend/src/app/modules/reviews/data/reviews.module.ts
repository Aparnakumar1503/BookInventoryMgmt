import { ModuleConfig } from '../../../core/models/module.model';
import { TEAMMATES } from '../../../core/data/team-members.data';

export const REVIEWS_MODULE: ModuleConfig = {
  id: 'reviews',
  name: 'Review Management',
  tagline: 'Book review, reviewer, and custom review query APIs for moderation and analysis.',
  description: 'This module covers book-scoped review operations together with reviewer management and custom query endpoints such as max-rating and reviewer-specific searches.',
  owner: TEAMMATES[4],
  endpoints: [
    {
      id: 'get-book-reviews',
      name: 'Get Book Reviews',
      method: 'GET',
      path: '/api/v1/books/{isbn}/reviews',
      description: 'Fetch all reviews for a given ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
    },
    {
      id: 'create-book-review',
      name: 'Create Book Review',
      method: 'POST',
      path: '/api/v1/books/{isbn}/reviews',
      description: 'Create a book review for an ISBN.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }],
      body: {
        title: 'Review Payload',
        fields: [
          { key: 'reviewerID', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' },
          { key: 'rating', label: 'Rating', type: 'number', required: true, placeholder: '8' },
          { key: 'comments', label: 'Comments', type: 'textarea', placeholder: 'Strong review.' }
        ]
      }
    },
    {
      id: 'get-reviews-by-isbn-query',
      name: 'Get Reviews By ISBN Query',
      method: 'GET',
      path: '/api/v1/reviews/isbn/{isbn}',
      description: 'Fetch reviews by ISBN using the custom review query endpoint.',
      pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
    },
    {
      id: 'get-reviews-by-reviewer',
      name: 'Get Reviews By Reviewer',
      method: 'GET',
      path: '/api/v1/reviews/reviewer/{reviewerId}',
      description: 'Fetch reviews created by one reviewer.',
      pathParams: [{ key: 'reviewerId', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }]
    },
    {
      id: 'get-max-rating-reviews',
      name: 'Get Max Rating Reviews',
      method: 'GET',
      path: '/api/v1/reviews/max-rating',
      description: 'Fetch the highest-rated reviews available.'
    },
    {
      id: 'delete-review',
      name: 'Delete Review',
      method: 'DELETE',
      path: '/api/v1/reviews/{id}',
      description: 'Delete a review by review ID.',
      pathParams: [{ key: 'id', label: 'Review ID', type: 'number', required: true, placeholder: '101' }]
    },
    {
      id: 'list-reviewers',
      name: 'List Reviewers',
      method: 'GET',
      path: '/api/v1/reviewers',
      description: 'Fetch all reviewers.'
    },
    {
      id: 'get-reviewer',
      name: 'Get Reviewer',
      method: 'GET',
      path: '/api/v1/reviewers/{id}',
      description: 'Fetch one reviewer by ID.',
      pathParams: [{ key: 'id', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }]
    },
    {
      id: 'get-reviewers-by-company',
      name: 'Get Reviewers By Company',
      method: 'GET',
      path: '/api/v1/reviewers/company/{company}',
      description: 'Fetch reviewers filtered by company name.',
      pathParams: [{ key: 'company', label: 'Company', type: 'text', required: true, placeholder: 'BookWorld' }]
    },
    {
      id: 'create-reviewer',
      name: 'Create Reviewer',
      method: 'POST',
      path: '/api/v1/reviewers',
      description: 'Create a new reviewer.',
      body: {
        title: 'Reviewer Payload',
        fields: [
          { key: 'name', label: 'Name', type: 'text', required: true, placeholder: 'Aditi Rao' },
          { key: 'employedBy', label: 'Employed By', type: 'text', required: true, placeholder: 'BookWorld' }
        ]
      }
    },
    {
      id: 'update-reviewer',
      name: 'Update Reviewer',
      method: 'PUT',
      path: '/api/v1/reviewers/{id}',
      description: 'Update an existing reviewer.',
      pathParams: [{ key: 'id', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }],
      body: {
        title: 'Updated Reviewer Payload',
        fields: [
          { key: 'name', label: 'Name', type: 'text', required: true, placeholder: 'Aditi Rao' },
          { key: 'employedBy', label: 'Employed By', type: 'text', required: true, placeholder: 'BookWorld' }
        ]
      }
    },
    {
      id: 'delete-reviewer',
      name: 'Delete Reviewer',
      method: 'DELETE',
      path: '/api/v1/reviewers/{id}',
      description: 'Delete a reviewer by ID.',
      pathParams: [{ key: 'id', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }]
    }
  ]
};
