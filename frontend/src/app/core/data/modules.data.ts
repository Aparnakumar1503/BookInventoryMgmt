import { ModuleConfig, PlatformStats } from '../models/module.model';
import { Teammate } from '../models/team.model';

export const TEAMMATES: readonly Teammate[] = [
  {
    id: 'user-module',
    name: 'User Team',
    role: 'User and Role API Owner',
    moduleId: 'users',
    moduleName: 'User Module',
    email: 'users.team@bookinventory.dev'
  },
  {
    id: 'author-module',
    name: 'Author Team',
    role: 'Author Mapping API Owner',
    moduleId: 'authors',
    moduleName: 'Author Module',
    email: 'authors.team@bookinventory.dev'
  },
  {
    id: 'book-module',
    name: 'Book Team',
    role: 'Catalog API Owner',
    moduleId: 'books',
    moduleName: 'Book Module',
    email: 'books.team@bookinventory.dev'
  },
  {
    id: 'inventory-module',
    name: 'Inventory Team',
    role: 'Inventory API Owner',
    moduleId: 'inventory',
    moduleName: 'Inventory Module',
    email: 'inventory.team@bookinventory.dev'
  },
  {
    id: 'order-module',
    name: 'Order Team',
    role: 'Order and Cart API Owner',
    moduleId: 'orders',
    moduleName: 'Order Module',
    email: 'orders.team@bookinventory.dev'
  },
  {
    id: 'review-module',
    name: 'Review Team',
    role: 'Review API Owner',
    moduleId: 'reviews',
    moduleName: 'Review Module',
    email: 'reviews.team@bookinventory.dev'
  }
];

export const MODULES: readonly ModuleConfig[] = [
  {
    id: 'users',
    name: 'User Module',
    tagline: 'Test user profile APIs and admin role assignment APIs.',
    description: 'Covers the user and permrole tables through user CRUD, role lookup, and role assignment endpoints.',
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
            { key: 'userName', label: 'User Name', type: 'email', required: true, placeholder: 'mary@example.com' },
            { key: 'password', label: 'Password', type: 'text', required: true, placeholder: 'password' },
            { key: 'roleNumber', label: 'Role Number', type: 'number', required: true, placeholder: '1' }
          ]
        }
      },
      {
        id: 'get-user',
        name: 'Get User',
        method: 'GET',
        path: '/api/v1/users/{userId}',
        description: 'Fetch a user profile by user ID.',
        pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' }]
      },
      {
        id: 'update-user',
        name: 'Update User',
        method: 'PUT',
        path: '/api/v1/users/{userId}',
        description: 'Update profile and login details for an existing user.',
        pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' }],
        body: {
          title: 'Updated User Payload',
          fields: [
            { key: 'firstName', label: 'First Name', type: 'text', required: true },
            { key: 'lastName', label: 'Last Name', type: 'text', required: true },
            { key: 'phoneNumber', label: 'Phone Number', type: 'text' },
            { key: 'userName', label: 'User Name', type: 'email', required: true },
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
        description: 'Fetch all available permission roles.'
      },
      {
        id: 'assign-role',
        name: 'Assign User Role',
        method: 'PUT',
        path: '/api/v1/users/{userId}/roles/{roleId}',
        description: 'Assign an admin role to a user.',
        pathParams: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' },
          { key: 'roleId', label: 'Role ID', type: 'number', required: true, placeholder: '4' }
        ]
      }
    ]
  },
  {
    id: 'authors',
    name: 'Author Module',
    tagline: 'Test author CRUD APIs and book-author relationship mapping.',
    description: 'Covers author and bookauthor tables through author management and ISBN-author mapping endpoints.',
    owner: TEAMMATES[1],
    endpoints: [
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
            { key: 'photo', label: 'Photo', type: 'text', placeholder: 'Y' }
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
        description: 'Delete an author by author ID.',
        pathParams: [{ key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'map-book-author',
        name: 'Map Book To Author',
        method: 'POST',
        path: '/api/v1/books/{isbn}/authors/{authorId}',
        description: 'Create a mapping between a book ISBN and an author.',
        pathParams: [
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
          { key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '9' }
        ],
        body: {
          title: 'Book Author Payload',
          fields: [
            { key: 'primaryAuthor', label: 'Primary Author', type: 'select', options: [
              { label: 'Yes', value: 'Y' },
              { label: 'No', value: 'N' }
            ] }
          ]
        }
      },
      {
        id: 'remove-book-author',
        name: 'Remove Book Author',
        method: 'DELETE',
        path: '/api/v1/books/{isbn}/authors/{authorId}',
        description: 'Delete a book-author mapping.',
        pathParams: [
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
          { key: 'authorId', label: 'Author ID', type: 'number', required: true, placeholder: '9' }
        ]
      }
    ]
  },
  {
    id: 'books',
    name: 'Book Module',
    tagline: 'Test book, category, publisher, and reference state APIs.',
    description: 'Covers book, category, publisher, and state tables with catalog CRUD and read-only reference endpoints.',
    owner: TEAMMATES[2],
    endpoints: [
      {
        id: 'list-books',
        name: 'List Books',
        method: 'GET',
        path: '/api/v1/books',
        description: 'Fetch all books, optionally filtered by category and publisher.',
        queryParams: [
          { key: 'categoryId', label: 'Category ID', type: 'number', placeholder: '3' },
          { key: 'publisherId', label: 'Publisher ID', type: 'number', placeholder: '7' }
        ]
      },
      {
        id: 'get-book',
        name: 'Get Book',
        method: 'GET',
        path: '/api/v1/books/{isbn}',
        description: 'Fetch one book by ISBN.',
        pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
      },
      {
        id: 'create-book',
        name: 'Create Book',
        method: 'POST',
        path: '/api/v1/books',
        description: 'Create a new book catalog record.',
        body: {
          title: 'Book Payload',
          fields: [
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
            { key: 'title', label: 'Title', type: 'text', required: true, placeholder: 'Master Java' },
            { key: 'description', label: 'Description', type: 'textarea', placeholder: 'Short book description' },
            { key: 'categoryId', label: 'Category ID', type: 'number', required: true, placeholder: '3' },
            { key: 'edition', label: 'Edition', type: 'text', placeholder: '1' },
            { key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '7' }
          ]
        }
      },
      {
        id: 'update-book',
        name: 'Update Book',
        method: 'PUT',
        path: '/api/v1/books/{isbn}',
        description: 'Update an existing book by ISBN.',
        pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }],
        body: {
          title: 'Updated Book Payload',
          fields: [
            { key: 'title', label: 'Title', type: 'text', required: true },
            { key: 'description', label: 'Description', type: 'textarea' },
            { key: 'categoryId', label: 'Category ID', type: 'number', required: true },
            { key: 'edition', label: 'Edition', type: 'text' },
            { key: 'publisherId', label: 'Publisher ID', type: 'number', required: true }
          ]
        }
      },
      {
        id: 'delete-book',
        name: 'Delete Book',
        method: 'DELETE',
        path: '/api/v1/books/{isbn}',
        description: 'Delete a book by ISBN.',
        pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
      },
      {
        id: 'list-categories',
        name: 'List Categories',
        method: 'GET',
        path: '/api/v1/categories',
        description: 'Fetch all read-only book categories.'
      },
      {
        id: 'get-category',
        name: 'Get Category',
        method: 'GET',
        path: '/api/v1/categories/{categoryId}',
        description: 'Fetch a category by category ID.',
        pathParams: [{ key: 'categoryId', label: 'Category ID', type: 'number', required: true, placeholder: '3' }]
      },
      {
        id: 'list-publishers',
        name: 'List Publishers',
        method: 'GET',
        path: '/api/v1/publishers',
        description: 'Fetch all publishers.'
      },
      {
        id: 'get-publisher',
        name: 'Get Publisher',
        method: 'GET',
        path: '/api/v1/publishers/{publisherId}',
        description: 'Fetch a publisher by publisher ID.',
        pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'create-publisher',
        name: 'Create Publisher',
        method: 'POST',
        path: '/api/v1/publishers',
        description: 'Create a new publisher.',
        body: {
          title: 'Publisher Payload',
          fields: [
            { key: 'name', label: 'Name', type: 'text', required: true, placeholder: 'New Press' },
            { key: 'city', label: 'City', type: 'text', placeholder: 'Rochester' },
            { key: 'stateCode', label: 'State Code', type: 'text', placeholder: 'NY' }
          ]
        }
      },
      {
        id: 'update-publisher',
        name: 'Update Publisher',
        method: 'PUT',
        path: '/api/v1/publishers/{publisherId}',
        description: 'Update a publisher by publisher ID.',
        pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1' }],
        body: {
          title: 'Updated Publisher Payload',
          fields: [
            { key: 'name', label: 'Name', type: 'text', required: true },
            { key: 'city', label: 'City', type: 'text' },
            { key: 'stateCode', label: 'State Code', type: 'text' }
          ]
        }
      },
      {
        id: 'delete-publisher',
        name: 'Delete Publisher',
        method: 'DELETE',
        path: '/api/v1/publishers/{publisherId}',
        description: 'Delete a publisher by publisher ID.',
        pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'list-states',
        name: 'List States',
        method: 'GET',
        path: '/api/v1/states',
        description: 'Fetch read-only state reference data.'
      },
      {
        id: 'get-state',
        name: 'Get State',
        method: 'GET',
        path: '/api/v1/states/{code}',
        description: 'Fetch one state by state code.',
        pathParams: [{ key: 'code', label: 'State Code', type: 'text', required: true, placeholder: 'NY' }]
      }
    ]
  },
  {
    id: 'inventory',
    name: 'Inventory Module',
    tagline: 'Test inventory records, purchase marking, and book condition lookup.',
    description: 'Covers inventory and bookcondition tables through inventory management and read-only condition endpoints.',
    owner: TEAMMATES[3],
    endpoints: [
      {
        id: 'list-inventory',
        name: 'List Inventory',
        method: 'GET',
        path: '/api/v1/inventory',
        description: 'Fetch all inventory copies.'
      },
      {
        id: 'get-inventory',
        name: 'Get Inventory',
        method: 'GET',
        path: '/api/v1/inventory/{inventoryId}',
        description: 'Fetch one inventory copy by inventory ID.',
        pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000' }]
      },
      {
        id: 'get-book-inventory',
        name: 'Get Book Inventory',
        method: 'GET',
        path: '/api/v1/inventory/books/{isbn}',
        description: 'Fetch all inventory copies for a book ISBN.',
        pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
      },
      {
        id: 'create-inventory',
        name: 'Create Inventory',
        method: 'POST',
        path: '/api/v1/inventory',
        description: 'Create a new inventory copy for a book.',
        body: {
          title: 'Inventory Payload',
          fields: [
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
            { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6' },
            { key: 'purchased', label: 'Purchased', type: 'boolean', defaultValue: false }
          ]
        }
      },
      {
        id: 'purchase-inventory',
        name: 'Purchase Inventory',
        method: 'PUT',
        path: '/api/v1/inventory/{inventoryId}/purchase',
        description: 'Mark an inventory copy as purchased.',
        pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000' }]
      },
      {
        id: 'update-inventory',
        name: 'Update Inventory',
        method: 'PUT',
        path: '/api/v1/inventory/{inventoryId}',
        description: 'Update an inventory copy.',
        pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000' }],
        body: {
          title: 'Updated Inventory Payload',
          fields: [
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
            { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6' },
            { key: 'purchased', label: 'Purchased', type: 'boolean', defaultValue: false }
          ]
        }
      },
      {
        id: 'delete-inventory',
        name: 'Delete Inventory',
        method: 'DELETE',
        path: '/api/v1/inventory/{inventoryId}',
        description: 'Delete one inventory copy by inventory ID.',
        pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000' }]
      },
      {
        id: 'list-book-conditions',
        name: 'List Book Conditions',
        method: 'GET',
        path: '/api/v1/book-conditions',
        description: 'Fetch all book condition ranks.'
      },
      {
        id: 'get-book-condition',
        name: 'Get Book Condition',
        method: 'GET',
        path: '/api/v1/book-conditions/{rank}',
        description: 'Fetch one book condition by rank.',
        pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }]
      },
      {
        id: 'create-book-condition',
        name: 'Create Book Condition',
        method: 'POST',
        path: '/api/v1/book-conditions',
        description: 'Create a new book condition rank.',
        body: {
          title: 'Book Condition Payload',
          fields: [
            { key: 'ranks', label: 'Rank', type: 'number', required: true, placeholder: '7' },
            { key: 'description', label: 'Description', type: 'text', required: true, placeholder: 'Mint' },
            { key: 'fullDescription', label: 'Full Description', type: 'textarea', placeholder: 'Collector quality copy.' },
            { key: 'price', label: 'Price', type: 'number', required: true, placeholder: '35' }
          ]
        }
      },
      {
        id: 'update-book-condition',
        name: 'Update Book Condition',
        method: 'PUT',
        path: '/api/v1/book-conditions/{rank}',
        description: 'Update a book condition rank.',
        pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }],
        body: {
          title: 'Updated Book Condition Payload',
          fields: [
            { key: 'ranks', label: 'Rank', type: 'number', required: true, placeholder: '6' },
            { key: 'description', label: 'Description', type: 'text', required: true },
            { key: 'fullDescription', label: 'Full Description', type: 'textarea' },
            { key: 'price', label: 'Price', type: 'number', required: true }
          ]
        }
      },
      {
        id: 'delete-book-condition',
        name: 'Delete Book Condition',
        method: 'DELETE',
        path: '/api/v1/book-conditions/{rank}',
        description: 'Delete a book condition rank.',
        pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }]
      }
    ]
  },
  {
    id: 'orders',
    name: 'Order Module',
    tagline: 'Test purchase log and shopping cart APIs.',
    description: 'Covers purchaselog and shoppingcart tables through add, list, and delete endpoints.',
    owner: TEAMMATES[4],
    endpoints: [
      {
        id: 'add-purchase',
        name: 'Add Purchase',
        method: 'POST',
        path: '/purchase/add',
        description: 'Create a purchase log entry.',
        body: {
          title: 'Purchase Payload',
          fields: [
            { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' },
            { key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000' }
          ]
        }
      },
      {
        id: 'list-purchases',
        name: 'List Purchases',
        method: 'GET',
        path: '/purchase/get',
        description: 'Fetch all purchase log records.'
      },
      {
        id: 'delete-purchase',
        name: 'Delete Purchase',
        method: 'DELETE',
        path: '/purchase/delete/{userId}/{inventoryId}',
        description: 'Delete one purchase log entry by composite key.',
        pathParams: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' },
          { key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1000000' }
        ]
      },
      {
        id: 'list-cart',
        name: 'List Shopping Cart',
        method: 'GET',
        path: '/cart/get',
        description: 'Fetch all shopping cart records.'
      },
      {
        id: 'add-cart-item',
        name: 'Add Cart Item',
        method: 'POST',
        path: '/cart/add',
        description: 'Add one book ISBN to a user shopping cart.',
        body: {
          title: 'Cart Payload',
          fields: [
            { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' },
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }
          ]
        }
      },
      {
        id: 'delete-cart-item',
        name: 'Delete Cart Item',
        method: 'DELETE',
        path: '/cart/delete/{userId}/{isbn}',
        description: 'Delete one shopping cart item by composite key.',
        pathParams: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1000000' },
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }
        ]
      }
    ]
  },
  {
    id: 'reviews',
    name: 'Review Module',
    tagline: 'Test book review and reviewer APIs.',
    description: 'Covers bookreview and reviewer tables through review filtering and reviewer CRUD endpoints.',
    owner: TEAMMATES[5],
    endpoints: [
      {
        id: 'list-reviews',
        name: 'List Reviews',
        method: 'GET',
        path: '/api/reviews',
        description: 'Fetch all book reviews.'
      },
      {
        id: 'create-book-review',
        name: 'Create Book Review',
        method: 'POST',
        path: '/api/reviews/add',
        description: 'Create a new book review.',
        body: {
          title: 'Review Payload',
          fields: [
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
            { key: 'reviewerID', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' },
            { key: 'rating', label: 'Rating', type: 'number', required: true, placeholder: '10' },
            { key: 'comments', label: 'Comments', type: 'textarea', placeholder: 'Excellent book.' }
          ]
        }
      },
      {
        id: 'get-reviews-by-isbn',
        name: 'Get Reviews By ISBN',
        method: 'GET',
        path: '/api/reviews/isbn/{isbn}',
        description: 'Fetch reviews for one book ISBN.',
        pathParams: [{ key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }]
      },
      {
        id: 'get-reviews-by-reviewer',
        name: 'Get Reviews By Reviewer',
        method: 'GET',
        path: '/api/reviews/reviewer/{reviewerId}',
        description: 'Fetch reviews written by one reviewer.',
        pathParams: [{ key: 'reviewerId', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }]
      },
      {
        id: 'delete-review',
        name: 'Delete Review',
        method: 'DELETE',
        path: '/api/reviews/{id}',
        description: 'Delete a review by review ID.',
        pathParams: [{ key: 'id', label: 'Review ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'list-reviewers',
        name: 'List Reviewers',
        method: 'GET',
        path: '/api/reviewers',
        description: 'Fetch all reviewers.'
      },
      {
        id: 'get-reviewer',
        name: 'Get Reviewer',
        method: 'GET',
        path: '/api/reviewers/{id}',
        description: 'Fetch one reviewer by reviewer ID.',
        pathParams: [{ key: 'id', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }]
      },
      {
        id: 'create-reviewer',
        name: 'Create Reviewer',
        method: 'POST',
        path: '/api/reviewers/add',
        description: 'Create a new reviewer.',
        body: {
          title: 'Reviewer Payload',
          fields: [
            { key: 'name', label: 'Name', type: 'text', required: true, placeholder: 'Jacobs' },
            { key: 'employedBy', label: 'Employed By', type: 'text', required: true, placeholder: 'Gadget Boy' }
          ]
        }
      },
      {
        id: 'update-reviewer',
        name: 'Update Reviewer',
        method: 'PUT',
        path: '/api/reviewers/{id}',
        description: 'Update an existing reviewer.',
        pathParams: [{ key: 'id', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }],
        body: {
          title: 'Updated Reviewer Payload',
          fields: [
            { key: 'name', label: 'Name', type: 'text', required: true },
            { key: 'employedBy', label: 'Employed By', type: 'text', required: true }
          ]
        }
      },
      {
        id: 'delete-reviewer',
        name: 'Delete Reviewer',
        method: 'DELETE',
        path: '/api/reviewers/{id}',
        description: 'Delete a reviewer by reviewer ID.',
        pathParams: [{ key: 'id', label: 'Reviewer ID', type: 'number', required: true, placeholder: '19' }]
      }
    ]
  }
];

export const PLATFORM_STATS: PlatformStats = {
  teams: TEAMMATES.length,
  modules: MODULES.length,
  endpoints: MODULES.reduce((count, module) => count + module.endpoints.length, 0)
};
