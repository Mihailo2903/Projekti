import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Navigation from "../../navigation/navigation";
import { ProveriLogovanost, dohvatiProizvode } from "../../util";
import PrikazProizvod from "../proizvodi/prikaz_proizvod";
import "./kolaci.css";

export default function Kolaci(){
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

    let kolaci = proizvodi.filter((p) => p.tip === "kolac")

    return(
        <div>
        <Navigation tip="kupac"></Navigation>
        <h1>Izaberite neki od kolača iz našeg asortimana!</h1>
        <div className="kolaci">
            {3*brojStrane < kolaci.length ? (
            <div>
                <PrikazProizvod
                id={kolaci[3*brojStrane].id}
                naziv={kolaci[3*brojStrane].naziv}
                slika={kolaci[3*brojStrane].slika}
                />
            </div>
            ): null}
            {3*brojStrane + 1 < kolaci.length ? (
            <div>
                <PrikazProizvod
                id={kolaci[3*brojStrane + 1].id}
                naziv={kolaci[3*brojStrane + 1].naziv}
                slika={kolaci[3*brojStrane + 1].slika}
                />
            </div>
            ): null}
            {3*brojStrane + 2 < kolaci.length ? (
            <div>
                <PrikazProizvod
                id={kolaci[3*brojStrane + 2].id}
                naziv={kolaci[3*brojStrane + 2].naziv}
                slika={kolaci[3*brojStrane + 2].slika}
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
           brojStrane > 0 && 3*(brojStrane + 1) < kolaci.length ? (
                <span>&nbsp;</span>
            ) : null
        }
        {
            3*(brojStrane + 1) < kolaci.length ? (
                <span className="slide" onClick={()=>setBrojStrane(brojStrane + 1)}>{">"}</span>
            ) : null
        }
    </div>
    )
}