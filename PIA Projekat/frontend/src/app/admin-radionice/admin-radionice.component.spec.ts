import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRadioniceComponent } from './admin-radionice.component';

describe('AdminRadioniceComponent', () => {
  let component: AdminRadioniceComponent;
  let fixture: ComponentFixture<AdminRadioniceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminRadioniceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRadioniceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
