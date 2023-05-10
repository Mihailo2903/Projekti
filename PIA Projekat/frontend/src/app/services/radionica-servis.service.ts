import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RadionicaServisService {

  constructor(private http: HttpClient) { }

  uri= 'http://localhost:4000';

  dohvatiRadionice(){
    return this.http.get(`${this.uri}/radionice/dohvatiRadionice`);
  }

  dohvatiRadionicuPoID(idRad){
    let body={
      idRad:idRad
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadionicu`,body);
  }

  dohvatiRadionicePoNazivu(naziv){
    let body={
      naziv:naziv
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadionicePoNazivu`,body);
  }

  dohvatiRadionicePoMestu(mesto){
    let body={
      mesto:mesto
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadionicePoMestu`,body);
  }

  dohvatiRadionicePoOba(naziv,mesto){
    let body={
      mesto:mesto,
      naziv: naziv
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadionicePoOba`,body);
  }

  dohvatiTop5(){
    return this.http.get(`${this.uri}/radionice/dohvatiTop5`);
  }

  dohvatiIstoriju(korisnik){
    let body={
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/dohvatiIstoriju`, body);
  }

  dohvatiMojeRadionice(radionice){
    let body={
      radionice: radionice
    }
    return this.http.post(`${this.uri}/radionice/dohvatiMojeRadionice`, body);
  }

  dohvatiradioniceNaCekanju(korisnik){
    let body={
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadioniceNaCekanju`, body);
  }

  dohvatiRezervisaneRadionice(korisnik){
    let body={
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRezervisaneRadionice`, body);
  }

  dohvatiAktivnosti(korisnik){
    let body={
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/dohvatiAktivnosti`, body);
  }

  obrisiAktivnost(idAk,tip,idRad){
    let body={
      idAk: idAk,
      tip: tip,
      idRad: idRad
    }
    return this.http.post(`${this.uri}/radionice/obrisiAktivnost`, body);
  }

  azurirajAktivnost(idAk,novi){
    let body={
      idAk: idAk,
      novi: novi,
    }
    return this.http.post(`${this.uri}/radionice/azurirajAktivnost`, body);
  }

  dohvatiPoruke(korisnik, idRad){
    let body={
      idRad: idRad,
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/dohvatiPoruke`, body);
  }

  posaljiPoruku(radionica,korisnik,sadrzaj,tip){
    let body={
      radionica: radionica,
      korisnik: korisnik,
      sadrzaj: sadrzaj,
      tip:tip
    }
    return this.http.post(`${this.uri}/radionice/posaljiPoruku`, body);
  }

  otkaziUceseNaRadionici(radionica,korisnik){
    let body={
      idRad: radionica,
      korisnik: korisnik,
    }
    return this.http.post(`${this.uri}/radionice/otkaziUcesceNaRadionici`, body);
  }

  proveriUcesce(radionica,korisnik){
    let body={
      idRad: radionica,
      korisnik: korisnik,
    }
    return this.http.post(`${this.uri}/radionice/proveriUcesce`, body);
  }

  rezervisiMestoNaRadionici(radionica,korisnik){
    let body={
      idRad: radionica,
      korisnik: korisnik,
    }
    return this.http.post(`${this.uri}/radionice/rezervisiMestoNaRadionici`, body);
  }
  
  dodajNaListuZacekanje(radionica,mejl){
    let body={
      idRad: radionica,
      mejl: mejl,
    }
    return this.http.post(`${this.uri}/radionice/dodajNaListuZaCekanje`, body);
  }

  bivseRadioniceSaIstimNazivom(radionica,korisnik){
    let body={
      radionica: radionica,
      korisnik: korisnik,
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadioniceSaIstimNazivom`, body);
  }
  
  dohvatiAktivnostiRadionice(idRad){
    let body={
      idRad: idRad
    }
    return this.http.post(`${this.uri}/radionice/dohvatiAktivnostiRadionice`, body);
  }

  dodajAktivnost(radionica,naziv,korisnik,tip,sadrzaj){
    let body={
      radionica: radionica,
      naziv: naziv,
      korisnik: korisnik,
      tip: tip,
      sadrzaj: sadrzaj,
    }
    return this.http.post(`${this.uri}/radionice/dodajAktivnost`, body);
  }

  dodajRadionicu(body){
    return this.http.post(`${this.uri}/radionice/dodajRadionicu`, body);
  }

  proveriPostojanjePredlogaRadionice(korisnik){
    let body={
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/proveriPostojanjePredlogaRadionice`, body);
  }

  azurirajRadionicu(body){
    return this.http.post(`${this.uri}/radionice/azurirajRadionicu`, body);
  }

  dohvatiUcescaNaRadionici(id){
    let body={
      id: id
    }
    return this.http.post(`${this.uri}/radionice/dohvatiUcescaNaRadionici`, body);
  }

  prihvatiRezervaciju(id,naziv,korisnik){
    let body={
      id:id,
      naziv:naziv,
      korisnik:korisnik
    }
    return this.http.post(`${this.uri}/radionice/prihvatiRezervaciju`, body);
  }

  odbijRezervaciju(id,korisnik){
    let body={
      id:id,
      korisnik:korisnik
    }
    return this.http.post(`${this.uri}/radionice/odbijRezervaciju`, body);
  }

  otkaziRadionicu(id){
    let body={
      id:id
    }
    return this.http.post(`${this.uri}/radionice/otkaziRadionicu`, body);
  }

  azurirajNazivRadionice(id,novi){
    let body={
      id:id,
      novi:novi
    }
    return this.http.post(`${this.uri}/radionice/azurirajNaziveRadionice`, body);
  }

  dohvatiRadioniceOrganizatora(korisnik){
    let body={
      korisnik: korisnik
    }
    return this.http.post(`${this.uri}/radionice/dohvatiRadioniceOrganizatora`, body);
  }

  dohvatiNeodobreneRadionice(){
    return this.http.get(`${this.uri}/radionice/dohvatiNeodobreneRadionice`);
  }
 
  obrisiRadionicu(id){
    let body={
      id:id,
    }
    return this.http.post(`${this.uri}/radionice/obrisiRadionicu`, body);
  }

  dohvatiSvaUcesca(){
    return this.http.get(`${this.uri}/radionice/dohvatiSvaUcesca`);
  }

  postaviStatusRadionice(id,status){
    let body={
      id:id,
      status:status
    }
    return this.http.post(`${this.uri}/radionice/postaviStatusRadionice`, body);
  }

  azurirajAktivnostiRadionicePosleBrisanjaKorisnika(id,broj_lajkova,broj_komentara){
    let body={
      id:id,
      broj_lajkova:broj_lajkova,
      broj_komentara:broj_komentara
    }
    return this.http.post(`${this.uri}/radionice/azurirajAktivnostiRadionicePosleBrisanjaKorisnika`, body);
  }

  otkaziPrijavuZaRadionicu(id,mejl){
    let body={
      id:id,
      mejl:mejl
    }
    return this.http.post(`${this.uri}/radionice/otkaziPrijavuZaRadionicu`, body);
  }

}
