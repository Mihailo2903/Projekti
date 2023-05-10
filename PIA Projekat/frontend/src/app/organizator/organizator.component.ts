import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-organizator',
  templateUrl: './organizator.component.html',
  styleUrls: ['./organizator.component.css']
})
export class OrganizatorComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="organizator")
      this.router.navigate([""]);
  }

  ulogovan: Korisnik;

  naSveRadionice(){
    this.router.navigate(["sveRadionice"]);
  }

  naDodaj(){
    this.router.navigate(["dodajRadionicu"]);
  }

}
