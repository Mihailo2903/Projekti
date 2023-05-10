import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-meni',
  templateUrl: './meni.component.html',
  styleUrls: ['./meni.component.css']
})
export class MeniComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan)
      this.ulogovan=null;
    if(this.ulogovan){
      this.slika=this.ulogovan.slika;
    }
  }

  ulogovan: Korisnik;
  slika: String;

  naRegistraciju(){
    this.router.navigate(["register"]);
  }

  naPromenuLozinke(){
    this.router.navigate(["promenaLozinke"]);
  }

  naPregled(){
    this.router.navigate(["pregledRadionica"]);
  }

  naTop5(){
    this.router.navigate(["top5Radionica"]);
  }

  naUcesnika(){
    if(this.ulogovan.tip=='ucesnik')
      this.router.navigate(["ucesnik"]);
    else if(this.ulogovan.tip=="organizator")
      this.router.navigate(["organizator"]);
    else
      this.router.navigate(["admin"]);
  }

  naMojeRadionice(){
    this.router.navigate(["istorijaRadionica"]);
  }

  naAktivnosti(){
    this.router.navigate(["aktivnosti"]);
  }

  naPorukeUcesnik(){
    this.router.navigate(["porukeUcesnik"]);
  }

  naPrijavljeneRadionice(){
    this.router.navigate(["prijavljeneRadionice"]);
  }

  naProfil(){
    this.router.navigate(["profil"]);
  }

  naPostaniOrganizator(){
    this.router.navigate(["postaniOrganizator"]);
  }

  naSveRadionice(){
    this.router.navigate(["sveRadionice"]);
  }

  naDodajRadionicu(){
    this.router.navigate(["dodajRadionicu"]);
  }
  
  naSveKorisnike(){
    this.router.navigate(["adminKorisnici"]);
  }

  naRadioniceAdmin(){
    this.router.navigate(["adminRadionice"]);
  }
}
