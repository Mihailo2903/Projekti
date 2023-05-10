import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KorisnikServisService } from '../services/korisnik-servis.service';

@Component({
  selector: 'app-ponistavanje-lozinke',
  templateUrl: './ponistavanje-lozinke.component.html',
  styleUrls: ['./ponistavanje-lozinke.component.css']
})
export class PonistavanjeLozinkeComponent implements OnInit {
  constructor(private servis: KorisnikServisService, private router: Router){}

  ngOnInit(): void {
    
  }

  mejl:String;
  poruka:String;
  porukaUspeh:string;

  posaljiMejl(){
    let nova = this.generisiNovuLozinku();
    if(!this.mejl){
      this.poruka="Niste uneli mejl";
      return;
    }
    this.servis.posaljiMejl(this.mejl,nova).subscribe(resp=>{
      this.porukaUspeh="Mejl je uspesno poslat";
      this.mejl="";
    })
  }

  generisiNovuLozinku(): string{
    let nova = "";
    nova = nova + String.fromCharCode(Math.floor(Math.random() * 26) + 65);
    for(let i=0; i<5; i++)
      nova = nova + String.fromCharCode(Math.floor(Math.random() * 26) + 97);
    nova = nova + "!";
    for(let i=0; i<5; i++)
      nova = nova + "" + (Math.floor(Math.random() * 9) + 1);

    return nova;
  }

}
