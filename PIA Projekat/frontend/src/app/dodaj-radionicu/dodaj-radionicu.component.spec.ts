import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajRadionicuComponent } from './dodaj-radionicu.component';

describe('DodajRadionicuComponent', () => {
  let component: DodajRadionicuComponent;
  let fixture: ComponentFixture<DodajRadionicuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DodajRadionicuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DodajRadionicuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
