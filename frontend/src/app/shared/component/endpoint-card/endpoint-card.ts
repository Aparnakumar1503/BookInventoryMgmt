import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { EndpointConfig } from '../../../core/models/endpoint.model';

@Component({
  selector: 'app-endpoint-card',
  imports: [RouterLink],
  templateUrl: './endpoint-card.html'
})
export class EndpointCardComponent {
  readonly moduleId = input.required<string>();
  readonly endpoint = input.required<EndpointConfig>();

  methodClass(method: string): string {
    const classes: Record<string, string> = {
      GET: 'bg-blue-100 text-blue-800',
      POST: 'bg-teal-100 text-teal-800',
      PUT: 'bg-amber-100 text-amber-800',
      PATCH: 'bg-violet-100 text-violet-800',
      DELETE: 'bg-rose-100 text-rose-800'
    };

    return classes[method] ?? 'bg-slate-100 text-slate-800';
  }
}
