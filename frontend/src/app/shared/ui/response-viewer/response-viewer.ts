import { Component, computed, input } from '@angular/core';
import { ApiCallResult } from '../../../core/models/response.model';

@Component({
  selector: 'app-response-viewer',
  templateUrl: './response-viewer.html'
})
export class ResponseViewerComponent {
  readonly response = input<ApiCallResult | null>(null);

  readonly formattedBody = computed(() => {
    const response = this.response();
    return response ? JSON.stringify(response.body, null, 2) : '';
  });
}
