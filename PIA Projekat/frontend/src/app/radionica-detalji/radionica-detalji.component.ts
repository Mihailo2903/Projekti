import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Poruka } from '../models/poruka';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-radionica-detalji',
  templateUrl: './radionica-detalji.component.html',
  styleUrls: ['./radionica-detalji.component.css']
})
export class RadionicaDetaljiComponent implements OnInit {
  id: string;

  constructor(private servis: RadionicaServisService, private router: Router, private route: ActivatedRoute, private korSer: KorisnikServisService){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan)
      this.router.navigate([""]);

    this.imeDugmeta = "Prikaži poruke";
    this.images=[]
    this.route.params.subscribe(params=>{
      this.id=params['id'];
      this.servis.dohvatiRadionicuPoID(this.id).subscribe((r:Radionica)=>{
        this.radionica=r;
        this.radionica.datum = new Date(this.radionica.datum);
        this.images.push(r.glavna_slika);
        r.slike.forEach(element => {
          this.images.push(element);
        });

        this.rezervisao=false;
        this.radionica.rezervisani.forEach(element=>{
          if(element == this.ulogovan.korisnicko_ime){
            this.rezervisao=true;
          }
        });

        this.naCekanju=false;
        this.radionica.naCekanju.forEach(element=>{
          if(element == this.ulogovan.mejl){
            this.naCekanju=true;
          }
        })

        this.servis.proveriUcesce(this.radionica._id,this.ulogovan.korisnicko_ime).subscribe(res=>{
          if(res) 
            this.ucestvuje=true;
          else
            this.ucestvuje=false;

          this.ucestvovaoNaRadSaIstimNazivom=false;
          this.servis.bivseRadioniceSaIstimNazivom(this.radionica.naziv, this.ulogovan.korisnicko_ime).subscribe((radion:Ucesce[])=>{
            radion.forEach(rad=>{
              this.servis.dohvatiRadionicuPoID(rad.radionica).subscribe((ra:Radionica)=>{
                if(ra && new Date(ra.datum).getTime() < new Date().getTime())
                  this.ucestvovaoNaRadSaIstimNazivom=true;
              })
            })
          })
        })

      })
    })
  }


  ulogovan: Korisnik;

  radionica: Radionica;
  images: Array<String>;

  ucestvuje: boolean;
  rezervisao: boolean;
  naCekanju: boolean;

  ucestvovaoNaRadSaIstimNazivom: boolean;

  prijaviMe(){
    this.servis.rezervisiMestoNaRadionici(this.radionica._id,this.ulogovan.korisnicko_ime).subscribe(res=>{
      this.ngOnInit();
    })
  }

  dodajNaListu(){
    this.servis.dodajNaListuZacekanje(this.radionica._id,this.ulogovan.mejl).subscribe(res=>{
      this.ngOnInit();
    })
  }

  poruke: Poruka[];

  organizator: String;
  slikaOrg: string;

  imeDugmeta: string;

  pogledajAktivnosti(){
    this.router.navigate(['radionicaAktivnosti',this.radionica._id]);
  }

  otvori(){
    if(this.imeDugmeta=="Prikaži poruke"){
      this.servis.dohvatiPoruke(this.ulogovan.korisnicko_ime, this.radionica._id).subscribe((por:Poruka[])=>{
        this.poruke=por;
        this.poruke.forEach(por=>{
          por.datum= new Date(por.datum);
          por.sadrzaj = this.uredi(por.sadrzaj, 75);
        })
        this.organizator=this.radionica.organizator;
        this.korSer.dohvatiPoKorImenu(this.radionica.organizator).subscribe((k:Korisnik)=>{
          this.slikaOrg = k.slika;
        })
        this.imeDugmeta = "Sakrij poruke"
      })
    }

    else{
      this.imeDugmeta = "Prikaži poruke"
    }
  }


  uredi(pocetni: string, karakteraUredu: number){
    let razmak=-1; let uRedu=0;
    let novi="";
    for(let i=0; i<pocetni.length; i++){
      novi = novi + pocetni.charAt(i);
      if(pocetni.charAt(i)==" ")
        razmak=i;
      uRedu++;
      if(pocetni.charAt(i)=="\n"){
        uRedu=0;
        razmak=-1;
      }
      if(uRedu==karakteraUredu){
        uRedu=0;
        if(razmak == -1)
          novi=novi+"\n";
        else
          novi=this.replaceAt(novi,razmak,"\n");
        razmak=-1;
      }
    }
    return novi;
  }

  replaceAt(str, index, replacement) {
    return str.substring(0, index) + replacement + str.substring(index + replacement.length);
  }

  novaPoruka: string;

  posaljiPoruku(){
    if(this.novaPoruka){
      this.servis.posaljiPoruku(this.radionica._id,this.ulogovan.korisnicko_ime,this.novaPoruka,"korisnik").subscribe(r=>{
        this.servis.dohvatiPoruke(this.ulogovan.korisnicko_ime, this.radionica._id).subscribe((por:Poruka[])=>{
          this.poruke=por;
          this.poruke.forEach(por=>{
            por.datum= new Date(por.datum);
            por.sadrzaj = this.uredi(por.sadrzaj, 75);
          })
          this.novaPoruka=null;
        })
      })
    }
  }

}
