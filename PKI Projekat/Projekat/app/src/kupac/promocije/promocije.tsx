import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navigation from "../../navigation/navigation";
import { ProveriLogovanost } from "../../util";
import "./promocije.css";

export default function Promocije() {
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })

    return (
        <div>
            <Navigation tip="kupac"></Navigation>
            <h1>Trenuto aktivne promocije u poslastičarnici!</h1>
            <div  className="promocije">
                <div className="promocija">
                    <h3>5 + 1 krempita</h3>
                    <img src="./images/6_krempita.jpg" alt="" />
                    <h4>Uz kupljenih 5 krempita, dobijate šestu gratis!</h4>
                </div>
                <div className="promocija">
                    <h3>2 tiramisua + kafa</h3>
                    <img src="./images/tiramisu_kafa.jpg" alt="" />
                    <h4>Uz kupljena 2 tiramisua, dobijate kafu gratis!</h4>
                </div>
                <div className="promocija">
                    <h3>2 + 1 ledena kocka</h3>
                    <img src="./images/ledene_kocke.jpg" alt="" />
                    <h4>Uz kupljene 2 ledene kocke, dobijate treću gratis!</h4>
                </div>
            </div>
        </div>
    )
}