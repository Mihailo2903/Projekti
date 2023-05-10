import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-pregled-radionica',
  templateUrl: './pregled-radionica.component.html',
  styleUrls: ['./pregled-radionica.component.css']
})
export class PregledRadionicaComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan)
      this.ulogovan=null;

    this.servis.dohvatiRadionice().subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
      })
      this.radionice = this.radionice.filter(r=>r.datum.getTime()>new Date().getTime());
    })
  }


  ulogovan: Korisnik;

  radionice: Radionica[];
  naziv: string;
  mesto:string;

  otvoriDetalje(r:Radionica){
    this.router.navigate(['radionicaDetalji',r._id]);
  }

  filterNaziv(){
    this.servis.dohvatiRadionicePoNazivu(this.naziv).subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
      })
    })
  }

  filterMesto(){  
    this.servis.dohvatiRadionicePoMestu(this.mesto).subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
      })
    })  
  }


  filterOba(){
      this.servis.dohvatiRadionicePoOba(this.naziv, this.mesto).subscribe((r:Radionica[])=>{
        this.radionice=r;
        this.radionice.forEach(rad=>{
          rad.datum = new Date(rad.datum);
        })
      })
  }

  sortDatum(){
    this.radionice.sort((a,b)=>{
      return a.datum.getTime() - b.datum.getTime();
    })
  }

  sortNaziv(){
    this.radionice.sort((a,b)=>{
      if(a.naziv < b.naziv) return -1;
      else return 1;
    })
  }

}
