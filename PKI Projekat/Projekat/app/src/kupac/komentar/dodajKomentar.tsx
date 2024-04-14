import { useNavigate } from "react-router-dom"
import Navigation from "../../navigation/navigation"
import { useEffect, useState } from "react"
import { ProveriLogovanost } from "../../util"
import "./dodajKomentar.css"
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';

export default function DodajKomentar() {
    
    const navigator = useNavigate()

    const [sadrzaj, setSadrzaj] = useState("")

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })


    let proizvod  = JSON.parse(localStorage.getItem("proizvodKomentar"))
    if(!proizvod){
        navigator("/kupac")
    } 

    let komentari = JSON.parse(localStorage.getItem("komentari"))
    if(!komentari){
       komentari = []
    }

   let ulogovan = JSON.parse(localStorage.getItem("ulogovan")).korisnicko_ime

   function dodajKomentar(){
        if(!sadrzaj || sadrzaj.length === 0){
            alertify.error("Niste uneli komentar")
            return
        }

        let komentar = {
            "korisnicko_ime": ulogovan,
            "sadrzaj": sadrzaj,
            "proizvodId": proizvod.id,
        }

        komentari.push(komentar)
        localStorage.setItem("komentari", JSON.stringify(komentari))
        alertify.success("Uspešno ste objavili komentar")
        navigator("/proizvod")
   }

   return(
        <div>
            <Navigation tip="kupac"></Navigation>
            <h1>Ostavite komentar na proizvod {proizvod.naziv}!</h1>
            <div className="content">
                    <h1>Komentar:</h1>
            </div>
            <div className="content">
                    <textarea placeholder="Ovde ostavite vaš komentar" className="aaa" onChange={(e)=>setSadrzaj(e.target.value)}></textarea>
            </div>
            <button type="submit" className="btn butDod" onClick={()=> dodajKomentar()}>Objavi komentar</button>
        </div>
   )
}