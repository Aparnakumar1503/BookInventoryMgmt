export interface CartItem {
  isbn: string;
  quantity?: number;
  title?: string;
}

export interface Cart {
  userId: number;
  items: CartItem[];
}
