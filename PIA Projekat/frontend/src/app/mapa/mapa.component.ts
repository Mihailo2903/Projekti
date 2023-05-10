import { Component, Input, OnInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements OnInit{

  @Input() placeName: string;
  @Input() radionica: string;

  ngOnInit(): void {
    var nominatimUrl = "https://nominatim.openstreetmap.org/search?format=json&limit=1&q=" + this.placeName;

    fetch(nominatimUrl)
    .then(response => response.json())
    .then(data => {
       var lat = data[0].lat;
       var lng = data[0].lon;

       var map = L.map('mapid').setView([lat, lng], 13); 
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        var marker = L.marker([lat, lng]).addTo(map);
        marker.bindPopup("<b>Hello world!</b><br>I am a popup.").openPopup();marker.bindPopup(this.radionica).openPopup();
     
    });
  }

}


