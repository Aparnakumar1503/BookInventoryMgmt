import { ModuleConfig, PlatformStats } from '../models/module.model';
import { TEAMMATES } from './team-members.data';
import { BOOKS_MODULE } from '../../modules/books/data/books.module';
import { USERS_AUTHORS_MODULE } from '../../modules/users-authors/data/users-authors.module';
import { INVENTORY_MODULE } from '../../modules/inventory/data/inventory.module';
import { ORDERS_MODULE } from '../../modules/orders/data/orders.module';
import { REVIEWS_MODULE } from '../../modules/reviews/data/reviews.module';

export { TEAMMATES };

export const MODULES: readonly ModuleConfig[] = [
  USERS_AUTHORS_MODULE,
  BOOKS_MODULE,
  INVENTORY_MODULE,
  ORDERS_MODULE,
  REVIEWS_MODULE
];

export const PLATFORM_STATS: PlatformStats = {
  teams: TEAMMATES.length,
  modules: MODULES.length,
  endpoints: MODULES.reduce((count, module) => count + module.endpoints.length, 0)
};
