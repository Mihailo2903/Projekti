import { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { ProveriLogovanost, dohvatiNarudzbine } from "../../util"
import "./obavestenja.css"
import Navigation from "../../navigation/navigation"

export default function Korpa(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })

    let ulogovan = JSON.parse(localStorage.getItem("ulogovan"))
    let pristigla = JSON.parse(localStorage.getItem("pristigla"))
    if(pristigla){
        for(let p of pristigla){
            if(p.korisnicko_ime === ulogovan.korisnicko_ime){
                p.broj = 0
                break
            }
        }
        pristigla = pristigla.filter((p) => p.broj !== 0)
        localStorage.setItem("pristigla", JSON.stringify(pristigla))
    }

    let narudzbine = JSON.parse(localStorage.getItem("narudzbine"))
    if(!narudzbine){
        narudzbine = dohvatiNarudzbine()
        localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
     }
     
    narudzbine = narudzbine.filter((n) => n.korisnicko_ime === JSON.parse(localStorage.getItem("ulogovan")).korisnicko_ime && n.status !== "KORPA")

    let prikazNar = narudzbine.map((nar) => {
        return(
            <tr>
                <td className="cell">{nar.broj}</td>
                <td className="cell">{nar.cena} din</td>
                <td className="cell">{nar.status}</td>
                <td className="cell"><button className="btn butD" onClick={()=>{
                    let obavestenje = {
                        "broj": nar.broj,
                        "korisnicko_ime": nar.korisnicko_ime,
                    }

                    localStorage.setItem("obavestenje", JSON.stringify(obavestenje))
                    return navigator("/prikaz_obavestenje")
                }}>Vidi detalje narudžbine</button></td>     
            </tr>
        )
    }
    )

    return(
        <div>
            <Navigation tip="kupac"></Navigation>
            {
                narudzbine.length > 0 ? <div>
                    <h1>Ovde možete videti status vaših narudžbina!</h1>
                        <div className="container">
                            <table className="table tab">
                                <thead>
                                <tr>
                                    <th className="cell">Broj narudžbine</th>
                                    <th className="cell">Cena</th>
                                    <th className="cell">Status</th>
                                    <th className="cell">Detalji</th>
                                </tr>
                                </thead>
                                <tbody>
                                    {prikazNar}
                                </tbody>
                            </table>
                        </div>
                </div> : <h1>Trenutno nemate narudžbina!</h1>
            }
        </div>
    )
}