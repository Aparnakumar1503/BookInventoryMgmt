import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ExplorerModule } from '../../../core/models/explorer.model';
import { MethodPillComponent } from '../method-pill/method-pill.component';

@Component({
  selector: 'app-data-card',
  imports: [RouterLink, MethodPillComponent],
  templateUrl: './data-card.component.html',
  styleUrl: './data-card.component.scss'
})
export class DataCardComponent {
  @Input({ required: true }) module!: ExplorerModule;
}
