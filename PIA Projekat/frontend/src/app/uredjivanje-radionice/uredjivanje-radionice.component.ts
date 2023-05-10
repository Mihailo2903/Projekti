import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { Ucesce } from '../models/ucesce';
import { KorisnikServisService } from '../services/korisnik-servis.service';
import { RadionicaServisService } from '../services/radionica-servis.service';
import { saveAs } from 'file-saver';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-uredjivanje-radionice',
  templateUrl: './uredjivanje-radionice.component.html',
  styleUrls: ['./uredjivanje-radionice.component.css']
})
export class UredjivanjeRadioniceComponent implements OnInit {
  id: string;

  constructor(private servis: RadionicaServisService, private router: Router, private route: ActivatedRoute, private korSer: KorisnikServisService){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip=="ucesnik")
      this.router.navigate([""]);

    this.previseSlika=null;

    this.route.params.subscribe(params=>{
      this.id=params['id'];
      this.servis.dohvatiRadionicuPoID(this.id).subscribe((r:Radionica)=>{
        this.radionica=r;
        this.radionica.datum = new Date(this.radionica.datum);

        this.naziv=this.radionica.naziv;
        this.mesto=this.radionica.mesto;
        this.kratak=this.radionica.kratak_opis;
        this.dug=this.radionica.duzi_opis;
        
        let dat = this.radionica.datum;
        this.datum = dat.getFullYear()+"-";
        if(dat.getMonth()<9) this.datum = this.datum + "0";
        this.datum=this.datum + (dat.getMonth()+1)+"-";
        if(dat.getDate()<10) this.datum = this.datum + "0";
        this.datum=this.datum + (dat.getDate());

        this.slika=this.radionica.glavna_slika;
        this.sporedneSlike=[];
        this.radionica.slike.forEach(elem=>{
          this.sporedneSlike.push(elem);
        })

        this.servis.dohvatiUcescaNaRadionici(this.radionica._id).subscribe((uc:Ucesce[])=>{
          this.ucesca=uc;
        })
      })
    })
  }


  ulogovan: Korisnik;

  radionica: Radionica;

  naziv: string;
  mesto: string;
  datum: string;
  kratak: string;
  dug: string;
 
  poruka:string;
  previseSlika: string;

  slika: string;
  sporedneSlike: Array<String>;

  ucesca: Ucesce[];
  

  azuriraj(){
    if(!this.proveriPodatke())
      return;
    if(this.proveriPromenu()){
      let drugiNaziv = false;
      if(this.naziv!=this.radionica.naziv)
        drugiNaziv=true;

      let body={
        id: this.radionica._id,
        naziv: this.naziv,
        mesto: this.mesto,
        datum: this.datum,
        kratak_opis: this.kratak,
        duzi_opis: this.dug,
        glavna_slika: this.slika,
        slike: this.sporedneSlike
      }
      this.servis.azurirajRadionicu(body).subscribe(re=>{
        if(drugiNaziv){
          this.servis.azurirajNazivRadionice(this.radionica._id,this.naziv).subscribe(r=>{
            this.ngOnInit();
          })
        }
      })
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

  otkazi(){
    let naCek=[];
    let pomocni=[];
    this.radionica.naCekanju.forEach(elem=>{naCek.push(elem)});
    this.radionica.rezervisani.forEach(elem=>{pomocni.push(elem)});
    this.ucesca.forEach(elem=>{pomocni.push(elem.korisnik)});

    this.servis.otkaziRadionicu(this.radionica._id).subscribe(r=>{
      naCek.forEach(element=>{
        this.korSer.posaljiObavestenjeOOtkazivanjuRadionice(element,this.radionica.naziv).subscribe(re=>{
        })
      })

      pomocni.forEach(elemnt=>{
        this.korSer.dohvatiPoKorImenu(elemnt).subscribe((k:Korisnik)=>{
          this.korSer.posaljiObavestenjeOOtkazivanjuRadionice(k.mejl,this.radionica.naziv).subscribe(r=>{});
        })
      })

      this.router.navigate(["organizator"]);
    })

  }

  odobri(korisnik){
    this.servis.prihvatiRezervaciju(this.radionica._id,this.radionica.naziv,korisnik).subscribe(r=>{
      this.ngOnInit();
    })
  }

  odbij(korisnik){
    let arr = []
    this.radionica.naCekanju.forEach(element => {
      arr.push(element);
    });
    this.servis.odbijRezervaciju(this.radionica._id,korisnik).subscribe(r=>{
      arr.forEach(element=>{
        this.korSer.posaljiObavestenjeOSlobodnomMestu(element,this.radionica.naziv).subscribe(re=>{
        })
      })
      this.ngOnInit();
    })
  }

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
              this.previseSlika="Ne možete uneti više od 5 slika\n";
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
    if(!this.dug || this.dug==""){
      ok=false;
      this.poruka= this.poruka + "Niste uneli duzi opis\n";
    }
    if(!this.kratak || this.kratak==""){
      ok=false;
      this.poruka= this.poruka + "Niste uneli kratak opis\n";
    }
    return ok;
  }

  proveriPromenu(): boolean{
    if(this.naziv != this.radionica.naziv){
      return true;
    }
    if(this.mesto != this.radionica.mesto){
      return true;
    }
    if(this.dug != this.radionica.duzi_opis){
      return true;
    }
    if(this.kratak != this.radionica.kratak_opis){
      return true;
    }

    let dat = this.radionica.datum;
    let datSt;

    datSt = dat.getFullYear()+"-";
    if(dat.getMonth()<9) datSt = datSt + "0";
    datSt = datSt + (dat.getMonth()+1)+"-";
    if(dat.getDate()<10) datSt = datSt + "0";
    datSt = datSt + (dat.getDate());

    if(datSt != this.datum)
      return true;

    if(this.slika != this.radionica.glavna_slika)
      return true;

    let len = this.radionica.slike.length;
    if(len != this.sporedneSlike.length)
      return true;
    
    for(let i=0; i<this.sporedneSlike.length;i++){
      if(this.sporedneSlike[i] != this.radionica.slike[i])
        return true;
    }
    
    return false;
  }

  sacuvajJSON(){
    const data = JSON.stringify({
      naziv: this.radionica.naziv,
      datum: this.radionica.datum,
      mesto: this.radionica.mesto,
      kratak_opis: this.radionica.kratak_opis,
      duzi_opis: this.radionica.duzi_opis,
      glavna_slika: this.radionica.glavna_slika,
      slike: this.radionica.slike,
    });

    let ime = this.radionica.organizator + "-" + this.radionica.naziv + ".json";

    const blob = new Blob([data], { type: 'application/json' });
    saveAs(blob, ime);
  }

}
