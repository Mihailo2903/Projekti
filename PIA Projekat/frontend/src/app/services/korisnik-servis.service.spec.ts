import { TestBed } from '@angular/core/testing';

import { KorisnikServisService } from './korisnik-servis.service';

describe('KorisnikServisService', () => {
  let service: KorisnikServisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KorisnikServisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
