import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    localStorage.removeItem("ulogovan"); 
  }

  lozinka: string;
  korisnicko_ime: string;
  poruka: string;

  login(){
    this.servis.ulogujSe(this.korisnicko_ime,this.lozinka).subscribe((kor:Korisnik)=>{
      if(!kor){
        this.poruka="Losi kredencijali ili vas zahtev za registraciju i dalje nije odobren";
        return;
      }
      else{
        localStorage.setItem("ulogovan", JSON.stringify(kor));
        if(kor.tip=="ucesnik")
          this.router.navigate(["ucesnik"]);
        else if(kor.tip=="organizator")
          this.router.navigate(["organizator"]);
        else
          this.router.navigate(["admin"]);
        
      }
    })
  }
}
