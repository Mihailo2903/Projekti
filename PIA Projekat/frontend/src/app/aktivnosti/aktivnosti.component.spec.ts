import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AktivnostiComponent } from './aktivnosti.component';

describe('AktivnostiComponent', () => {
  let component: AktivnostiComponent;
  let fixture: ComponentFixture<AktivnostiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AktivnostiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AktivnostiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
