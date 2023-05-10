import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { elementAt } from 'rxjs';
import { Aktivnost } from '../models/aktivnost';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-aktivnosti-radionice',
  templateUrl: './aktivnosti-radionice.component.html',
  styleUrls: ['./aktivnosti-radionice.component.css']
})
export class AktivnostiRadioniceComponent implements OnInit {
  id: string;

  constructor(private servis: RadionicaServisService, private router: Router, private route: ActivatedRoute, private korSer: KorisnikServisService){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);

    this.route.params.subscribe(params=>{
      this.id=params['id'];
      
      this.servis.dohvatiRadionicuPoID(this.id).subscribe((ra:Radionica)=>{
        this.radionica=ra;

        this.servis.dohvatiAktivnostiRadionice(this.id).subscribe((a:Aktivnost[])=>{
          this.aktivnosti=a;
          this.aktivnosti.forEach(ak=>{
            if(ak.tip=="komentar"){
              ak.sadrzaj = this.uredi(ak.sadrzaj,50);
            }
            ak.datum = new Date(ak.datum);
          })
          this.aktivnosti.sort((a,b)=>{
            if(a.datum.getTime() > b.datum.getTime()) return -1;
            else return 1;
          })
          
          this.vecLajkovao=false;
          this.aktivnosti.forEach(element=>{
            if(element.tip=="lajk" && element.korisnik==this.ulogovan.korisnicko_ime)
              this.vecLajkovao=true;
          })
  
        })

      })

      

    })   
  }

  ulogovan: Korisnik;
  radionica: Radionica;
  aktivnosti: Aktivnost[];

  noviKomentar: String;

  vecLajkovao: boolean;


  lajkuj(){
    this.servis.dodajAktivnost(this.radionica._id,this.radionica.naziv,this.ulogovan.korisnicko_ime,"lajk",null).subscribe(r=>{
      this.ngOnInit();
    })
  }
  
  komentarisi(){
    if(!this.noviKomentar || this.noviKomentar=="")
      return;
    this.servis.dodajAktivnost(this.radionica._id,this.radionica.naziv,this.ulogovan.korisnicko_ime,"komentar",this.noviKomentar).subscribe(r=>{
      this.noviKomentar=null;
      this.ngOnInit();
    })
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

}
