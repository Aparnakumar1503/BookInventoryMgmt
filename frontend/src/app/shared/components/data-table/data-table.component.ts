import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.scss'
})
export class DataTableComponent {
  @Input({ required: true }) columns: string[] = [];
  @Input({ required: true }) rows: Record<string, unknown>[] = [];

  trackColumn(_index: number, column: string): string {
    return column;
  }
}
