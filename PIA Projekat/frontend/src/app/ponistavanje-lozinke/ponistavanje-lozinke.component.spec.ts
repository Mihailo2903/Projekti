import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PonistavanjeLozinkeComponent } from './ponistavanje-lozinke.component';

describe('PonistavanjeLozinkeComponent', () => {
  let component: PonistavanjeLozinkeComponent;
  let fixture: ComponentFixture<PonistavanjeLozinkeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PonistavanjeLozinkeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PonistavanjeLozinkeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
