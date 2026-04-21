import { Injectable } from '@angular/core';
import { MODULES, PLATFORM_STATS, TEAMMATES } from '../data/modules.data';
import { EndpointConfig } from '../models/endpoint.model';
import { ModuleConfig, PlatformStats } from '../models/module.model';
import { Teammate } from '../models/team.model';

@Injectable({ providedIn: 'root' })
export class ModuleService {
  getModules(): readonly ModuleConfig[] {
    return MODULES;
  }

  getModulesByIds(moduleIds: readonly string[]): readonly ModuleConfig[] {
    return MODULES.filter((module) => moduleIds.includes(module.id));
  }

  getTeammates(): readonly Teammate[] {
    return TEAMMATES;
  }

  getStats(): PlatformStats {
    return PLATFORM_STATS;
  }

  getModule(moduleId: string): ModuleConfig | undefined {
    return MODULES.find((module) => module.id === moduleId);
  }

  getEndpoint(moduleId: string, endpointId: string): EndpointConfig | undefined {
    return this.getModule(moduleId)?.endpoints.find((endpoint) => endpoint.id === endpointId);
  }
}
