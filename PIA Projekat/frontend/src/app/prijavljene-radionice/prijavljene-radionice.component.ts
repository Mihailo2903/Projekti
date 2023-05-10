import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-prijavljene-radionice',
  templateUrl: './prijavljene-radionice.component.html',
  styleUrls: ['./prijavljene-radionice.component.css']
})
export class PrijavljeneRadioniceComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router, private korServis: KorisnikServisService){}

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
        this.radionice = this.radionice.filter(rad=> rad.datum.getTime() > new Date().getTime());

        this.servis.dohvatiRezervisaneRadionice(this.ulogovan.korisnicko_ime).subscribe((rr:Radionica[])=>{
          this.rezervisane=rr;
          this.rezervisane.forEach(rad=>{
            rad.datum= new Date(rad.datum);
            rad.kratak_opis = this.uredi(rad.kratak_opis, 25);
            rad.naziv = this.uredi(rad.naziv, 20);
          });

          this.servis.dohvatiradioniceNaCekanju(this.ulogovan.mejl).subscribe((nc:Radionica[])=>{
            this.naCekanju=nc;
            this.naCekanju.forEach(rad=>{
              rad.datum= new Date(rad.datum);
              rad.kratak_opis = this.uredi(rad.kratak_opis, 25);
              rad.naziv = this.uredi(rad.naziv, 20);
            });
          })

        })
      })
    })
  }

  ulogovan: Korisnik;
  ucesca: Ucesce[];
  radionice: Radionica[];

  rezervisane: Radionica[];
  naCekanju: Radionica[];

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

  otkaziva(r:Radionica): boolean{
    if(r.datum.getTime() > new Date().getTime() + 12*60*60*1000 )
      return true;
    return false;
  }

  otkaziUcesce(r:Radionica){
    let pomocni=[]
    r.naCekanju.forEach(element=> {pomocni.push(element);})

    this.servis.otkaziUceseNaRadionici(r._id,this.ulogovan.korisnicko_ime).subscribe(re=>{
      pomocni.forEach(element=>{
        this.korServis.posaljiObavestenjeOSlobodnomMestu(element,r.naziv).subscribe(re=>{
        })
      })
      this.ngOnInit();
    })
  }

  otkaziRezervaciju(rad:Radionica){
    let arr = []
    rad.naCekanju.forEach(element => {
      arr.push(element);
    });
    this.servis.odbijRezervaciju(rad._id,this.ulogovan.korisnicko_ime).subscribe(r=>{
      arr.forEach(element=>{
        this.korServis.posaljiObavestenjeOSlobodnomMestu(element,rad.naziv).subscribe(re=>{
        })
      })
      this.ngOnInit();
    })
  }

  otkaziPrijavu(rad:Radionica){
    this.servis.otkaziPrijavuZaRadionicu(rad._id,this.ulogovan.mejl).subscribe(r=>{
      this.ngOnInit();
    })
  }

}
