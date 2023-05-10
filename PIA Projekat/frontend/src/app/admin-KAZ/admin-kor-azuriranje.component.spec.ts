import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminKorAzuriranjeComponent } from './admin-kor-azuriranje.component';

describe('AdminKorAzuriranjeComponent', () => {
  let component: AdminKorAzuriranjeComponent;
  let fixture: ComponentFixture<AdminKorAzuriranjeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminKorAzuriranjeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminKorAzuriranjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
