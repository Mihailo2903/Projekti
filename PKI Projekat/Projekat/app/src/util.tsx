export const dohvatiKorisnike = () => {
    return  [
        {
            "korisnicko_ime": "pera123",
            "ime": "Petar",
            "prezime": "Petrović",
            "lozinka": "pera123",
            "tip": "kupac",
            "telefon": "065/123-456",
            "adresa": "Bulevar kralja Aleksandra 73"
        },
        {
            "korisnicko_ime": "mika123",
            "ime": "Milan",
            "prezime": "Mitrović",
            "lozinka": "mika123",
            "tip": "kupac",
            "telefon": "065/123-456",
            "adresa": "Bulevar kralja Aleksandra 73"
        },
        {
            "korisnicko_ime": "zika123",
            "ime": "Živorad",
            "prezime": "Žikić",
            "lozinka": "zika123",
            "tip": "zaposleni",
            "telefon": "065/123-456",
            "adresa": "Bulevar kralja Aleksandra 73"
        },
    ]
}

export const ProveriLogovanost = (navigator, tip) => {
    let user = JSON.parse(localStorage.getItem("ulogovan"))
    if(!user || user.tip!==tip) {
        navigator("/")
    }
}

export const dohvatiProizvode = () => {
    return[
        {
            "id": 1,
            "naziv": "Baklava",
            "cena"  : 200,
            "sastav": " orasi, prezla, ulje, maslac, voda, šećer, sok od limuna.",
            "slika": "./images/baklava.jpg",
            "tip": "kolac"
        },
        {
            "id": 2,
            "naziv": "Bajadera",
            "cena"  : 200,
            "sastav": " čokolada, orasi, keks, maslac, šećer, ulje.",
            "slika": "./images/bajadera.jpg",
            "tip": "kolac"
        },
        {
            "id": 3,
            "naziv": "Indijaner",
            "cena"  : 200,
            "sastav": "  jaja, šećer, ulje, mleko, brašno, čokolada, vanilin šećer.",
            "slika": "./images/indijaner.jpg",
            "tip": "kolac"
        },
        {
            "id": 4,
            "naziv": "Krempita",
            "cena"  : 200,
            "sastav": " jaja, šećer, brašno, voda, ulje, mleko, puding od vanile.",
            "slika": "./images/krempita.jpg",
            "tip": "kolac"
        },
        {
            "id": 5,
            "naziv": "Ledena kocka",
            "cena"  : 200,
            "sastav": " jaja, šećer, ulje, mleko, kakao, brašno, puding, margarin.",
            "slika": "./images/ledena_kocka.jpg",
            "tip": "kolac"
        },
        {
            "id": 6,
            "naziv": "Tiramisu",
            "cena"  : 200,
            "sastav": " slatka pavlaka, maskarpone, jaja, šećer, nes kafa, piškote.",
            "slika": "./images/tiramisu.jpg",
            "tip": "kolac"
        },
        {
            "id": 7,
            "naziv": "Doboš torta",
            "cena"  : 200,
            "sastav": " jaja, šećer, brašno, čokolada, puter, puding od čokolade.",
            "slika": "./images/dobos_torta.jpg",
            "tip": "torta"
        },
        {
            "id": 8,
            "naziv": "Reforma torta",
            "cena"  : 200,
            "sastav": "  jaja, orasi, čokolada, margarin, šećer u prahu, brašno.",
            "slika": "./images/reforma_torta.jpg",
            "tip": "torta"
        },
        {
            "id": 9,
            "naziv": "Saher torta",
            "cena"  : 200,
            "sastav": "  čokolada, jaja, brašno, maslac, džem, prašak za pecivo.",
            "slika": "./images/saher_torta.jpg",
            "tip": "torta"
        },
        {
            "id": 10,
            "naziv": "Snikers torta",
            "cena"  : 200,
            "sastav": " jaja, kikiriki, brašno, neskvik, čokolada, margarin.",
            "slika": "./images/snikers_torta.jpg",
            "tip": "torta"
        },
        {
            "id": 11,
            "naziv": "Vasina torta",
            "cena"  : 200,
            "sastav": " jaja, šećer, orasi, brašno, pomorandža, čokolada, mleko.",
            "slika": "./images/vasina_torta.jpg",
            "tip": "torta"
        },
        {
            "id": 12,
            "naziv": "Voćna torta",
            "cena"  : 200,
            "sastav": " plazma, malina, mleko, šećer, margarin, slatka pavlaka.",
            "slika": "./images/vocna_torta.jpg",
            "tip": "torta"
        }
    ]
}

export const podrazumevaniOpis = () => {
    return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
}

export const dohvatiNarudzbine = () => {
    return [
        {   
            "korisnicko_ime": "mika123",
            "broj": 1,
            "cena": 1200,
            "status": "ODOBRENA",
            "detalji":[
                {
                    "proizvodId": 6,
                    "naziv": "Tiramisu",
                    "kolicina": 2,
                    "cena": 200
                },
                {
                    "proizvodId": 5,
                    "naziv": "Ledena kocka",
                    "kolicina": 2,
                    "cena": 200
                },
                {
                    "proizvodId": 12,
                    "naziv": "Voćna torta",
                    "kolicina": 2,
                    "cena": 200
                },
            ]
        },
        {   
            "korisnicko_ime": "mika123",
            "broj": 2,
            "cena": 1000,
            "status": "ODBIJENA",
            "detalji":[
                {
                    "proizvodId": 6,
                    "naziv": "Tiramisu",
                    "kolicina": 1,
                    "cena": 200
                },
                {
                    "proizvodId": 5,
                    "naziv": "Ledena kocka",
                    "kolicina": 2,
                    "cena": 200
                },
                {
                    "proizvodId": 12,
                    "naziv": "Voćna torta",
                    "kolicina": 2,
                    "cena": 200
                },
            ]
        }
    ]
}

export const dohvatiSlike = () => {
    return [
       {
         "putanja": "./images/nove/bounty_torta.jpg",
         "naziv": "Bounty torta"
       },
       {
        "putanja": "./images/nove/cheesecake.jpg",
        "naziv": "Cheesecake"
      },
      {
        "putanja": "./images/nove/oreo_torta.jpg",
        "naziv": "Oreo torta"
      },
      {
        "putanja": "./images/nove/urmasica.jpg",
        "naziv": "Urmašica"
      },
    ]
}