import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResponseViewer } from './response-viewer';

describe('ResponseViewer', () => {
  let component: ResponseViewer;
  let fixture: ComponentFixture<ResponseViewer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResponseViewer],
    }).compileComponents();

    fixture = TestBed.createComponent(ResponseViewer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
