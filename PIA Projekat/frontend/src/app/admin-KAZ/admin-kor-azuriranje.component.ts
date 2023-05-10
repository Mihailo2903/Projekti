import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-admin-kor-azuriranje',
  templateUrl: './admin-kor-azuriranje.component.html',
  styleUrls: ['./admin-kor-azuriranje.component.css']
})
export class AdminKorAzuriranjeComponent implements OnInit {
  id: string;

  constructor(private servis: KorisnikServisService, private router: Router, private route: ActivatedRoute){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="admin")
      this.router.navigate([""]);

    this.route.params.subscribe(params=>{
      this.id=params['id'];
      this.servis.dohvatiPoKorImenu(this.id).subscribe((k:Korisnik)=>{
        this.trenutni = k;

        this.ime=this.trenutni.ime;
        this.prezime=this.trenutni.prezime;
        this.mejl=this.trenutni.mejl;
        this.korisnicko_ime=this.trenutni.korisnicko_ime;
        this.telefon=this.trenutni.telefon;
        this.slika=this.trenutni.slika;

        if(this.trenutni.tip=="organizator"){
          this.organizator=true;
          this.naziv_organizacije=this.trenutni.naziv_organizacije;
          this.drzava=this.trenutni.drzava;
          this.grad=this.trenutni.grad;
          this.ulica=this.trenutni.ulica;
          this.broj=this.trenutni.broj;
          this.maticni_broj=this.trenutni.maticni_broj;
        }
      })
    })

  }

  trenutni: Korisnik;
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
          this.slika=this.trenutni.slika;
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
    if(this.korisnicko_ime == this.trenutni.korisnicko_ime || !this.korisnicko_ime || this.korisnicko_ime==""){
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
        this.servis.azurirajPromenukorisnickogImena(this.trenutni.korisnicko_ime,this.korisnicko_ime).subscribe(r=>{
          this.servis.promeniPodatak(this.trenutni.mejl,this.korisnicko_ime,0).subscribe(r1=>{
            this.porukaUspeh="Uspesno ste promenili korisnicko ime";
            this.poruka="";
            this.porukaSlikaGreska=null;
            this.porukaUspehSlika=null;
            this.router.navigate(["azurirajKorisnika",this.korisnicko_ime]);
          })
        })
      }

    })
  }

  azurirajIme(){
    if(this.ime == this.trenutni.ime || !this.ime || this.ime==""){
      this.poruka="Niste promenili ime";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.ime,1).subscribe(r=>{
      this.porukaUspeh="Uspesno ste promenili ime";
      this.poruka="";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.ngOnInit();
    })
  }

  azurirajPrezime(){
    if(this.prezime == this.trenutni.prezime || !this.prezime || this.prezime==""){
      this.poruka="Niste promenili prezime";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.prezime,2).subscribe(r=>{
      this.porukaUspeh="Uspesno ste promenili prezime";
      this.poruka="";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.ngOnInit();
    })
  }

  azurirajMejl(){
    if(this.mejl == this.trenutni.mejl || !this.mejl || this.mejl==""){
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
      this.servis.azurirajPromenuMejla(this.trenutni.mejl,this.mejl).subscribe(r=>{
        this.servis.promeniPodatak(this.trenutni.mejl,this.mejl,3).subscribe(r=>{
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
    if(this.telefon == this.trenutni.telefon || !this.telefon){
      this.poruka="Niste promenili telefon";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.porukaUspeh = "";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.telefon,4).subscribe(r=>{
      this.porukaUspeh="Uspesno ste promenili telefon";
      this.poruka="";
      this.porukaSlikaGreska=null;
      this.porukaUspehSlika=null;
      this.ngOnInit();
    })
  }

  azurirajSliku(){
    if(this.slika == this.trenutni.slika || !this.slika){
      this.porukaSlikaGreska="Niste promenili sliku";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.slika,5).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili sliku";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }

  proveraMejla(): boolean{
    let pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(pattern.test(this.mejl))
      return true;
    else
      return false;
  }

  naziv_organizacije: string;
  drzava: string;
  grad: string;
  ulica: string;
  broj: number;
  maticni_broj:number;

  azurirajOrg(){
    if(this.naziv_organizacije == this.trenutni.naziv_organizacije || !this.naziv_organizacije){
      this.porukaSlikaGreska="Niste promenili naziv organizacije";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.naziv_organizacije,6).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili naziv organizacije";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }

  azurirajDrzavu(){
    if(this.drzava == this.trenutni.drzava || !this.drzava){
      this.porukaSlikaGreska="Niste promenili drzavu";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.drzava,7).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili drzavu";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }

  azurirajGrad(){
    if(this.grad == this.trenutni.grad || !this.grad){
      this.porukaSlikaGreska="Niste promenili grad";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.grad,8).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili grad";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }

  azurirajUlicu(){
    if(this.ulica == this.trenutni.ulica || !this.ulica){
      this.porukaSlikaGreska="Niste promenili ulicu";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.ulica,9).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili ulicu";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }

  azurirajBroj(){
    if(this.broj == this.trenutni.broj || !this.broj){
      this.porukaSlikaGreska="Niste promenili broj";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.broj,10).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili broj";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }

  azurirajMaticni(){
    if(this.maticni_broj == this.trenutni.maticni_broj || !this.maticni_broj){
      this.porukaSlikaGreska="Niste promenili maticni broj";
      this.porukaUspehSlika=null;
      this.poruka="";
      this.porukaUspeh="";
      return;
    }
    this.servis.promeniPodatak(this.trenutni.mejl,this.maticni_broj,11).subscribe(r=>{
      this.porukaUspehSlika="Uspesno ste promenili maticni broj";
      this.poruka="";
      this.porukaUspeh="";
      this.porukaSlikaGreska=null;
      this.ngOnInit();
    })
  }



}
