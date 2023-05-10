import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Poruka } from '../models/poruka';
import { Radionica } from '../models/radionica';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-poruke-ucesnik',
  templateUrl: './poruke-ucesnik.component.html',
  styleUrls: ['./poruke-ucesnik.component.css']
})
export class PorukeUcesnikComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router, private korSer: KorisnikServisService){}

  @ViewChild('scrollableDiv') scrollableDiv: ElementRef;

  ngAfterViewInit() {
    this.scrollableDiv.nativeElement.scrollTop = this.scrollableDiv.nativeElement.scrollHeight;
  }

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);

    this.servis.dohvatiRadionice().subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
      })
    })
  }

  ulogovan: Korisnik;
  radionice: Radionica[];
  odabranaRadionica: Radionica;

  poruke: Poruka[];
  organizator: String;

  slikaOrg: string;

  otvori(rad:Radionica){
    this.odabranaRadionica=rad;
    this.servis.dohvatiPoruke(this.ulogovan.korisnicko_ime, rad._id).subscribe((por:Poruka[])=>{
      this.poruke=por;
      this.poruke.forEach(por=>{
        por.datum= new Date(por.datum);
        por.sadrzaj = this.uredi(por.sadrzaj, 75);
      })
      this.organizator=rad.organizator;
      this.korSer.dohvatiPoKorImenu(rad.organizator).subscribe((k:Korisnik)=>{
        this.slikaOrg = k.slika;
      })
    })
  }


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

  novaPoruka: string;

  posaljiPoruku(){
    if(this.novaPoruka && this.novaPoruka!=""){
      this.servis.posaljiPoruku(this.odabranaRadionica._id,this.ulogovan.korisnicko_ime,this.novaPoruka,"korisnik").subscribe(r=>{
        this.servis.dohvatiPoruke(this.ulogovan.korisnicko_ime, this.odabranaRadionica._id).subscribe((por:Poruka[])=>{
          this.poruke=por;
          this.poruke.forEach(por=>{
            por.datum= new Date(por.datum);
            por.sadrzaj = this.uredi(por.sadrzaj, 75);
          })
          this.novaPoruka=null;
        })
      })
    }
  }

}
