import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RadionicaDetaljiComponent } from './radionica-detalji.component';

describe('RadionicaDetaljiComponent', () => {
  let component: RadionicaDetaljiComponent;
  let fixture: ComponentFixture<RadionicaDetaljiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RadionicaDetaljiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RadionicaDetaljiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
