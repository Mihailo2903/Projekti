import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-admin-radionice',
  templateUrl: './admin-radionice.component.html',
  styleUrls: ['./admin-radionice.component.css']
})
export class AdminRadioniceComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router, private korSer: KorisnikServisService){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="admin")
      this.router.navigate([""]);

    this.aktivneRadionice=[];
    this.onemoguceniKorisnici=[];

    this.servis.dohvatiRadionice().subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
        if(rad.datum.getTime()>new Date().getTime()){
          this.aktivneRadionice.push(rad._id);
          rad.rezervisani.forEach(rez=>{
            if(!this.onemoguceniKorisnici.includes(rez))
              this.onemoguceniKorisnici.push(rez);
          })
        }
      })
      
      this.servis.dohvatiNeodobreneRadionice().subscribe((n:Radionica[])=>{
        this.neodobrene=n;
        this.neodobrene.forEach(rad=>{
          rad.datum = new Date(rad.datum);
        })

        this.servis.dohvatiSvaUcesca().subscribe((u:Ucesce[])=>{
          this.svaUcesca=u;
          this.svaUcesca.forEach(ucesce=>{
            if(this.aktivneRadionice.includes(ucesce.radionica.toString())){
              if(!this.onemoguceniKorisnici.includes(ucesce.korisnik.toString()))
                this.onemoguceniKorisnici.push(ucesce.korisnik.toString());
            }
          })

          this.neodobrene.forEach(neodob=>{
            if(this.onemoguceniKorisnici.includes(neodob.organizator)){
              neodob.uslov=false;
            }
            else{
              neodob.uslov=true;
            }

          })

        })

      })

    })
  }


  ulogovan: Korisnik;

  radionice: Radionica[];
  neodobrene: Radionica[];
  svaUcesca: Ucesce[];
  aktivneRadionice: string[];

  onemoguceniKorisnici: string[];

  azuriraj(r:Radionica){
    this.router.navigate(["uredjivanjeRadionice", r._id]);
  }

  odbij(rad:Radionica){
    this.servis.postaviStatusRadionice(rad._id,"odbijena").subscribe(r=>{
      this.ngOnInit();
    })
  }

  odobri(rad:Radionica){
    this.servis.postaviStatusRadionice(rad._id,"odobrena").subscribe(r=>{
      this.korSer.unaprediUOrganizatora(rad.organizator).subscribe(e=>{
        this.ngOnInit();
      })
    })
  }

  otvoriDetalje(r:Radionica){
    this.router.navigate(['radionicaDetalji',r._id]);
  }

  obrisi(r:Radionica){
    this.servis.obrisiRadionicu(r._id).subscribe(r=>{
      this.ngOnInit();
    })
  }

}
