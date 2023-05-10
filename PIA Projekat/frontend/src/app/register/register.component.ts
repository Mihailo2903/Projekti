import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  constructor(private servis:KorisnikServisService, private router:Router){}

  ngOnInit(): void {
    this.ulogovan=JSON.parse(localStorage.getItem("ulogovan"));
    if(this.ulogovan && (this.ulogovan.tip=="ucesnik" || this.ulogovan.tip=="organizator"))
      this.router.navigate([""]);
  }

  ulogovan:Korisnik;

  ime: string;
  prezime: string;
  lozinka: string;
  potvrda: string;
  mejl: string;
  tip: string;
  korisnicko_ime: string;
  telefon: number;

  organizator: boolean;

  naziv_organizacije: string;
  drzava: string;
  grad: string;
  postanski_broj: number;
  ulica: string;
  broj: number;
  maticni_broj: number;

  poruka: string;

  slika: string;
  porukaSlikaGreska: string;

  register(){
    this.poruka="";
    let ispravno = this.proveriUnosPodataka();
    if(!ispravno)
      return; 
    ispravno = this.proveriLozinkuIMejl();
    if(!ispravno)
      return;

    let status;
    if(this.ulogovan && this.ulogovan.tip=="admin")
      status="aktivan";
    else
      status="naCekanju";
    
    if(!this.organizator){
      let body={
        ime:this.ime,
        prezime:this.prezime,
        korisnicko_ime: this.korisnicko_ime,
        tip: "ucesnik",
        lozinka:this.lozinka,
        telefon:this.telefon,
        mejl:this.mejl,
        status: status,
        privremena_lozinka: null,
        trenutak_zahteva: null,
        slika: this.slika
      }
      
      this.servis.proveriJedinstvenost(this.korisnicko_ime,this.mejl).subscribe(korisnik=>{
        if(korisnik){
          this.poruka = this.poruka + "U sistemu vec postoji korisnik sa unetim korisnickim imenom ili mejlom";
          return;
        }
        else{
          this.servis.registrujUcensika(body).subscribe(odgovor=>{
            if(odgovor["poruka"]=="OK")
              alert("Ucesnik uspesno registrovan");
              if(!this.ulogovan)
                this.router.navigate([""]);
              else
                this.router.navigate(["admin"]);
          })
        }
      })

    }
    else{   
      let body={
        ime:this.ime,
        prezime:this.prezime,
        korisnicko_ime: this.korisnicko_ime,
        tip: "organizator",
        lozinka:this.lozinka,
        telefon:this.telefon,
        mejl:this.mejl,
        naziv_organizacije: this.naziv_organizacije,
        drzava: this.drzava,
        grad: this.grad,
        postanski_broj: this.postanski_broj,
        ulica: this.ulica,
        broj: this.broj,
        maticni_broj: this.maticni_broj,
        status: status,
        privremena_lozinka: null,
        trenutak_zahteva: null,
        slika: this.slika
      }
      
      this.servis.proveriJedinstvenost(this.korisnicko_ime,this.mejl).subscribe(korisnik=>{
        if(korisnik){
          this.poruka = this.poruka + "U sistemu vec postoji korisnik sa unetim korisnickim imenom ili mejlom";
          return;
        }
        else{
          this.servis.registrujOrganizatora(body).subscribe(odgovor=>{
            if(odgovor["poruka"]=="OK")
              alert("Organizator uspesno registrovan");
              if(!this.ulogovan)
                this.router.navigate([""]);
              else
                this.router.navigate(["admin"]);
          })
        }
      })
    }
   
  }

  proveraLozinke(): boolean{
    let pattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/;
    if(pattern.test(this.lozinka))
      return true;
    else
      return false;
  }

  proveraMejla(): boolean{
    let pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(pattern.test(this.mejl))
      return true;
    else
      return false;
  }

  proveriUnosPodataka(): boolean{
    let ok = true;
    if(!this.korisnicko_ime){
      this.poruka=this.poruka+"Niste uneli korisnicko ime" + "\n";
      ok=false;
    }
    if(!this.ime){
      this.poruka=this.poruka+"Niste uneli ime\n";
      ok=false;
    }
    if(!this.prezime){
      this.poruka=this.poruka+"Niste uneli prezime\n";
      ok=false;
    }
    if(!this.lozinka){
      this.poruka=this.poruka+"Niste uneli lozinku\n";
      ok=false;
    }
    if(!this.potvrda){
      this.poruka=this.poruka+"Niste potvrdili lozinku\n";
      ok=false;
    }
    if(!this.mejl){
      this.poruka=this.poruka+"Niste uneli mejl\n";
      ok=false;
    }
    if(!this.telefon){
      this.poruka=this.poruka+"Niste uneli broj telefona\n";
      ok=false;
    }

    return ok;
  }

  proveriLozinkuIMejl(): boolean{
    let ok = true;
    if(!this.proveraLozinke()){
      this.poruka = this.poruka + "Lozinka nije u odgovarajucem formatu\n";
      ok=false;
    }
    if(this.lozinka != this.potvrda){
      this.poruka = this.poruka + "Niste dobro potvrdili lozinku\n";
      ok=false;
    }     
    if(!this.proveraMejla()){
      this.poruka = this.poruka + "Mejl nije u odgovarajucem formatu\n";
      ok=false;
    }
    return ok;
  }

  onFileChange(event) {
    this.porukaSlikaGreska = null;
    this.slika = null;
    if (event.target.files && event.target.files[0]) {

      const max_height = 300;
      const max_width = 300;
      const min_height = 100;
      const min_width = 100;

      
      const reader = new FileReader();   
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (e: any) => {
      const image = new Image();
      image.src = e.target.result;
      image.onload = rs => {
        const img_height = rs.currentTarget['height'];
        const img_width = rs.currentTarget['width'];

       /* console.log(img_height, img_width);
        alert(img_height + " " + img_width);*/

        if (img_height > max_height || img_width > max_width || img_height < min_height || img_width < min_width) {
          this.porukaSlikaGreska = 'Nedozvoljene dimenzije slike'
          return false;
        } else {
          const imgBase64Path = e.target.result;
          this.slika = imgBase64Path;
          return true
        }
      };    
      }     
    }
  }


}