import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);

    this.ime=this.ulogovan.ime;
    this.prezime=this.ulogovan.prezime;
    this.mejl=this.ulogovan.mejl;
    this.korisnicko_ime=this.ulogovan.korisnicko_ime;
    this.telefon=this.ulogovan.telefon;
    this.slika=this.ulogovan.slika;
  }

  ulogovan: Korisnik;
  organizator: boolean;

  ime: string;
  prezime: string;

  mejl: string;
  korisnicko_ime: string;
  telefon: number;


  poruka: string;
  porukaUspeh: string;

  slika: string;
  porukaSlikaGreska: string;
  porukaUspehSlika: string;

  onFileChange(event) {
    this.porukaSlikaGreska = null;

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

        if (img_height > max_height || img_width > max_width || img_height < min_height || img_width < min_width) {
          this.porukaSlikaGreska = 'Nedozvoljene dimenzije slike';
          this.slika=this.ulogovan.slika;
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

  azurirajKorisnickoIme(){
    if(this.korisnicko_ime == this.ulogovan.korisnicko_ime || !this.korisnicko_ime || this.korisnicko_ime==""){
      this.poruka="Niste promenili korisnicko ime";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.dohvatiPoKorImenu(this.korisnicko_ime).subscribe((k:Korisnik)=>{
      if(k){
        this.poruka="Vec postoji korisnik sa odabranim korisnickim imenom";
        return;
      }
      else{
        this.servis.azurirajPromenukorisnickogImena(this.ulogovan.korisnicko_ime,this.korisnicko_ime).subscribe(r=>{
          this.servis.promeniPodatak(this.ulogovan.mejl,this.korisnicko_ime,0).subscribe(r1=>{
            this.ulogovan.korisnicko_ime=this.korisnicko_ime;
            localStorage.setItem("ulogovan",JSON.stringify(this.ulogovan));
            this.porukaUspeh="Uspesno ste promenili korisnicko ime";
            this.poruka="";
            this.porukaSlikaGreska=null;
            this.porukaUspehSlika=null;
            this.ngOnInit();
          })
        })
      }

    })
  }

  azurirajIme(){
    if(this.ime == this.ulogovan.ime || !this.ime || this.ime==""){
      this.poruka="Niste promenili ime";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.promeniPodatak(this.ulogovan.mejl,this.ime,1).subscribe(r=>{
      this.ulogovan.ime=this.ime;
      localStorage.setItem("ulogovan",JSON.stringify(this.ulogovan));
      this.porukaUspeh="Uspesno ste promenili ime";
      this.poruka="";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.ngOnInit();
    })
  }

  azurirajPrezime(){
    if(this.prezime == this.ulogovan.prezime || !this.prezime || this.prezime==""){
      this.poruka="Niste promenili prezime";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.promeniPodatak(this.ulogovan.mejl,this.prezime,2).subscribe(r=>{
      this.ulogovan.prezime=this.prezime;
      localStorage.setItem("ulogovan",JSON.stringify(this.ulogovan));
      this.porukaUspeh="Uspesno ste promenili prezime";
      this.poruka="";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.ngOnInit();
    })
  }

  azurirajMejl(){
    if(this.mejl == this.ulogovan.mejl || !this.mejl || this.mejl==""){
      this.poruka="Niste promenili mejl";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    if(this.proveraMejla()==false){
      this.poruka="Los format mejla";
      return;
    }

    this.servis.dohvatiPoMejlu(this.mejl).subscribe((k:Korisnik)=>{
      if(k){
        this.poruka="Vec postoji korisnik sa odabranim mejlom";
        return;
      }
      this.servis.azurirajPromenuMejla(this.ulogovan.mejl,this.mejl).subscribe(r=>{
        this.servis.promeniPodatak(this.ulogovan.mejl,this.mejl,3).subscribe(r=>{
          this.ulogovan.mejl=this.mejl;
          localStorage.setItem("ulogovan",JSON.stringify(this.ulogovan));
          this.porukaUspeh="Uspesno ste promenili mejl";
          this.poruka="";
          this.porukaSlikaGreska=null;
          this.porukaUspehSlika=null;
          this.ngOnInit();
        })
      }) 
    })
  }

  azurirajTelefon(){
    if(this.telefon == this.ulogovan.telefon || !this.telefon){
      this.poruka="Niste promenili telefon";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.promeniPodatak(this.ulogovan.mejl,this.telefon,4).subscribe(r=>{
      this.ulogovan.telefon=this.telefon;
      localStorage.setItem("ulogovan",JSON.stringify(this.ulogovan));
      this.porukaUspeh="Uspesno ste promenili telefon";
      this.poruka="";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.ngOnInit();
    })
  }

  azurirajSliku(){
    if(this.slika == this.ulogovan.slika || !this.slika){
      this.porukaSlikaGreska="Niste promenili sliku";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.ulogovan.mejl,this.slika,5).subscribe(r=>{
      this.ulogovan.slika=this.slika;
      localStorage.setItem("ulogovan",JSON.stringify(this.ulogovan));
      this.porukaUspehSlika="Uspesno ste promenili sliku";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      //this.ngOnInit();
      window.location.reload();
    })
  }

  proveraMejla(): boolean{
    let pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(pattern.test(this.mejl))
      return true;
    else
      return false;
  }
}
