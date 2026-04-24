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
      GET: 'bv-method-get',
      POST: 'bv-method-post',
      PUT: 'bv-method-put',
      PATCH: 'bv-method-patch',
      DELETE: 'bv-method-delete'
    };

    return classes[method] ?? 'bg-slate-100 text-slate-800';
  }

  parameterCount(): number {
    return (this.endpoint().pathParams?.length ?? 0) + (this.endpoint().queryParams?.length ?? 0);
  }

  hasBody(): boolean {
    return Boolean(this.endpoint().body?.fields.length);
  }
}
