import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ProveriLogovanost, dohvatiNarudzbine, dohvatiProizvode, podrazumevaniOpis } from "../../util";
import Navigation from "../../navigation/navigation";
import 'alertifyjs/build/css/alertify.css';
import 'alertifyjs/build/css/themes/default.css';
import alertify from 'alertifyjs';
import "./proizvod.css";


export default function Proizvod(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })

    const [kolicina, setKolicina] = useState(0)

    let proizvodId  = localStorage.getItem("proizvodId")
    if(!proizvodId){
        navigator("/kupac")
    } 

    let proizvodi = JSON.parse(localStorage.getItem("proizvodi"))
    if(!proizvodi){
        proizvodi = dohvatiProizvode()
        localStorage.setItem("proizvodi", JSON.stringify(proizvodi))
    }

    let proizvod = proizvodi.find((p) => p.id === parseInt(proizvodId))
    let komentari = JSON.parse(localStorage.getItem("komentari")).filter((k) => k.proizvodId === parseInt(proizvodId))

    let prikazKomentari = komentari.map((k) => {
        return(
            <h4>
                {k.sadrzaj} ~ {k.korisnicko_ime}
            </h4>
        )
    })

    function dodajUKorpu(){
        if(kolicina <= 0){
            alertify.error("Niste uneli količinu")
            return
        }

        let narudzbine = JSON.parse(localStorage.getItem("narudzbine"))
        if(!narudzbine){
            narudzbine = dohvatiNarudzbine()
        }

        let korisnicko_ime = JSON.parse(localStorage.getItem("ulogovan")).korisnicko_ime

        let korpa = narudzbine.find((p) => p.korisnicko_ime === korisnicko_ime && p.status === "KORPA")
        if(!korpa){
            korpa = {
                "korisnicko_ime": korisnicko_ime,
                "broj": narudzbine.length > 0 ? narudzbine[narudzbine.length - 1].broj + 1 : 1,
                "cena": proizvod.cena * kolicina,
                "status": "KORPA",
                "detalji":[
                    {
                        "proizvodId": proizvod.id,
                        "naziv": proizvod.naziv,
                        "cena": proizvod.cena,
                        "kolicina": kolicina
                    }
                ]
            }
            narudzbine.push(korpa)
        } else {
           korpa.cena += proizvod.cena * kolicina
           let detalj = korpa.detalji.find((d) => d.proizvodId === proizvod.id)
           if(!detalj){
                detalj = {
                    "proizvodId": proizvod.id,
                    "naziv": proizvod.naziv,
                    "cena": proizvod.cena,
                    "kolicina": kolicina
                }
                korpa.detalji.push(detalj)
           } else {
                detalj.kolicina += kolicina
           }
        }

        localStorage.setItem("narudzbine", JSON.stringify(narudzbine))
        alertify.success("Uspešno ste dodali proizvod u korpu")
    }

    return(
        <div>
            <Navigation tip="kupac"></Navigation>
            <h1>{proizvod.naziv}</h1>
            <div className="content">
                <span className="left">
                    <img src={proizvod.slika} alt=""/>
                    <h4>Cena: {proizvod.cena} din</h4>
                    <h3>Sastav: {proizvod.sastav}</h3>
                </span>
                <span className="right">
                    <div className="form-group">
                        <label htmlFor="kolicina">Unesite količinu</label>
                        <input type="number" name="kolicina" id="kolicina"  onChange={(e)=>setKolicina(parseInt(e.target.value))} />
                        <button type="submit" className="btn butPro" onClick={()=> dodajUKorpu()}>Dodaj u korpu</button>
                    </div>
                    {
                        proizvod.opis !== null && proizvod.opis !== "" ? <h4 className="opis">{proizvod.opis}</h4> : <h4 className="opis">{podrazumevaniOpis()}</h4>
                    }
                   <div className="komHead">
                        <h2>Komentari</h2>
                        <button type="submit" className="btn butKom" onClick={()=>{
                            localStorage.setItem("proizvodKomentar", JSON.stringify({
                                "id": proizvod.id,
                                "naziv": proizvod.naziv
                            }))
                            navigator("/dodajKomentar")
                        }}>Dodaj komentar</button>
                        <span>&nbsp;</span>
                   </div>
                    {
                        proizvod.tip === "torta" ? <h4>Najbolja torta koju sam do sada probao! ~ user23</h4> : <h4>Najbolji kolač koji sam do sada probao! ~ user23</h4>
                    }
                    <h4>Svakome bih preporučio da proba! ~ user24</h4>
                    <h4>{proizvod.naziv} je moj omiljeni slatkiš! ~ user25</h4>
                    {prikazKomentari}

                </span>
            </div>
            
        </div>
    )
}