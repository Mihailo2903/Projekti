import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SveRadioniceComponent } from './sve-radionice.component';

describe('SveRadioniceComponent', () => {
  let component: SveRadioniceComponent;
  let fixture: ComponentFixture<SveRadioniceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SveRadioniceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SveRadioniceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
