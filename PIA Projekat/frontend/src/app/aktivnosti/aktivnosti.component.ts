import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Aktivnost } from '../models/aktivnost';
import { Korisnik } from '../models/korisnik';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-aktivnosti',
  templateUrl: './aktivnosti.component.html',
  styleUrls: ['./aktivnosti.component.css']
})
export class AktivnostiComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);

    this.servis.dohvatiAktivnosti(this.ulogovan.korisnicko_ime).subscribe((a:Aktivnost[])=>{
      this.aktivnosti=a;
      this.aktivnosti.forEach(ak=>{
        if(ak.tip=="komentar"){
          ak.sadrzaj = this.uredi(ak.sadrzaj,30);
          ak.noviSadrzaj = ak.sadrzaj;
        }
        ak.datum = new Date(ak.datum);
      })

      this.aktivnosti.sort((a,b)=>{
        if(a.datum.getTime()<b.datum.getTime())
          return 1;
        else return -1;
      })
    })

    
  }

  ulogovan: Korisnik;
  aktivnosti: Aktivnost[];

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

  promeni(ak:Aktivnost){
    if(!ak.promenaUToku)
      ak.promenaUToku=true;
    else
      ak.promenaUToku=false;
    ak.noviSadrzaj=ak.sadrzaj;
  }

  azuriraj(ak:Aktivnost){
    if(!ak.promenaUToku || ak.sadrzaj==ak.noviSadrzaj){
      return;
    }
    this.servis.azurirajAktivnost(ak._id, ak.noviSadrzaj).subscribe(r=>{
      this.ngOnInit();
    })
  }

  obrisi(ak:Aktivnost){
    this.servis.obrisiAktivnost(ak._id, ak.tip, ak.radionica).subscribe(r=>{
      this.ngOnInit();
    })
  }

}
