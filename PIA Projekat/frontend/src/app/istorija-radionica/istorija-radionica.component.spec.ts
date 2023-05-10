import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IstorijaRadionicaComponent } from './istorija-radionica.component';

describe('IstorijaRadionicaComponent', () => {
  let component: IstorijaRadionicaComponent;
  let fixture: ComponentFixture<IstorijaRadionicaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IstorijaRadionicaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IstorijaRadionicaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
