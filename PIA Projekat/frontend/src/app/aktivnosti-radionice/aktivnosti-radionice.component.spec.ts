import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AktivnostiRadioniceComponent } from './aktivnosti-radionice.component';

describe('AktivnostiRadioniceComponent', () => {
  let component: AktivnostiRadioniceComponent;
  let fixture: ComponentFixture<AktivnostiRadioniceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AktivnostiRadioniceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AktivnostiRadioniceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
