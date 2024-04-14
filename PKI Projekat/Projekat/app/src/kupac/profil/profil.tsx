import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import './profil.css'

import Navigation from "../../navigation/navigation";


export default function Profil() {
    const navigator = useNavigate()
    let user = JSON.parse(localStorage.getItem("ulogovan"))

    useEffect(()=>{   
        if(!user) 
            navigator("/")
    }
    )

    const [korisnickoIme, setKorisnickoIme] = useState("")
    const [ime, setIme] = useState("")
    const [prezime, setPrezime] = useState("")
    const [telefon, setTelefon] = useState("")
    const [adresa, setAdresa] = useState("")

    return (
        <div className="prof">
            <Navigation tip={user.tip}></Navigation>
            <h1>Informacije o profilu</h1>
            <div className="login-form1">
                <div className="form-group1">
                    <label htmlFor="korime">Korisničko ime</label>
                    <input className="inputProfil" type="text" name="korime" id="korime" placeholder={user.korisnicko_ime} onChange={(e)=>setKorisnickoIme(e.target.value)}/>
                </div>
                <div className="form-group1">
                    <label htmlFor="ime">Ime</label>
                    <input className="inputProfil" type="text" name="ime" id="ime" placeholder={user.ime} onChange={(e)=>setIme(e.target.value)} />
                </div>
                <div className="form-group1">
                    <label htmlFor="prezime">Prezime</label>
                    <input className="inputProfil" type="text" name="prezime" id="prezime" placeholder={user.prezime} onChange={(e)=>setPrezime(e.target.value)} />
                </div>
                <div className="form-group1">
                    <label htmlFor="telefon">Kontakt telefon</label>
                    <input className="inputProfil" type="text" name="telefon" id="telefon" placeholder={user.telefon} onChange={(e)=>setTelefon(e.target.value)} />
                </div>
                <div className="form-group1">
                    <label htmlFor="adresa">Adresa</label>
                    <input className="inputProfil" type="text" name="adresa" id="adresa" placeholder={user.adresa} onChange={(e)=>setAdresa(e.target.value)} />
                </div>
                <div className="dugmad">
                    <button type="submit" className="btn but" onClick={()=>{
                        if(korisnickoIme === "" && ime === "" && prezime === "" && telefon === "" && adresa === ""){
                            alertify.error("Niste uneli nijedno polje")
                            return
                        }
                        if(korisnickoIme !== "")
                             user.korisnicko_ime = korisnickoIme
                        if(ime !== "")
                            user.ime = ime
                        if(prezime !== "")
                            user.prezime = prezime
                        if(telefon !== "")
                            user.telefon = telefon
                        if(adresa !== "")
                            user.adresa = adresa
                        localStorage.setItem("ulogovan", JSON.stringify(user))
                        alertify.success("Uspešno ste izmenili podatke")
                        
                        if(user.tip === "kupac")
                           return navigator("/kupac")
                        return navigator("/zaposleni")
                    }}>Izmeni podatke</button>
                    <button type="submit" className="btn butLO" onClick={()=>{
                        localStorage.removeItem("ulogovan")
                        alertify.success("Uspešno ste se odjavili")
                        return navigator("/")
                    }}>Odjavi se</button>
                </div>
            </div>
            
        </div>
    );
}
