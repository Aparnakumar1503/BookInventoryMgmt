export type HttpMethod = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

export type FieldType = 'text' | 'number' | 'email' | 'date' | 'textarea' | 'boolean' | 'select';

export interface EndpointFieldOption {
  readonly label: string;
  readonly value: string;
}

export interface EndpointField {
  readonly key: string;
  readonly label: string;
  readonly type: FieldType;
  readonly required?: boolean;
  readonly placeholder?: string;
  readonly defaultValue?: string | number | boolean;
  readonly options?: readonly EndpointFieldOption[];
}

export interface EndpointBodyConfig {
  readonly title: string;
  readonly fields: readonly EndpointField[];
}

export interface EndpointConfig {
  readonly id: string;
  readonly name: string;
  readonly method: HttpMethod;
  readonly path: string;
  readonly description: string;
  readonly pathParams?: readonly EndpointField[];
  readonly queryParams?: readonly EndpointField[];
  readonly body?: EndpointBodyConfig;
}

export interface EndpointRequestPayload {
  readonly pathParams: Record<string, string | number | boolean>;
  readonly queryParams: Record<string, string | number | boolean>;
  readonly body: Record<string, string | number | boolean> | null;
}
