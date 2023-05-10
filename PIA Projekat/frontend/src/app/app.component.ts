import { Component, OnInit } from '@angular/core';
import { Korisnik } from './models/korisnik';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(){}

  ngOnInit(): void {
    this.ulogovan = JSON.parse(localStorage.getItem("ulogovan"));
    if(!this.ulogovan)
      this.ulogovan=null;
  }
  
  title = 'app';
  ulogovan: Korisnik;
}
