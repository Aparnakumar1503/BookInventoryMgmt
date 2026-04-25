import { EndpointConfig } from '../models/endpoint.model';
import { ModuleConfig } from '../models/module.model';

export interface EndpointGroup {
  readonly title: string;
  readonly endpoints: readonly EndpointConfig[];
}

export function groupEndpoints(endpoints: readonly EndpointConfig[], moduleId?: string): EndpointGroup[] {
  const groups = new Map<string, EndpointConfig[]>();

  endpoints.forEach((endpoint) => {
    const title = resolveEndpointGroupTitle(endpoint.path, moduleId);
    const collection = groups.get(title) ?? [];
    collection.push(endpoint);
    groups.set(title, collection);
  });

  return Array.from(groups.entries()).map(([title, collection]) => ({
    title,
    endpoints: collection
  }));
}

function resolveEndpointGroupTitle(path: string, moduleId?: string): string {
  if (moduleId === 'users-authors') {
    if (path.includes('/auth/') || path.includes('/users') || path.includes('/roles')) return 'UserMgmt API';
    if (path.includes('/authors') || (path.includes('/books') && path.includes('/authors'))) return 'AuthorMgmt API';
  }

  if (path.includes('/books') && path.includes('/authors')) return 'Book-Author Mapping';
  if (path.includes('/books') && path.includes('/reviews')) return 'Review APIs';
  if (path.includes('/books')) return 'Book APIs';
  if (path.includes('/authors')) return 'Author APIs';
  if (path.includes('/publishers')) return 'Publisher APIs';
  if (path.includes('/categories')) return 'Category APIs';
  if (path.includes('/states')) return 'Reference APIs';
  if (path.includes('/inventory')) return 'Inventory APIs';
  if (path.includes('/book-conditions')) return 'Book Condition APIs';
  if (path.includes('/users') && path.includes('/roles')) return 'Role APIs';
  if (path.includes('/users')) return 'User APIs';
  if (path.includes('/roles')) return 'Role APIs';
  if (path.includes('/orders')) return 'Order APIs';
  if (path.includes('/cart')) return 'Shopping Cart APIs';
  if (path.includes('/reviewers')) return 'Reviewer APIs';
  return 'Module APIs';
}
