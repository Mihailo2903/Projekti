import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Korisnik } from '../models/korisnik';
import { Radionica } from '../models/radionica';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-sve-radionice',
  templateUrl: './sve-radionice.component.html',
  styleUrls: ['./sve-radionice.component.css']
})
export class SveRadioniceComponent implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan || this.ulogovan.tip!="organizator")
      this.router.navigate([""]);

    this.servis.dohvatiRadionice().subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
        if(rad.datum.getTime()<new Date().getTime())
          rad.zavrsena = true;
        else
          rad.zavrsena = false;
      })
    })
  }


  ulogovan: Korisnik;

  radionice: Radionica[];

  uredi(r:Radionica){
    this.router.navigate(["uredjivanjeRadionice", r._id]);
  }

  naPoruke(r:Radionica){
    this.router.navigate(["porukeOrganizator",r._id]);
  }
}
