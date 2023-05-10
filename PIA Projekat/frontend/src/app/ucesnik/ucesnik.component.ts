import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-ucesnik',
  templateUrl: './ucesnik.component.html',
  styleUrls: ['./ucesnik.component.css']
})
export class UcesnikComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);
  }

  ulogovan: Korisnik;

  a(){}

  naProfil(){
    this.router.navigate(["profil"]);
  }

  naMojeRadionice(){
    this.router.navigate(["prijavljeneRadionice"]);
  }

  naPostaniOrganizator(){
    this.router.navigate(["postaniOrganizator"]);
  }

}
