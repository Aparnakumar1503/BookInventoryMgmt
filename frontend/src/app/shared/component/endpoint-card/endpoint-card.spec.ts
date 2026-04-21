import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EndpointCard } from './endpoint-card';

describe('EndpointCard', () => {
  let component: EndpointCard;
  let fixture: ComponentFixture<EndpointCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EndpointCard],
    }).compileComponents();

    fixture = TestBed.createComponent(EndpointCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
