import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrijavljeneRadioniceComponent } from './prijavljene-radionice.component';

describe('PrijavljeneRadioniceComponent', () => {
  let component: PrijavljeneRadioniceComponent;
  let fixture: ComponentFixture<PrijavljeneRadioniceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrijavljeneRadioniceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrijavljeneRadioniceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
