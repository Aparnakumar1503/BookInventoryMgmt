import { ModuleConfig, PlatformStats } from '../models/module.model';
import { Teammate } from '../models/team.model';

export const TEAMMATES: readonly Teammate[] = [
  {
    id: 'aparna',
    name: 'Aparna',
    role: 'User and Author API Owner',
    moduleId: 'users-authors',
    moduleName: 'User and Author Management',
    modules: ['Users', 'Roles', 'Authors', 'Book-Author Mapping'],
    username: 'Aparna',
    email: 'aparna@bookinventory.dev'
  },
  {
    id: 'moses',
    name: 'Moses',
    role: 'Book Catalog API Owner',
    moduleId: 'books',
    moduleName: 'Book Catalog and References',
    modules: ['Books', 'Categories', 'Publishers', 'States'],
    username: 'Moses',
    email: 'moses@bookinventory.dev'
  },
  {
    id: 'sobika',
    name: 'Sobika',
    role: 'Inventory API Owner',
    moduleId: 'inventory',
    moduleName: 'Inventory Management',
    modules: ['Inventory', 'Book Conditions'],
    username: 'Sobika',
    email: 'sobika@bookinventory.dev'
  },
  {
    id: 'janapriya',
    name: 'Janapriya',
    role: 'Order API Owner',
    moduleId: 'orders',
    moduleName: 'Cart and Order Processing',
    modules: ['Shopping Cart', 'Orders'],
    username: 'Janapriya',
    email: 'janapriya@bookinventory.dev'
  },
  {
    id: 'swarnalatha',
    name: 'Swarnalatha',
    role: 'Review API Owner',
    moduleId: 'reviews',
    moduleName: 'Review Management',
    modules: ['Book Reviews', 'Reviewers'],
    username: 'SwarnaLatha',
    email: 'swarnalatha@bookinventory.dev'
  }
];

export const MODULES: readonly ModuleConfig[] = [
  {
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
  },
  {
    id: 'books',
    name: 'Book Catalog and References',
    tagline: 'Book CRUD, search filters, publishers, categories, and state reference APIs.',
    description: 'This module covers the full catalog experience from book CRUD through reference data reads and publisher maintenance.',
    owner: TEAMMATES[1],
    endpoints: [
      {
        id: 'list-books',
        name: 'List Books',
        method: 'GET',
        path: '/api/v1/books',
        description: 'Fetch all books or filter by category and publisher.',
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
        description: 'Create a new book.',
        body: {
          title: 'Book Payload',
          fields: [
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
            { key: 'title', label: 'Title', type: 'text', required: true, placeholder: 'Master Java' },
            { key: 'description', label: 'Description', type: 'textarea', placeholder: 'Book description' },
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
        description: 'Update an existing book.',
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
        description: 'Fetch all categories.'
      },
      {
        id: 'get-category',
        name: 'Get Category',
        method: 'GET',
        path: '/api/v1/categories/{categoryId}',
        description: 'Fetch a category by ID.',
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
        description: 'Fetch one publisher by ID.',
        pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'create-publisher',
        name: 'Create Publisher',
        method: 'POST',
        path: '/api/v1/publishers',
        description: 'Create a publisher.',
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
        description: 'Update a publisher.',
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
        description: 'Delete a publisher.',
        pathParams: [{ key: 'publisherId', label: 'Publisher ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'list-states',
        name: 'List States',
        method: 'GET',
        path: '/api/v1/states',
        description: 'Fetch all state reference values.'
      },
      {
        id: 'get-state',
        name: 'Get State',
        method: 'GET',
        path: '/api/v1/states/{code}',
        description: 'Fetch a state by code.',
        pathParams: [{ key: 'code', label: 'State Code', type: 'text', required: true, placeholder: 'NY' }]
      }
    ]
  },
  {
    id: 'inventory',
    name: 'Inventory Management',
    tagline: 'Inventory copy lookup, creation, purchase marking, and condition reference APIs.',
    description: 'This module manages book inventory copies and the read-only condition reference data used to price them.',
    owner: TEAMMATES[2],
    endpoints: [
      {
        id: 'list-inventory',
        name: 'List Inventory',
        method: 'GET',
        path: '/api/v1/inventory',
        description: 'Fetch all inventory records.'
      },
      {
        id: 'get-inventory',
        name: 'Get Inventory',
        method: 'GET',
        path: '/api/v1/inventory/{inventoryId}',
        description: 'Fetch one inventory record by ID.',
        pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'get-inventory-by-book',
        name: 'Get Inventory By ISBN',
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
        description: 'Create a new inventory copy.',
        body: {
          title: 'Inventory Payload',
          fields: [
            { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' },
            { key: 'ranks', label: 'Condition Rank', type: 'number', required: true, placeholder: '6' }
          ]
        }
      },
      {
        id: 'purchase-inventory',
        name: 'Purchase Inventory',
        method: 'PUT',
        path: '/api/v1/inventory/{inventoryId}/purchase',
        description: 'Mark an inventory copy as purchased.',
        pathParams: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
      },
      {
        id: 'list-book-conditions',
        name: 'List Book Conditions',
        method: 'GET',
        path: '/api/v1/book-conditions',
        description: 'Fetch all book condition references.'
      },
      {
        id: 'get-book-condition',
        name: 'Get Book Condition',
        method: 'GET',
        path: '/api/v1/book-conditions/{rank}',
        description: 'Fetch one book condition by rank.',
        pathParams: [{ key: 'rank', label: 'Rank', type: 'number', required: true, placeholder: '6' }]
      }
    ]
  },
  {
    id: 'orders',
    name: 'Cart and Order Processing',
    tagline: 'User cart browsing, item management, and checkout endpoints.',
    description: 'This module demonstrates user cart operations and order checkout flows, each protected for the owner responsible for order processing.',
    owner: TEAMMATES[3],
    endpoints: [
      {
        id: 'get-cart',
        name: 'Get Cart',
        method: 'GET',
        path: '/api/v1/cart/{userId}',
        description: 'Fetch the shopping cart for a user.',
        pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }]
      },
      {
        id: 'add-cart-item',
        name: 'Add Cart Item',
        method: 'POST',
        path: '/api/v1/cart/{userId}/items/{isbn}',
        description: 'Add one book to a user cart.',
        pathParams: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' },
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }
        ]
      },
      {
        id: 'delete-cart-item',
        name: 'Delete Cart Item',
        method: 'DELETE',
        path: '/api/v1/cart/{userId}/items/{isbn}',
        description: 'Delete one book from a user cart.',
        pathParams: [
          { key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' },
          { key: 'isbn', label: 'ISBN', type: 'text', required: true, placeholder: '1-111-11111-4' }
        ]
      },
      {
        id: 'checkout-order',
        name: 'Checkout Order',
        method: 'POST',
        path: '/api/v1/orders/checkout/{userId}',
        description: 'Checkout a selected inventory copy for a user.',
        pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }],
        body: {
          title: 'Checkout Payload',
          fields: [{ key: 'inventoryId', label: 'Inventory ID', type: 'number', required: true, placeholder: '1' }]
        }
      },
      {
        id: 'get-orders',
        name: 'Get Orders By User',
        method: 'GET',
        path: '/api/v1/orders/user/{userId}',
        description: 'Fetch all purchases for a user.',
        pathParams: [{ key: 'userId', label: 'User ID', type: 'number', required: true, placeholder: '1001' }]
      }
    ]
  },
  {
    id: 'reviews',
    name: 'Review Management',
    tagline: 'Book-scoped review APIs plus reviewer administration endpoints.',
    description: 'This module demonstrates book review creation and lookup alongside reviewer CRUD APIs for review owners.',
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
        id: 'create-reviewer',
        name: 'Create Reviewer',
        method: 'POST',
        path: '/api/v1/reviewers',
        description: 'Create a reviewer.',
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
        path: '/api/v1/reviewers/{id}',
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
        path: '/api/v1/reviewers/{id}',
        description: 'Delete one reviewer by ID.',
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
