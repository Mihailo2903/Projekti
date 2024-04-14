import { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { ProveriLogovanost, dohvatiNarudzbine } from "../../util"
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import "./korpa.css"
import Navigation from "../../navigation/navigation"

export default function Korpa(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })

    let narudzbine = JSON.parse(localStorage.getItem("narudzbine"))
    if(!narudzbine){
        narudzbine = dohvatiNarudzbine()
        localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
    }
     
    let korpa = narudzbine.find((n) => n.status === "KORPA" && n.korisnicko_ime === JSON.parse(localStorage.getItem("ulogovan")).korisnicko_ime)

    if(!korpa){
       return(
            <div>
                <Navigation tip="kupac"></Navigation>
                <h1>Korpa je trenutno prazna</h1>
           </div>
       )
    }

    let prikazKorpe = korpa.detalji.map((detalj) => {
        return(
            <tr>
                <td className="cell">{detalj.naziv}</td>
                <td className="cell">{detalj.cena} din</td>
                <td className="cell">{detalj.kolicina}</td>
                <td className="cell">{detalj.cena * detalj.kolicina} din</td>

                <td className="cell"><button className="btn butD" onClick={()=>{
                     localStorage.setItem("proizvodId", detalj.proizvodId)
                     return navigator("/proizvod")
                }} >Naruči još</button></td>

                <td className="cell"><button className="btn butU" onClick={()=>{
                    let d = korpa.detalji.find((det) => det.proizvodId === detalj.proizvodId)
                    d.kolicina--
                    korpa.cena -= d.cena
                    if(d.kolicina === 0){
                        korpa.detalji = korpa.detalji.filter((det) => det.proizvodId !== detalj.proizvodId)
                    }
                    if(korpa.cena === 0){
                        narudzbine = narudzbine.filter((n) => n.broj !== korpa.broj)
                    }
                    localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
                    alertify.success(`Uspešno ste izbacili proizvod ${d.naziv} iz korpe`)
                    return navigator("/korpa")
                }} >Izbaci 1 iz korpe</button></td>

                <td className="cell"><button className="btn butU" onClick={()=>{
                    let d = korpa.detalji.find((det) => det.proizvodId === detalj.proizvodId)
                    korpa.cena -= d.cena * d.kolicina
                    korpa.detalji = korpa.detalji.filter((det) => det.proizvodId !== d.proizvodId)
               
                    if(korpa.cena === 0){
                        narudzbine = narudzbine.filter((n) => n.broj !== korpa.broj)
                    }
                    localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
                    alertify.success(`Uspešno ste izbacili sve proizvode ${d.naziv} iz korpe`)
                    return navigator("/korpa")
                }} >Izbaci sve iz korpe</button></td>
            </tr>
        )
    }
    )

    return(
        <div>
            <Navigation tip="kupac"></Navigation>
            <h1>Trenutno naručeni proizvodi u korpi</h1>
            <div className="container">
                <table className="table tab">
                    <thead>
                    <tr>
                        <th className="cell">Proizvod</th>
                        <th className="cell">Cena</th>
                        <th className="cell">Količina</th>
                        <th className="cell">Ukupna cena</th>
                        <th className="cell"></th>
                        <th className="cell"></th>
                        <th className="cell"></th>
                    </tr>
                    </thead>
                    <tbody>
                        {prikazKorpe}
                    </tbody>
                </table>
            </div>
            <h2>Ukupna cena: {korpa.cena} din</h2> 
            <div className="c">   
                 <button className="btn butP" onClick={()=>{
                    korpa.status = "NA_CEKANJU"
                    localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
                    alertify.success("Uspešno ste potvrdili narudžbinu")
                    return navigator("/korpa")
                 }} >Potvrdi narudžbinu</button>
            </div>
        </div>
    )
}