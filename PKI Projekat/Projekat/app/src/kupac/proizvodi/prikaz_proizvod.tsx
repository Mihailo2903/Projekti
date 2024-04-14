import { useNavigate } from "react-router-dom"

export default function PrikazProizvod({id, naziv, slika}){
    const navigator = useNavigate()

    return(
        <div>
            <h3>{naziv}</h3>
            <img src={slika} alt=""/>
            <br/>
            <button type="submit" className="btn but1" onClick={() => {
                localStorage.setItem("proizvodId", id)
                return navigator("/proizvod")
                }} >Naruči</button>
           
        </div>
    )
}