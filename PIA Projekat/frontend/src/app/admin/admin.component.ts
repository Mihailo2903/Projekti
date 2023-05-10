import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="admin")
      this.router.navigate([""]);
    
  }

  ulogovan: Korisnik;

  naKorisnike(){
    this.router.navigate(["adminKorisnici"]);
  }

  dodajKorisnika(){
    this.router.navigate(["register"]);
  }

  naRadionice(){
    this.router.navigate(["adminRadionice"]);
  }

  dodajRadionicu(){
    this.router.navigate(["dodajRadionicu"]);
  }

}

