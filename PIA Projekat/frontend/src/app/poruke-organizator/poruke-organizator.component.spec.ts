import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PorukeOrganizatorComponent } from './poruke-organizator.component';

describe('PorukeOrganizatorComponent', () => {
  let component: PorukeOrganizatorComponent;
  let fixture: ComponentFixture<PorukeOrganizatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PorukeOrganizatorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PorukeOrganizatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
