import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleHome } from './module-home';

describe('ModuleHome', () => {
  let component: ModuleHome;
  let fixture: ComponentFixture<ModuleHome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModuleHome],
    }).compileComponents();

    fixture = TestBed.createComponent(ModuleHome);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
