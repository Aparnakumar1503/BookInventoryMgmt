import { Component, Input } from '@angular/core';
import { HttpMethod } from '../../../core/models/explorer.model';

@Component({
  selector: 'app-method-pill',
  templateUrl: './method-pill.component.html',
  styleUrl: './method-pill.component.scss'
})
export class MethodPillComponent {
  @Input({ required: true }) method!: HttpMethod;
}
