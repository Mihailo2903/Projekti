import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';

import Navigation from "../../navigation/navigation";


export default function PromenaLozinke() {
    const navigator = useNavigate()

    useEffect(()=>{
        let user = JSON.parse(localStorage.getItem("ulogovan"))
        if(!user) 
            navigator("/")
    }
    )

    let ulogovan = JSON.parse(localStorage.getItem("ulogovan"))

    const [password, setPassword] = useState("")
    const [newPassword, setNewPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")

    return (
        <div>
            <Navigation tip={ulogovan.tip}></Navigation>
            <h1>Promenite lozinku</h1>
            <div className="login-form">
                <div className="form-group">
                    <label htmlFor="password">Lozinka</label>
                    <input type="password" name="password" id="password"  onChange={(e)=>setPassword(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="newPassword">Nova lozinka</label>
                    <input type="password" name="newPassword" id="newPassword"  onChange={(e)=>setNewPassword(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="confirmPassword">Potvrdi lozinku</label>
                    <input type="password" name="confirmPassword" id="confirmPassword"  onChange={(e)=>setConfirmPassword(e.target.value)} />
                </div>
                <button type="submit" className="btn but" onClick={()=>{
                    if(password === "" || newPassword === "" || confirmPassword === ""){
                        alertify.error("Sva polja moraju biti popunjena")
                        return
                    }
                    if(newPassword !== confirmPassword){
                        alertify.error("Niste dobro potvrdili lozinku")
                        return
                    }
                    let user = JSON.parse(localStorage.getItem("ulogovan"))
                    if(user.lozinka !== password){
                        alertify.error("Pogrešna lozinka")
                        return
                    }
                    user.lozinka = newPassword
                    localStorage.setItem("ulogovan", JSON.stringify(user))
                    alertify.success("Uspešno ste promenili lozinku")

                    if(user.tip === "kupac")
                         return navigator("/kupac")
                    return navigator("/zaposleni")
                }}>Promeni lozinku</button>
                
            </div>
        </div>
    );
}
