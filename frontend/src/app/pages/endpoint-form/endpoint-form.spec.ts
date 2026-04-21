import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EndpointForm } from './endpoint-form';

describe('EndpointForm', () => {
  let component: EndpointForm;
  let fixture: ComponentFixture<EndpointForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EndpointForm],
    }).compileComponents();

    fixture = TestBed.createComponent(EndpointForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
