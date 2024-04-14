import {
  createBrowserRouter, RouterProvider
} from 'react-router-dom'

import './App.css'
import Login from './login/login'
import Kupac from './kupac/pocetna/kupac'
import Promocije from './kupac/promocije/promocije'
import Torte from './kupac/torte/torte'
import Kolaci from './kupac/kolaci/kolaci'
import Proizvod from './kupac/proizvodi/proizvod'
import DodajKomentar from './kupac/komentar/dodajKomentar'
import Korpa from './kupac/korpa/korpa'
import Obavestenja from './kupac/obavestenja/obavestenja'
import PrikazObavestenje from './kupac/obavestenja/prikaz_obavestenje'
import PromenaLozinke from './kupac/profil/promena_lozinke'
import Profil from './kupac/profil/profil'
import Zaposleni from './zaposleni/pocetna/zaposleni'
import Pristigle from './zaposleni/narudzbine/pristigle'
import DodajProizvod from './zaposleni/proizvod/dodaj_proizvod'

const router = createBrowserRouter(
  [
    {path: "/", element: <Login/>},
    {path: "/kupac", element: <Kupac/>},
    {path: "/promocije", element: <Promocije/>},
    {path: "/torte", element: <Torte/>},
    {path: "/kolaci", element: <Kolaci/>},
    {path: "/proizvod", element: <Proizvod/>},
    {path: "/dodajKomentar", element: <DodajKomentar/>},
    {path: "/korpa", element: <Korpa/>},
    {path: "/obavestenja", element: <Obavestenja/>},
    {path: "/prikaz_obavestenje", element: <PrikazObavestenje/>},
    {path: "/promena_lozinke", element: <PromenaLozinke/>},
    {path: "/profil", element: <Profil/>},
    {path: "/zaposleni", element: <Zaposleni/>},
    {path: "/pristigle", element: <Pristigle/>},
    {path: "/dodaj_proizvod", element: <DodajProizvod/>},
  ]
)

export default function App(){
  
  return(
    <div className="App">
      <RouterProvider router={router}></RouterProvider>
    </div>
  )
}