import { useNavigate } from "react-router-dom";
import Navigation from "../../navigation/navigation";
import { useEffect } from "react";
import { ProveriLogovanost } from "../../util";


export default function Zaposleni(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "zaposleni")
    })

    let ulogovan = JSON.parse(localStorage.getItem("ulogovan"))

    return(
        <div className="zaposleni">
            <Navigation tip="zaposleni"></Navigation>
            <h1>Dobrodošli {ulogovan.ime} {ulogovan.prezime}!</h1>
        </div>
    )
}