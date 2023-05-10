import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminKorAzuriranjeComponent } from './admin-KAZ/admin-kor-azuriranje.component';
import { AdminKorisniciComponent } from './admin-korisnici/admin-korisnici.component';
import { AdminRadioniceComponent } from './admin-radionice/admin-radionice.component';
import { AdminComponent } from './admin/admin.component';
import { AktivnostiRadioniceComponent } from './aktivnosti-radionice/aktivnosti-radionice.component';
import { AktivnostiComponent } from './aktivnosti/aktivnosti.component';
import { DodajRadionicuComponent } from './dodaj-radionicu/dodaj-radionicu.component';
import { IstorijaRadionicaComponent } from './istorija-radionica/istorija-radionica.component';
import { LoginAdminComponent } from './login-admin/login-admin.component';
import { LoginComponent } from './login/login.component';
import { OrganizatorComponent } from './organizator/organizator.component';
import { PonistavanjeLozinkeComponent } from './ponistavanje-lozinke/ponistavanje-lozinke.component';
import { PorukeOrganizatorComponent } from './poruke-organizator/poruke-organizator.component';
import { PorukeUcesnikComponent } from './poruke-ucesnik/poruke-ucesnik.component';
import { PostaniOrganizatorComponent } from './postani-organizator/postani-organizator.component';
import { PregledRadionicaComponent } from './pregled-radionica/pregled-radionica.component';
import { PrijavljeneRadioniceComponent } from './prijavljene-radionice/prijavljene-radionice.component';
import { ProfilComponent } from './profil/profil.component';
import { PromenaLozinkeComponent } from './promena-lozinke/promena-lozinke.component';
import { RadionicaDetaljiComponent } from './radionica-detalji/radionica-detalji.component';
import { RegisterComponent } from './register/register.component';
import { SveRadioniceComponent } from './sve-radionice/sve-radionice.component';
import { Top5Component } from './top5/top5.component';
import { UcesnikComponent } from './ucesnik/ucesnik.component';
import { UredjivanjeRadioniceComponent } from './uredjivanje-radionice/uredjivanje-radionice.component';

const routes: Routes = [
  {path:"", component:LoginComponent},
  {path:"register" , component: RegisterComponent},
  {path:"ucesnik" , component: UcesnikComponent},
  {path:"organizator" , component: OrganizatorComponent},
  {path:"ponistavanjeLozinke" , component: PonistavanjeLozinkeComponent},
  {path:"promenaLozinke" , component: PromenaLozinkeComponent},
  {path:"pregledRadionica" , component: PregledRadionicaComponent},
  {path:"top5Radionica" , component: Top5Component},
  {path:"profil" , component: ProfilComponent},
  {path:"istorijaRadionica" , component: IstorijaRadionicaComponent},
  {path:"aktivnosti" , component: AktivnostiComponent},
  {path:"porukeUcesnik" , component: PorukeUcesnikComponent},
  {path:"prijavljeneRadionice" , component: PrijavljeneRadioniceComponent},
  {path:"radionicaDetalji/:id", component: RadionicaDetaljiComponent },
  {path:"radionicaAktivnosti/:id", component: AktivnostiRadioniceComponent },
  {path:"postaniOrganizator", component: PostaniOrganizatorComponent },
  {path:"sveRadionice", component: SveRadioniceComponent },
  {path:"uredjivanjeRadionice/:id", component: UredjivanjeRadioniceComponent },
  {path:"dodajRadionicu", component: DodajRadionicuComponent },
  {path:"porukeOrganizator/:id", component: PorukeOrganizatorComponent },
  {path:"admin", component: AdminComponent },
  {path:"adminKorisnici", component: AdminKorisniciComponent },
  {path:"azurirajKorisnika/:id", component: AdminKorAzuriranjeComponent },
  {path:"adminRadionice", component: AdminRadioniceComponent },
  {path:"loginAdmin", component: LoginAdminComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
