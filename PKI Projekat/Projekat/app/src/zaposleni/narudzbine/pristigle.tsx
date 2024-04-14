import { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { ProveriLogovanost, dohvatiNarudzbine } from "../../util"
import Navigation from "../../navigation/navigation"
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import "./pristigle.css"

function ubaciUObavestenja(korisnicko_ime){
    let pristigla = JSON.parse(localStorage.getItem("pristigla"))
    if(!pristigla)
        pristigla = []
    if(pristigla.length > 0){
        for(let p of pristigla){
            if(p.korisnicko_ime === korisnicko_ime){
                p.broj++
                localStorage.setItem("pristigla", JSON.stringify(pristigla))
                return
            }
        }
    }
    pristigla.push({
        "korisnicko_ime": korisnicko_ime,
        "broj": 1
    })
    localStorage.setItem("pristigla", JSON.stringify(pristigla))
}

export default function Pristigle(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "zaposleni")
    })

    let narudzbine = JSON.parse(localStorage.getItem("narudzbine"))
    if(!narudzbine){
        narudzbine = dohvatiNarudzbine()
        localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
    }

    narudzbine = narudzbine.filter((n) => n.status === "NA_CEKANJU")

    let prikaz = narudzbine.map((nar) => {
            return(
                <tr>
                    <td className="cell">{nar.broj}</td>
                    <td className="cell">{nar.cena} din</td>
                    <td className="cell">{nar.korisnicko_ime}</td>
                    <td className="cell"><button className="btn butDe" onClick={()=>{
                        let obavestenje = {
                            "broj": nar.broj,
                            "korisnicko_ime": nar.korisnicko_ime,
                        }

                        localStorage.setItem("obavestenje", JSON.stringify(obavestenje))
                        return navigator("/prikaz_obavestenje")
                    }}>Vidi detalje narudžbine</button></td>  
                    <td className="cell"><button className="btn butDe" onClick={()=>{
                         let narudzbineU = JSON.parse(localStorage.getItem("narudzbine"))
                         let narU = narudzbineU.find((n) => n.broj === nar.broj)
                         narU.status = "ODOBRENA"
                         localStorage.setItem("narudzbine", JSON.stringify(narudzbineU))
                         ubaciUObavestenja(nar.korisnicko_ime)
                         alertify.success("Uspešno ste odobrili narudžbinu")
                         return navigator("/pristigle")
                    }} >Odobri</button></td> 
                    <td className="cell"><button className="btn butOd" onClick={()=>{
                        let narudzbineU = JSON.parse(localStorage.getItem("narudzbine"))
                        let narU = narudzbineU.find((n) => n.broj === nar.broj)
                        narU.status = "ODBIJENA"
                        localStorage.setItem("narudzbine", JSON.stringify(narudzbineU))
                        ubaciUObavestenja(nar.korisnicko_ime)
                        alertify.success("Uspešno ste odbili narudžbinu")
                        return navigator("/pristigle")
                    }} >Odbij</button></td>    
                </tr>
            )
        }
    )

    return(
        <div className="pristigle">
            <Navigation tip="zaposleni"></Navigation>
            {
                narudzbine.length > 0 ? <div>
                    <h1>Ovde možete videti pristigle narudžbine!</h1>
                        <div className="container">
                            <table className="table tab">
                                <thead>
                                <tr>
                                    <th className="cell">Broj narudžbine</th>
                                    <th className="cell">Cena</th>
                                    <th className="cell">Kupac</th>
                                    <th className="cell">Detalji</th>
                                    <th className="cell">Odobri</th>
                                    <th className="cell">Odbij</th>
                                </tr>
                                </thead>
                                <tbody>
                                    {prikaz}
                                </tbody>
                            </table>
                        </div>
                    </div> : <h1>Trenutno nema pristiglih narudžbina!</h1>
            }
        </div>
    )
}


