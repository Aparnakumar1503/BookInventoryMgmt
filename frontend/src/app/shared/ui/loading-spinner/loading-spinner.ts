import { Component, input } from '@angular/core';

@Component({
  selector: 'app-loading-spinner',
  templateUrl: './loading-spinner.html'
})
export class LoadingSpinnerComponent {
  readonly label = input('Calling API...');
}
