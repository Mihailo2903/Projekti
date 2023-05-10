import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-dodaj-radionicu',
  templateUrl: './dodaj-radionicu.component.html',
  styleUrls: ['./dodaj-radionicu.component.css']
})
export class DodajRadionicuComponent implements OnInit {
  
  constructor(private servis:RadionicaServisService, private router:Router){}

  ngOnInit(): void {
    this.ulogovan=JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || (this.ulogovan.tip!="admin" && this.ulogovan.tip!="organizator"))
      this.router.navigate([""]);

    this.sporedneSlike=new Array();
    this.slika=null;
    this.naziv=null;
    this.mesto=null;
    this.datum=null;
    this.kratak=null;
    this.dug=null;
    this.broj_mesta=null;
  }

  ulogovan: Korisnik;

  naziv: string;
  mesto: string;
  datum: string;
  kratak: string;
  dug: string;
  broj_mesta: number;

  poruka:string;

  slika: string;
  sporedneSlike: Array<String>;
  porukaUspeh: string;

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

    let status;
    if(this.ulogovan.tip=="admin")
      status="odobrena";
    else
      status="neodobrena";

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
      status: status
    }

    this.servis.dodajRadionicu(body).subscribe(re=>{
      this.porukaUspeh="Uspesno ste dodali radionicu";
      this.ngOnInit();
    })
  }

  
  importJson(event) {
    this.ngOnInit();
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (e) => {
      const data = JSON.parse(reader.result as string);
      this.naziv=data["naziv"];
      this.mesto=data["mesto"];
      this.kratak=data["kratak_opis"];
      this.dug=data["duzi_opis"];
      this.slika=data["glavna_slika"];
      this.sporedneSlike=data["slike"];
     
      let dat = new Date(data["datum"]);

      this.datum = dat.getFullYear()+"-";
        if(dat.getMonth()<9) this.datum = this.datum + "0";
        this.datum=this.datum + (dat.getMonth()+1)+"-";
        if(dat.getDate()<10) this.datum = this.datum + "0";
        this.datum=this.datum + (dat.getDate());
    };
    reader.readAsText(file);
  }

}
