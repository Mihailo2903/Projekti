import { TestBed } from '@angular/core/testing';

import { RadionicaServisService } from './radionica-servis.service';

describe('RadionicaServisService', () => {
  let service: RadionicaServisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RadionicaServisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
