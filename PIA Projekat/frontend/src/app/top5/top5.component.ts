import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Aktivnost } from '../models/aktivnost';
import { Radionica } from '../models/radionica';
import { RadionicaServisService } from '../services/radionica-servis.service';

@Component({
  selector: 'app-top5',
  templateUrl: './top5.component.html',
  styleUrls: ['./top5.component.css']
})
export class Top5Component implements OnInit {
  constructor(private servis: RadionicaServisService, private router: Router){}

  ngOnInit(): void {
    this.servis.dohvatiTop5().subscribe((r:Radionica[])=>{
      this.radionice=r;
      this.radionice.forEach(rad=>{
        rad.datum = new Date(rad.datum);
      })
    })
  }


  radionice: Radionica[];
}
