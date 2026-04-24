export type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

export interface ExplorerFieldOption {
  label: string;
  value: string;
}

export interface ExplorerField {
  key: string;
  label: string;
  type: 'text' | 'number' | 'textarea' | 'select';
  placeholder?: string;
  required?: boolean;
  options?: ExplorerFieldOption[];
}

export interface ExplorerEndpoint {
  id: string;
  title: string;
  method: HttpMethod;
  path: string;
  description: string;
  group: string;
  queryParams?: ExplorerField[];
  pathParams?: ExplorerField[];
  bodyFields?: ExplorerField[];
}

export interface ExplorerModule {
  id: string;
  title: string;
  subtitle: string;
  description: string;
  route: string;
  icon: string;
  accent: string;
  loginHint: {
    username: string;
    password: string;
  };
  endpoints: ExplorerEndpoint[];
}
