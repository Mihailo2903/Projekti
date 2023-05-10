import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PorukeUcesnikComponent } from './poruke-ucesnik.component';

describe('PorukeUcesnikComponent', () => {
  let component: PorukeUcesnikComponent;
  let fixture: ComponentFixture<PorukeUcesnikComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PorukeUcesnikComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PorukeUcesnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
