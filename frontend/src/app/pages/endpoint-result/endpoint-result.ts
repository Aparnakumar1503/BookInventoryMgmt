import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { StorageService } from '../../core/services/storage.service';
import { ResponseViewerComponent } from '../../shared/component/response-viewer/response-viewer';

@Component({
  selector: 'app-endpoint-result',
  imports: [RouterLink, ResponseViewerComponent],
  templateUrl: './endpoint-result.html'
})
export class EndpointResultComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly storageService = inject(StorageService);

  readonly response = this.storageService.lastResponse;
  readonly moduleId = this.storageService.getLastModuleId();

  ngOnInit(): void {
    window.setTimeout(() => {
      void this.router.navigate(['/member', this.moduleId ?? 'books']);
    }, 2500);
  }
}
