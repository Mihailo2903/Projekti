import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UredjivanjeRadioniceComponent } from './uredjivanje-radionice.component';

describe('UredjivanjeRadioniceComponent', () => {
  let component: UredjivanjeRadioniceComponent;
  let fixture: ComponentFixture<UredjivanjeRadioniceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UredjivanjeRadioniceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UredjivanjeRadioniceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
