import { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import Navigation from "../../navigation/navigation"
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import "./obavestenja.css"

export default function PrikazObavestenje() {
    const navigator = useNavigate()

    let ulogovan = JSON.parse(localStorage.getItem("ulogovan"))

    useEffect(()=>{   
        if(!ulogovan) 
            navigator("/")
    }
    )

    let obavestenje = JSON.parse(localStorage.getItem("obavestenje"))
    if(ulogovan.tip === "kupac"){
        if(!obavestenje || obavestenje.korisnicko_ime !== ulogovan.korisnicko_ime){
            navigator("/kupac")
        }
    } else {
        if(!obavestenje){
            navigator("/zaposleni")
        }
    }

    let narudzbina = JSON.parse(localStorage.getItem("narudzbine")).find((n) => n.broj === obavestenje.broj)

    let prikazNar = narudzbina.detalji.map((detalj) => {
        return(
            <tr>
                <td className="cell">{detalj.naziv}</td>
                <td className="cell">{detalj.cena} din</td>
                <td className="cell">{detalj.kolicina}</td>
                <td className="cell">{detalj.cena * detalj.kolicina} din</td>
            </tr>
        )
    }
    )
    return (
        <div>
            <Navigation tip={ulogovan.tip}></Navigation>
            {
                ulogovan.tip === "kupac" ? <h1>Ovde možete videti status narudžbine!</h1> : <h1>Ovde možete videti detalje narudžbine!</h1>
            }
            <div className="container">
                <table className="table tab">
                    <thead>
                    <tr>
                        <th className="cell">Proizvod</th>
                        <th className="cell">Cena</th>
                        <th className="cell">Količina</th>
                        <th className="cell">Ukupna cena</th>
                    </tr>
                    </thead>
                    <tbody>
                        {prikazNar}
                    </tbody>
                </table>
            </div>
            <h2>Ukupna cena: {narudzbina.cena} din</h2> 
            <br/> <br/>
            {
                ulogovan.tip === "kupac" ? <h2>Status: {narudzbina.status}</h2>  : 
                <div>
                    <button type="submit" className="btn butOdo" onClick={()=>{
                        let narudzbine = JSON.parse(localStorage.getItem("narudzbine"))
                        let nar = narudzbine.find((n) => n.broj === obavestenje.broj)
                        nar.status = "ODOBRENA"
                        localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
                        alertify.success("Uspešno ste odobrili narudžbinu")
                        return navigator("/pristigle")
                    }}>Odobri</button>
                    <button type="submit" className="btn butOdb" onClick={()=>{
                        let narudzbine = JSON.parse(localStorage.getItem("narudzbine"))
                        let nar = narudzbine.find((n) => n.broj === obavestenje.broj)
                        nar.status = "ODBIJENA"
                        localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
                        alertify.success("Uspešno ste odbili narudžbinu")
                        return navigator("/pristigle")
                    }}>Odbij</button>
                </div>
            }
            
        </div>
    )
}