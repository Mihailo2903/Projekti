import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-istorija-radionica',
  templateUrl: './istorija-radionica.component.html',
  styleUrls: ['./istorija-radionica.component.css']
})
export class IstorijaRadionicaComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);

    this.servis.dohvatiIstoriju(this.ulogovan.korisnicko_ime).subscribe((u:Ucesce[])=>{
      this.ucesca=u;
      let radIds = [];
      this.ucesca.forEach(u=> radIds.push(u.radionica));

      this.servis.dohvatiMojeRadionice(radIds).subscribe((rad:Radionica[])=>{
        this.radionice = rad;
        this.radionice.forEach(rad=>{
          rad.datum= new Date(rad.datum);
          rad.kratak_opis = this.uredi(rad.kratak_opis, 25);
          rad.naziv = this.uredi(rad.naziv, 20);
        });
        this.radionice = this.radionice.filter(rad=> rad.datum.getTime()<new Date().getTime());
      })
    })
  }

  ulogovan: Korisnik;
  ucesca: Ucesce[];
  radionice: Radionica[];

  uredi(pocetni: string, karakteraUredu: number){
    let razmak=-1; let uRedu=0;
    let novi="";
    for(let i=0; i<pocetni.length; i++){
      novi = novi + pocetni.charAt(i);
      if(pocetni.charAt(i)==" ")
        razmak=i;
      uRedu++;
      if(pocetni.charAt(i)=="\n"){
        uRedu=0;
        razmak=-1;
      }
      if(uRedu==karakteraUredu){
        uRedu=0;
        if(razmak == -1)
          novi=novi+"\n";
        else
          novi=this.replaceAt(novi,razmak,"\n");
        razmak=-1;
      }
    }
    return novi;
  }

  replaceAt(str, index, replacement) {
    return str.substring(0, index) + replacement + str.substring(index + replacement.length);
  }

  sortDatum(){
    this.radionice.sort((a,b)=>{
      return a.datum.getTime() - b.datum.getTime();
    })
  }

  sortNaziv(){
    this.radionice.sort((a,b)=>{
      if(a.naziv < b.naziv) return -1;
      else return 1;
    })
  }

  sortMesto(){
    this.radionice.sort((a,b)=>{
      if(a.mesto < b.mesto) return -1;
      else return 1;
    })
  }

  sortOpis(){
    this.radionice.sort((a,b)=>{
      if(a.kratak_opis < b.kratak_opis) return -1;
      else return 1;
    })
  }

  sortLajkovi(){
    this.radionice.sort((a,b)=>{
      if(a.broj_lajkova > b.broj_lajkova) return -1;
      else return 1;
    })
  }

  sortKomentari(){
    this.radionice.sort((a,b)=>{
      if(a.broj_komentara > b.broj_komentara) return -1;
      else return 1;
    })
  }

}
