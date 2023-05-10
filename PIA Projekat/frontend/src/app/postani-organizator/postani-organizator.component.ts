import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-postani-organizator',
  templateUrl: './postani-organizator.component.html',
  styleUrls: ['./postani-organizator.component.css']
})
export class PostaniOrganizatorComponent implements OnInit {
  
  constructor(private servis:RadionicaServisService, private router:Router){}

  ngOnInit(): void {
    this.ulogovan=JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="ucesnik")
      this.router.navigate([""]);
      
    this.sporedneSlike=new Array();
    this.slika=null;

    this.servis.proveriPostojanjePredlogaRadionice(this.ulogovan.korisnicko_ime).subscribe((r:Radionica)=>{
      if(r){
        this.vecPostoji=true;
        this.radionica=r;
        this.radionica.datum=new Date(this.radionica.datum);
        this.radionica.duzi_opis= this.uredi(this.radionica.duzi_opis,50);
      }
      else
        this.vecPostoji=false;
    })
  }

  ulogovan: Korisnik;

  vecPostoji: boolean;
  radionica: Radionica;

  naziv: string;
  mesto: string;
  datum: Date;
  kratak: string;
  dug: string;
  broj_mesta: number;

  poruka:string;

  slika: string;
  sporedneSlike: Array<String>;

  onFileChange(event) {
    this.slika = null;

    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();   
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (e: any) => {
        const image = new Image();
        image.src = e.target.result;
        image.onload = rs => {
            const imgBase64Path = e.target.result;
            this.slika = imgBase64Path;
            return true     
        };    
      }     
    }
  }

  previseSlika: string;

  onFileChangeMultiple(event) {
    this.previseSlika = null;

    if (event.target.files && event.target.files[0]) {
      for(let file of event.target.files){
        const reader = new FileReader();   
        reader.readAsDataURL(file);
        reader.onload = (e: any) => {
        const image = new Image();
          image.src = e.target.result;
          image.onload = rs => {
            if(this.sporedneSlike.length >= 5){
              this.previseSlika="Ne možete uneti više od 5 slika";
            }
            else{
              const imgBase64Path = e.target.result;
              this.sporedneSlike.push(imgBase64Path);
            }
          };
        };
      } 
    }
  }

  izbaci(index){
    let novi = new Array();
    let ind=0;
    this.sporedneSlike.forEach(element=>{
      if(index != ind)
        novi.push(element);
      ind++;
    })
    this.sporedneSlike=novi;
  }

  proveriPodatke(): boolean{
    let ok=true;
    this.poruka="";
    if(!this.naziv || this.naziv==""){
      ok=false;
      this.poruka= this.poruka + "Niste uneli naziv\n";
    }
    if(!this.mesto || this.mesto==""){
      ok=false;
      this.poruka= this.poruka + "Niste uneli mesto održavanja\n";
    }
    if(!this.datum){
      ok=false;
      this.poruka= this.poruka + "Niste uneli datum održavanja\n";
    }
    if(this.datum && new Date(this.datum).getTime() < new Date().getTime()){
      ok=false;
      this.poruka= this.poruka + "Datum održavanja je u prošlosti\n";
    }
    if(!this.broj_mesta || this.broj_mesta<=0){
      ok=false;
      this.poruka= this.poruka + "Niste ispravno uneli broj mesta\n";
    }
    if(!this.dug || this.dug==""){
      ok=false;
      this.poruka= this.poruka + "Niste uneli duzi opis\n";
    }
    if(!this.kratak || this.kratak==""){
      ok=false;
      this.poruka= this.poruka + "Niste uneli kratak opis\n";
    }
    if(!this.slika){
      ok=false;
      this.poruka= this.poruka + "Niste uneli glavnu sliku\n";
    }
    return ok;
  }

  posaljiPredlog(){
    if(!this.proveriPodatke())
      return;

    let body={
      organizator: this.ulogovan.korisnicko_ime,
      naziv: this.naziv,
      datum: this.datum,
      mesto: this.mesto,
      kratak_opis: this.kratak,
      duzi_opis: this.dug,
      glavna_slika: this.slika,
      slike: this.sporedneSlike,
      preostalo_mesta: this.broj_mesta,
      status: "neodobrena"
    }

    this.servis.dodajRadionicu(body).subscribe(re=>{
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
