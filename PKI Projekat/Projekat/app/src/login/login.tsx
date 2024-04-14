import { useState } from "react";
import { useNavigate } from "react-router-dom";
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import { dohvatiKorisnike } from "../util";
import "./login.css";


export default function Login() {
    let users = JSON.parse(localStorage.getItem("korisnici"))
    if(!users){
        users = dohvatiKorisnike()
        localStorage.setItem("korisnici", JSON.stringify(users))
    }

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("")

    const navigator = useNavigate()

    function login(){
        let found = false;
        for(let i=0; i<users.length; i++){
            if(users[i].korisnicko_ime===username && users[i].lozinka===password){
                found=true;
                alertify.success("Uspešno ste se prijavili")
                localStorage.setItem("ulogovan", JSON.stringify(users[i]))
                if(users[i].tip==="kupac")
                    navigator("/kupac")
                else
                    navigator("/zaposleni")
            }
        }
        if(!found) 
            alertify.error("Uneli ste nepostojeće kredencijale")
    }

    return (
        <div className="login">
        <h1>Dobrodošli! Prijavite se ovde</h1>
        <div className="login-form">
            <div className="form-group">
                <label htmlFor="username">Korisničko ime</label>
                <input type="text" name="username" id="username"  onChange={(e)=>setUsername(e.target.value)} />
            </div>
            <div className="form-group">
                <label htmlFor="password">Lozinka</label>
                <input type="password" name="password" id="password"  onChange={(e)=>setPassword(e.target.value)} />
            </div>
            <button type="submit" className="btn but" onClick={login}>Prijavi se</button>
            
        </div>
        </div>
    );
}
