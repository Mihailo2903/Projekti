import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Aktivnost } from '../models/aktivnost';
import { AktivnostWrapper } from '../models/aktivnostiWrapper';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-admin-korisnici',
  templateUrl: './admin-korisnici.component.html',
  styleUrls: ['./admin-korisnici.component.css']
})
export class AdminKorisniciComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router, private radSer: RadionicaServisService){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="admin")
      this.router.navigate([""]);

    this.servis.dohvatiSveKorisnike().subscribe((r:Korisnik[])=>{
      this.korisnici=r;
      this.korisnici = this.korisnici.filter(kor=>kor.status!="neaktivan")
    })
  }

  ulogovan: Korisnik;

  korisnici: Korisnik[];

  azuriraj(k:Korisnik){
    this.router.navigate(["azurirajKorisnika",k.korisnicko_ime]);
  }

  prihvati(k:Korisnik){
    this.servis.postaviStatus(k.korisnicko_ime,"aktivan").subscribe(k=>{
      this.ngOnInit();
    })
  }

  odbij(k:Korisnik){
    this.servis.postaviStatus(k.korisnicko_ime,"neaktivan").subscribe(k=>{
      this.ngOnInit();
    })
  }

  obrisi(k:Korisnik){
    let mojeRadionice;
    let mojeAktivnosti;
    let zaBrisanje: AktivnostWrapper[];
    zaBrisanje = [];

    this.radSer.dohvatiRadioniceOrganizatora(k.korisnicko_ime).subscribe((r:Radionica[])=>{
      mojeRadionice = r;
      this.radSer.dohvatiAktivnosti(k.korisnicko_ime).subscribe((ak:Aktivnost[])=>{
        mojeAktivnosti=ak;
        mojeAktivnosti.forEach(aktivnost=>{
          let rad = aktivnost.radionica;
          let nasao = false;
          zaBrisanje.forEach(bris=>{
            if(bris.radionica==rad){
              nasao=true;
              if(aktivnost.tip=="lajk")
                bris.broj_lajkova+=1;
              else
                bris.broj_komentara+=1;
            }
          })

          if(nasao==false){
            let a = new AktivnostWrapper();
            a.radionica = rad;
            if(aktivnost.tip=="lajk"){
              a.broj_komentara=0;
              a.broj_lajkova=1;
            }
            else{
              a.broj_komentara=1;
              a.broj_lajkova=0;
            }
            zaBrisanje.push(a);
          }
        })

        zaBrisanje.forEach(br=>{
          this.radSer.azurirajAktivnostiRadionicePosleBrisanjaKorisnika(br.radionica,br.broj_lajkova,br.broj_komentara).subscribe(e=>{})
        })

        this.servis.obrisiKorisnika(k.korisnicko_ime,k.mejl).subscribe(k=>{
          mojeRadionice.forEach(element=>{
            this.radSer.obrisiRadionicu(element._id).subscribe(e=>{
            })
          })
          this.ngOnInit();
        })  
      })
    })

  }

}
