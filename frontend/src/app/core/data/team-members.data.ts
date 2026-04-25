import { Teammate } from '../models/team.model';

export const TEAMMATES: readonly Teammate[] = [
  {
    id: 'aparna',
    name: 'Aparna',
    role: 'Access and Author API Owner',
    moduleId: 'users-authors',
    moduleName: 'User and Author Management',
    modules: ['Authentication', 'Users', 'Roles', 'Authors', 'Book-Author Mapping'],
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
