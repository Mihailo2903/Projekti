import { useNavigate } from "react-router-dom";
import  "./navigation.css";

export default function Navigation({tip}) {

    let navigator = useNavigate()

    if(tip === "kupac") {
        let ulogovan = JSON.parse(localStorage.getItem("ulogovan"))
        let broj = 0
        let pristigla = JSON.parse(localStorage.getItem("pristigla"))
        if(pristigla){
            for(let p of pristigla){
                if(p.korisnicko_ime === ulogovan.korisnicko_ime)
                    broj = p.broj
            }
        }

        return (
            <div className="navigation">
                <span className="menuItem" onClick={() => navigator("/kupac")}>Početna</span>
                <span className="menuItem" onClick={() => navigator("/promocije")}>Promocije</span>
                <span className="menuItem" onClick={() => navigator("/torte")}>Torte</span>
                <span className="menuItem" onClick={() => navigator("/kolaci")}>Kolači</span>
                <span className="menuItem" onClick={() => navigator("/korpa")}>Korpa</span>
                <span className="menuItem" onClick={() => navigator("/obavestenja")}>Obaveštenja 
                   {
                     broj > 0 ?  <span className="badge bg-danger bad">{broj}</span> : null
                   }
                </span>
                <span className="menuItem" onClick={() => navigator("/promena_lozinke")}>Promena lozinke</span>
                <span className="menuItem" onClick={() => navigator("/profil")}>Profil</span>
               
            </div>
        )
    }
    else
        return (
            <div className="navigation">
                <span className="menuItem" onClick={() => navigator("/zaposleni")}>Početna</span>
                <span className="menuItem" onClick={() => navigator("/pristigle")}>Pristigle narudžbine</span>
                <span className="menuItem" onClick={() => navigator("/dodaj_proizvod")}>Dodaj proizvod</span>
                <span className="menuItem" onClick={() => navigator("/promena_lozinke")}>Promena lozinke</span>
                <span className="menuItem" onClick={() => navigator("/profil")}>Profil</span>
            </div>
        )
}