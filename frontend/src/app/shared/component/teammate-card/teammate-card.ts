import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Teammate } from '../../../core/models/team.model';

@Component({
  selector: 'app-teammate-card',
  imports: [RouterLink],
  templateUrl: './teammate-card.html'
})
export class TeammateCardComponent {
  readonly teammate = input.required<Teammate>();
}
