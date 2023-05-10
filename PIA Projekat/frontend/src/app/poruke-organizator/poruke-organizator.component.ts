import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Poruka } from '../models/poruka';
import { PorukaWrapper } from '../models/porukeWrapper';
import { Radionica } from '../models/radionica';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-poruke-organizator',
  templateUrl: './poruke-organizator.component.html',
  styleUrls: ['./poruke-organizator.component.css']
})
export class PorukeOrganizatorComponent implements OnInit {
  id: string;

  constructor(private servis: KorisnikServisService, private router: Router, private route: ActivatedRoute, private radSer: RadionicaServisService){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="organizator")
      this.router.navigate([""]);
    this.nizNiti = [];

    this.route.params.subscribe(params=>{
      this.id=params['id'];
      this.servis.dohvatiSveUcesnike().subscribe((u:Korisnik[])=>{
        this.ucesnici=u;
        this.ucesnici.forEach(uc=>{
          uc.otvorenaNit=false;
        })

        this.radSer.dohvatiRadionicuPoID(this.id).subscribe((r:Radionica)=>{
          this.radionica=r;
          this.organizator=this.radionica.organizator;
          this.servis.dohvatiPoKorImenu(this.organizator).subscribe((k:Korisnik)=>{
            this.slikaOrg = k.slika;
          })
        })
      })
      
    })

  }

  ulogovan: Korisnik;

  ucesnici: Korisnik[];
  radionica: Radionica;

  nizNiti: PorukaWrapper[];
  organizator: string;
  slikaOrg: string;

  

  vidiPoruke(u:Korisnik){
    u.otvorenaNit=true;
    let novaNit = new PorukaWrapper();
    this.radSer.dohvatiPoruke(u.korisnicko_ime,this.id).subscribe((p:Poruka[])=>{
      p.forEach(element=>{
        element.datum= new Date(element.datum);
        element.sadrzaj = this.uredi(element.sadrzaj,20);
      })
      novaNit.poruke = p;
      novaNit.ucesnik = u.korisnicko_ime;
      novaNit.ucesnikSlika = u.slika;
      this.nizNiti.push(novaNit);
    })
  }

  sakrijPoruke(u:Korisnik){
    u.otvorenaNit=false;
    let novi = [];
    this.nizNiti.forEach(element=>{
      if(element.ucesnik != u.korisnicko_ime)
        novi.push(element);
    })
    this.nizNiti=novi;
  }

  posaljiPoruku(nit:PorukaWrapper){
    if(nit.novaPoruka && nit.novaPoruka!=""){
      this.radSer.posaljiPoruku(this.id,nit.ucesnik,nit.novaPoruka,"radionica").subscribe(re=>{
        let por = new Poruka();
        por.korisnik=nit.ucesnik;
        por.radionica=this.id;
        por.sadrzaj=this.uredi(nit.novaPoruka,20);
        por.tip="radionica";
        por.datum= new Date();
        nit.poruke.push(por);
        nit.novaPoruka=null;
      })
    }
   
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

}
