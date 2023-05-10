import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-promena-lozinke',
  templateUrl: './promena-lozinke.component.html',
  styleUrls: ['./promena-lozinke.component.css']
})
export class PromenaLozinkeComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan)
      this.ulogovan=null;
  }

  ulogovan: Korisnik;
  mejlNeulogovan: string;
  staraNeulogovan: string;
  novaNeulogovan: string;
  potvrdaNeulogovan: string;

  staraUlogovan: string;
  novaUlogovan: string;
  potvrdaUlogovan: string;

  porukaNeulogovan: string;
  porukaUlogovan: string;

  porukaNeulogovanUspeh:string;
  porukaUlogovanUspeh:string;
  
  promeniNeulogovan(){
    if(!this.mejlNeulogovan || !this.staraNeulogovan || !this.novaNeulogovan || !this.potvrdaNeulogovan){
      this.porukaNeulogovan = "Niste uneli sve zahtevane podatke";
      return;
    }
    if(this.novaNeulogovan != this.potvrdaNeulogovan){
      this.porukaNeulogovan = "Niste dobro potvrdili lozinku";
      return;
    }
    if(!this.proveraLozinke(this.novaNeulogovan)){
      this.porukaNeulogovan="Nova lozinka ne zadovoljava kriterijume";
      return;
    }

    this.servis.dohvatiPoMejlu(this.mejlNeulogovan).subscribe((kor:Korisnik)=>{
      if(!kor){
        this.porukaNeulogovan = "Nepostojeci mail";
        return;
      }
      
      if(kor.lozinka != this.staraNeulogovan && kor.privremena_lozinka != this.staraNeulogovan){
        this.porukaNeulogovan = "Niste dobro uneli staru ili privremenu lozinku";
        return;
      }
      if(kor.lozinka != this.staraNeulogovan && kor.privremena_lozinka == this.staraNeulogovan){
        const sad = new Date();
        const vremeZahteva = new Date(kor.trenutak_zahteva+"");
        const diff = sad.getTime() - vremeZahteva.getTime();

        if((diff)>30*60*1000){
          this.porukaNeulogovan = "Proslo je vise od 30 minuta od prethodnog zahteva";
          return;
        } else{
          this.servis.promeniLozinku(kor.mejl, this.novaNeulogovan).subscribe(res=>{
            this.porukaNeulogovanUspeh = "Uspesna promena lozinke";
            this.mejlNeulogovan="";
            this.staraNeulogovan="";
            this.novaNeulogovan="";
            this.potvrdaNeulogovan=""
            
          })      
        }
      }
      else{
        this.servis.promeniLozinku(kor.mejl, this.novaNeulogovan).subscribe(res=>{
          this.porukaNeulogovanUspeh = "Uspesna promena lozinke";
            this.mejlNeulogovan="";
            this.staraNeulogovan="";
            this.novaNeulogovan="";
            this.potvrdaNeulogovan=""          
        }) 
      }
    })
  }

  promeniUlogovan(){
    if(!this.novaUlogovan || !this.staraUlogovan || !this.potvrdaUlogovan){
      this.porukaUlogovan = "Niste uneli sve zahtevane podatke";
      return;
    }
    if(this.novaUlogovan != this.potvrdaUlogovan){
      this.porukaUlogovan = "Niste dobro potvrdili lozinku";
      return;
    }
    if(!this.proveraLozinke(this.novaUlogovan)){
      this.porukaUlogovan="Nova lozinka ne zadovoljava kriterijume";
      return;
    }

    this.servis.dohvatiPoMejlu(this.ulogovan.mejl).subscribe((kor:Korisnik)=>{
      if(!kor){
        this.porukaUlogovan = "Nepostojeci mail";
        return;
      }
      
      if(kor.lozinka != this.staraUlogovan && kor.privremena_lozinka != this.staraUlogovan){
        this.porukaUlogovan = "Niste dobro uneli staru ili privremenu lozinku";
        return;
      }
      if(kor.lozinka != this.staraUlogovan && kor.privremena_lozinka == this.staraUlogovan){
        const sad = new Date();
        const vremeZahteva = new Date(kor.trenutak_zahteva+"");
        const diff = sad.getTime() - vremeZahteva.getTime();

        if((diff)>30*60*1000){
          this.porukaUlogovan = "Proslo je vise od 30 minuta od prethodnog zahteva";
          return;
        } else{
          this.servis.promeniLozinku(kor.mejl, this.novaUlogovan).subscribe(res=>{
            this.porukaUlogovanUspeh = "Uspesna promena lozinke";
            this.staraUlogovan="";
            this.novaUlogovan="";
            this.potvrdaUlogovan="";
            this.router.navigate([""]);           
          })      
        }
      }
      else{
        this.servis.promeniLozinku(kor.mejl, this.novaUlogovan).subscribe(res=>{
          this.porukaUlogovanUspeh = "Uspesna promena lozinke";
          this.staraUlogovan="";
          this.novaUlogovan="";
          this.potvrdaUlogovan="";
          this.router.navigate([""]);  
        }) 
      }
    })
  }


  proveraLozinke(lozinka): boolean{
    let pattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/;
    if(pattern.test(lozinka))
      return true;
    else
      return false;
  }

}
