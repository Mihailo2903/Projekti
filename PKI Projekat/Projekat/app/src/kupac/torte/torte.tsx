import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navigation from "../../navigation/navigation";
import { ProveriLogovanost, dohvatiProizvode } from "../../util";
import PrikazProizvod from "../proizvodi/prikaz_proizvod";
import "./torte.css";

export default function Torte(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })

    const [brojStrane, setBrojStrane] = useState(0);

    let proizvodi = JSON.parse(localStorage.getItem("proizvodi"))
    if(!proizvodi){
        proizvodi = dohvatiProizvode()
        localStorage.setItem("proizvodi", JSON.stringify(proizvodi))
    }

    let torte = proizvodi.filter((p) => p.tip === "torta")

    return(
        <div>
        <Navigation tip="kupac"></Navigation>
        <h1>Izaberite neku od torti iz našeg asortimana!</h1>
        <div className="torte">
            {3*brojStrane < torte.length ? (
            <div>
                <PrikazProizvod
                id={torte[3*brojStrane].id}
                naziv={torte[3*brojStrane].naziv}
                slika={torte[3*brojStrane].slika}
                />
            </div>
            ): null}
            {3*brojStrane + 1 < torte.length ? (
            <div>
                <PrikazProizvod
                id={torte[3*brojStrane + 1].id}
                naziv={torte[3*brojStrane + 1].naziv}
                slika={torte[3*brojStrane + 1].slika}
                />
            </div>
            ): null}
            {3*brojStrane + 2 < torte.length ? (
            <div>
                <PrikazProizvod
                id={torte[3*brojStrane + 2].id}
                naziv={torte[3*brojStrane + 2].naziv}
                slika={torte[3*brojStrane + 2].slika}
                />
            </div>
            ): null}
        </div> 
        {
            brojStrane > 0 ? (
                <span className="slide" onClick={()=>setBrojStrane(brojStrane - 1)}>{"<"}</span>
            ) : null
        }
        {
           brojStrane > 0 && 3*(brojStrane + 1) < torte.length ? (
                <span>&nbsp;</span>
            ) : null
        }
        {
            3*(brojStrane + 1) < torte.length ? (
                <span className="slide" onClick={()=>setBrojStrane(brojStrane + 1)}>{">"}</span>
            ) : null
        }
    </div>
    )
}