import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { ProveriLogovanost, dohvatiProizvode, dohvatiSlike } from "../../util"
import Navigation from "../../navigation/navigation"
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import "./dodaj_proizvod.css"

export default function DodajProizvod() {
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "zaposleni")
    })

    let images = dohvatiSlike()

    let showImageNames = images.map((imageFile, index) => {
        return (
            <option key={index} value={imageFile.putanja}>{imageFile.naziv}</option>
        )
    })

    let proizvodi = JSON.parse(localStorage.getItem("proizvodi"))
    if(!proizvodi){
        proizvodi = dohvatiProizvode()
        localStorage.setItem("proizvodi", JSON.stringify(proizvodi))
    }

    const [naziv, setNaziv] = useState("")
    const [cena, setCena] = useState(0)
    const [opis, setOpis] = useState("")
    const [sastav, setSastav] = useState("")
    const [vrsta, setVrsta] = useState("")
    const [slika, setSlika] = useState("")

    return (
        <div>
            <Navigation tip="zaposleni"></Navigation>
            <h1>Ovde možete dodati novi proizvod!</h1>
            <div className="login-form2">
                <div className="form-group2">
                    <label htmlFor="naziv">Naziv</label>
                    <input className="inputProfil1" type="text" name="naziv" id="naziv" placeholder="Naziv" onChange = {(e)=>setNaziv(e.target.value)}/>
                    <label htmlFor="cena">Cena</label>
                    <input className="inputProfil1" type="number" name="cena" id="cena" placeholder="Cena" onChange = {(e)=>setCena(parseInt(e.target.value))}/>
                </div>
                <div className="form-group3">
                    <label htmlFor="opis">Opis</label>
                    <textarea placeholder="Unesite opis proizvoda" name="opis" id="opis" className="opis" onChange = {(e)=>setOpis(e.target.value)}></textarea>
                    <label htmlFor="sastav" className="lab">Sastav </label>
                    <textarea placeholder="Unesite sastav proizvoda" name="sastav" id="sastav" className="sastav" onChange = {(e)=>setSastav(e.target.value)}></textarea>
                </div>
            </div>
            <div className="down">
                <div className="rb">
                    <span className="la" >
                        Izaberite tip proizvoda:
                    </span>
                    <span className="la" >
                        <input type="radio" name="options" id="torta" onClick={(e) =>
                            setVrsta("torta")
                        }/>
                        <label htmlFor="torta" className="labela" onClick={(e) =>
                            setVrsta("kolac")
                        }>Torta</label>
                    </span>
                    <span className="la">
                        <input type="radio" name="options" id="kolac"/>
                        <label htmlFor="kolac" className="labela">Kolač</label>
                    </span>
                </div>
            </div>
            <div className="slike">
                Odaberite sliku
                <select className="sel"  onChange = {(e)=>setSlika(e.target.value)}>
                    <option value=""></option>
                    {showImageNames}
                </select>
            </div>
            <div>
                { slika !== "" ? <img src={slika}/> : null}
            </div>
            <div>
                <button type="submit" className="btn butDp" onClick={()=>{
                    if(naziv === ""){
                        alertify.error("Niste uneli naziv proizvoda")
                        return
                    }
                    if(cena === 0){
                        alertify.error("Niste uneli cenu proizvoda")
                        return
                    }
                    if(opis === ""){
                        alertify.error("Niste uneli opis proizvoda")
                        return
                    }
                    if(sastav === ""){
                        alertify.error("Niste uneli sastav proizvoda")
                        return
                    }
                    if(vrsta === ""){
                        alertify.error("Niste izabrali tip proizvoda")
                        return
                    }
                    if(slika === ""){
                        alertify.error("Niste izabrali sliku proizvoda")
                        return
                    }
                    
                    let proizvod = {
                        "id": proizvodi.length + 1,
                        "naziv": naziv,
                        "cena" : cena,
                        "opis": opis,
                        "sastav": sastav,
                        "slika": slika,
                        "tip": vrsta
                    }

                    proizvodi.push(proizvod)
                    localStorage.setItem("proizvodi", JSON.stringify(proizvodi))

                    alertify.success("Uspešno ste dodali proizvod")
                    return navigator("/zaposleni")
                }}>Dodaj proizvod</button>
            </div>
        </div>
    )
}