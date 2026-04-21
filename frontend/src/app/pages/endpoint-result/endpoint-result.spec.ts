import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EndpointResult } from './endpoint-result';

describe('EndpointResult', () => {
  let component: EndpointResult;
  let fixture: ComponentFixture<EndpointResult>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EndpointResult],
    }).compileComponents();

    fixture = TestBed.createComponent(EndpointResult);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
