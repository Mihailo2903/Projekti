import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledRadionicaComponent } from './pregled-radionica.component';

describe('PregledRadionicaComponent', () => {
  let component: PregledRadionicaComponent;
  let fixture: ComponentFixture<PregledRadionicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PregledRadionicaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PregledRadionicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
