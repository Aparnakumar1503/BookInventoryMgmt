export interface Book {
  isbn: string;
  title: string;
  description?: string;
  edition?: string;
  categoryId?: number;
  publisherId?: number;
}
