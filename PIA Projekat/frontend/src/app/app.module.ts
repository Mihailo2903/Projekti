import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UcesnikComponent } from './ucesnik/ucesnik.component';
import { OrganizatorComponent } from './organizator/organizator.component';
import { PonistavanjeLozinkeComponent } from './ponistavanje-lozinke/ponistavanje-lozinke.component'
import { PromenaLozinkeComponent } from './promena-lozinke/promena-lozinke.component';
import { MeniComponent } from './meni/meni.component';
import { FooterComponent } from './footer/footer.component';
import { PregledRadionicaComponent } from './pregled-radionica/pregled-radionica.component';
import { Top5Component } from './top5/top5.component';
import { ProfilComponent } from './profil/profil.component';
import { IstorijaRadionicaComponent } from './istorija-radionica/istorija-radionica.component';
import { AktivnostiComponent } from './aktivnosti/aktivnosti.component';
import { PorukeUcesnikComponent } from './poruke-ucesnik/poruke-ucesnik.component';
import { PrijavljeneRadioniceComponent } from './prijavljene-radionice/prijavljene-radionice.component';
import { RadionicaDetaljiComponent } from './radionica-detalji/radionica-detalji.component';
import { SlideShowComponent } from './slide-show/slide-show.component';
import { MapaComponent } from './mapa/mapa.component';
import { AktivnostiRadioniceComponent } from './aktivnosti-radionice/aktivnosti-radionice.component';
import { PostaniOrganizatorComponent } from './postani-organizator/postani-organizator.component';
import { SveRadioniceComponent } from './sve-radionice/sve-radionice.component';
import { UredjivanjeRadioniceComponent } from './uredjivanje-radionice/uredjivanje-radionice.component';
import { DodajRadionicuComponent } from './dodaj-radionicu/dodaj-radionicu.component';
import { AdminKorisniciComponent } from './admin-korisnici/admin-korisnici.component';
import { AdminComponent } from './admin/admin.component';
import { AdminKorAzuriranjeComponent } from './admin-KAZ/admin-kor-azuriranje.component';
import { AdminRadioniceComponent } from './admin-radionice/admin-radionice.component';
import { PorukeOrganizatorComponent } from './poruke-organizator/poruke-organizator.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UcesnikComponent,
    OrganizatorComponent,
    PromenaLozinkeComponent,
    PonistavanjeLozinkeComponent,
    MeniComponent,
    FooterComponent,
    PregledRadionicaComponent,
    Top5Component,
    ProfilComponent,
    IstorijaRadionicaComponent,
    AktivnostiComponent,
    PorukeUcesnikComponent,
    PrijavljeneRadioniceComponent,
    RadionicaDetaljiComponent,
    SlideShowComponent,
    MapaComponent,
    AktivnostiRadioniceComponent,
    PostaniOrganizatorComponent,
    SveRadioniceComponent,
    UredjivanjeRadioniceComponent,
    DodajRadionicuComponent,
    AdminKorisniciComponent,
    AdminComponent,
    AdminKorAzuriranjeComponent,
    AdminRadioniceComponent,
    PorukeOrganizatorComponent,
    LoginAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
