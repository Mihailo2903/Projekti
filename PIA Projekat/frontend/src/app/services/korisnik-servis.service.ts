import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class KorisnikServisService {

  constructor(private http: HttpClient) { }

  uri= 'http://localhost:4000';

  proveriJedinstvenost(korisnicko_ime, mejl){
    let body={
      korisnicko_ime: korisnicko_ime,
      mejl: mejl
    }

    return this.http.post(`${this.uri}/korisnici/proveriJedinstvenost`, body);
  }

  registrujUcensika(body){
    return this.http.post(`${this.uri}/korisnici/registrujUcesnika`, body);
  }

  registrujOrganizatora(body){
    return this.http.post(`${this.uri}/korisnici/registrujOrganizatora`, body);
  }

  ulogujSe(korisnicko_ime, lozinka){
    let body={
      korisnicko_ime: korisnicko_ime,
      lozinka: lozinka
    }
    return this.http.post(`${this.uri}/korisnici/ulogujSe`, body);
  }

  posaljiMejl(mejl, lozinka){
    let body={
      mejl: mejl,
      novaLozinka: lozinka
    }
    return this.http.post(`${this.uri}/korisnici/posaljiMejl`, body);
  }

  dohvatiPoMejlu(mejl){
    let body={
      mejl:mejl
    }
    return this.http.post(`${this.uri}/korisnici/dohvatiPoMejlu`, body);
  }

  promeniLozinku(mejl,nova){
    let body={
      mejl:mejl,
      nova:nova
    }
    return this.http.post(`${this.uri}/korisnici/promeniLozinku`, body);
  }

  promeniPodatak(mejl,novo,broj){
    let body={
      mejl:mejl,
      novo:novo,
      broj: broj
    }
    return this.http.post(`${this.uri}/korisnici/promeniPodatak`, body);
  }

  
  dohvatiPoKorImenu(korisnicko_ime){
    let body={
      korisnicko_ime:korisnicko_ime
    }
    return this.http.post(`${this.uri}/korisnici/dohvatiPoKorImenu`, body);
  }

  posaljiObavestenjeOSlobodnomMestu(mejl, radionica){
    let body={
      mejl: mejl,
      radionica: radionica
    }
    return this.http.post(`${this.uri}/korisnici/posaljiObavestenjeOSlobodnomMestu`, body);
  }

  posaljiObavestenjeOOtkazivanjuRadionice(mejl, radionica){
    let body={
      mejl: mejl,
      radionica: radionica
    }
    return this.http.post(`${this.uri}/korisnici/posaljiObavestenjeOOtkazivanju`, body);
  }

  azurirajPromenukorisnickogImena(staro, novo){
    let body={
      staro: staro,
      novo: novo
    }
    return this.http.post(`${this.uri}/korisnici/azurirajPromenukorisnickogImena`, body);
  }

  azurirajPromenuMejla(staro, novo){
    let body={
      staro: staro,
      novo: novo
    }
    return this.http.post(`${this.uri}/korisnici/azurirajPromenuMejla`, body);
  }

  dohvatiSveKorisnike(){
    return this.http.get(`${this.uri}/korisnici/dohvatSveKorisnike`);
  }

  dohvatiSveUcesnike(){
    return this.http.get(`${this.uri}/korisnici/dohvatSveUcesnike`);
  }

  postaviStatus(korisnicko_ime, status){
    let body={
      korisnicko_ime:korisnicko_ime,
      status:status
    }
    return this.http.post(`${this.uri}/korisnici/postaviStatus`, body);
  }

  obrisiKorisnika(korisnicko_ime, mejl){
    let body={
      korisnik:korisnicko_ime,
      mejl:mejl
    }
    return this.http.post(`${this.uri}/korisnici/obrisiKorisnika`, body);
  }

  unaprediUOrganizatora(korisnicko_ime){
    let body={
      korisnicko_ime:korisnicko_ime,
    }
    return this.http.post(`${this.uri}/korisnici/unaprediUOrganizatora`, body);
  }

}
