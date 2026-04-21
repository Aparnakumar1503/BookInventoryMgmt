import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeammateCard } from './teammate-card';

describe('TeammateCard', () => {
  let component: TeammateCard;
  let fixture: ComponentFixture<TeammateCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeammateCard],
    }).compileComponents();

    fixture = TestBed.createComponent(TeammateCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
