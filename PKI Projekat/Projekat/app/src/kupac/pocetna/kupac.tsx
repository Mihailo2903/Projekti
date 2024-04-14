import { useNavigate } from "react-router-dom";
import Navigation from "../../navigation/navigation";
import { Carousel } from 'react-bootstrap';

import "./kupac.css";
import { useEffect } from "react";
import { ProveriLogovanost } from "../../util";


export default function Kupac(){
    const navigator = useNavigate()

    useEffect(()=>{
        ProveriLogovanost(navigator, "kupac")
    })

    let ulogovan = JSON.parse(localStorage.getItem("ulogovan"))

    return(
        <div className="kupac">
            <Navigation tip="kupac"></Navigation>
            <h1>Dobrodošli {ulogovan.ime} {ulogovan.prezime}, pogledajte neke od naših aktivnih promocija!</h1>
            <Carousel interval={3000}>
                <Carousel.Item>
                    <h3>5 + 1 krempita</h3>
                    <img
                    src="./images/6_krempita.jpg"
                    alt="First slide"
                    onClick={() => navigator("/promocije")}
                    />  
                </Carousel.Item>
                <Carousel.Item>    
                    <h3>2 + 1 ledena kocka</h3>
                    <img 
                    src="./images/ledene_kocke.jpg"
                    alt="Second slide"
                    onClick={() => navigator("/promocije")}
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <h3>2 tiramisua + kafa</h3>
                    <img
                    src="./images/tiramisu_kafa.jpg"
                    alt="Third slide"
                    onClick={() => navigator("/promocije")}
                    />
                </Carousel.Item>
            </Carousel>
        </div>
    )
}