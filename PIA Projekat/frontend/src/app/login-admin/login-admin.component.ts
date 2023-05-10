import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-login-admin',
  templateUrl: './login-admin.component.html',
  styleUrls: ['./login-admin.component.css']
})
export class LoginAdminComponent implements OnInit {
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
        if(kor.tip!="admin"){
          this.poruka="Uneti kredencijali ne pripadaju adminu";
          return;
        }
        else{
          localStorage.setItem("ulogovan", JSON.stringify(kor));
          this.router.navigate(["admin"]);
        }
      }
    })
  }
}
