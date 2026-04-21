import { EndpointConfig } from './endpoint.model';
import { Teammate } from './team.model';

export interface ModuleConfig {
  readonly id: string;
  readonly name: string;
  readonly tagline: string;
  readonly description: string;
  readonly owner: Teammate;
  readonly endpoints: readonly EndpointConfig[];
}

export interface PlatformStats {
  readonly teams: number;
  readonly modules: number;
  readonly endpoints: number;
}
