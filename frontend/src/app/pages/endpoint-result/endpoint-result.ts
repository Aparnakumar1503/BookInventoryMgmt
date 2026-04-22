import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { StorageService } from '../../core/services/storage.service';
import { ResponseViewerComponent } from '../../shared/component/response-viewer/response-viewer';

@Component({
  selector: 'app-endpoint-result',
  imports: [RouterLink, ResponseViewerComponent],
  templateUrl: './endpoint-result.html'
})
export class EndpointResultComponent {
  private readonly storageService = inject(StorageService);

  readonly response = this.storageService.lastResponse;
  readonly moduleId = this.storageService.getLastModuleId();
}
