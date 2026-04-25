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
  readonly min?: number;
  readonly max?: number;
  readonly minLength?: number;
  readonly maxLength?: number;
  readonly pattern?: string;
  readonly requiredMessage?: string;
  readonly minMessage?: string;
  readonly maxMessage?: string;
  readonly minLengthMessage?: string;
  readonly maxLengthMessage?: string;
  readonly patternMessage?: string;
}

export interface EndpointBodyConfig {
  readonly title: string;
  readonly fields: readonly EndpointField[];
}

export interface EndpointPrefillConfig {
  readonly endpointId: string;
  readonly pathParamMap?: Readonly<Record<string, string>>;
  readonly bodyFieldMap?: Readonly<Record<string, string>>;
  readonly lockWhenFieldTrue?: string;
  readonly lockedMessage?: string;
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
  readonly prefill?: EndpointPrefillConfig;
}

export interface EndpointRequestPayload {
  readonly pathParams: Record<string, string | number | boolean>;
  readonly queryParams: Record<string, string | number | boolean>;
  readonly body: Record<string, string | number | boolean> | null;
}
