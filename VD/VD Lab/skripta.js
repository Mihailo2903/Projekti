$(document).ready(function () {

    var plaviIme;
    var crveniIme;

    provera();

    var pomocni = ["A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"];
    var protekloVreme = 0;
    var handlerProteklo;

    var naPotezu = "Plavi";
    var vremeIgraca = 0;
    var handlerIgrac;

    var otvorioPolje = false;

    var poeniCrveni = 0;
    var poeniPlavi = 0;

    var acosijacije = [
        {
            A1: "SUVA",
            A2: "PLAVA",
            A3: "RAKIJA",
            A4: "DžEM",
            A: "ŠLjIVA",
            B1: "CRVENA",
            B2: "ZLATNA",
            B3: "OTROVNA",
            B4: "CRV",
            B: "JABUKA",
            C1: "MAJMUN",
            C2: "KORA",
            C3: "KALIJUM",
            C4: "ŽUTA",
            C: "BANANA",
            D1: "BOBICA",
            D2: "DIVLjA",
            D3: "SLATKA",
            D4: "ŠLAG",
            D: "JAGODA",
            Resenje: "VOĆE",
        },
        {
            A1: "BRKOVI",
            A2: "VUNDU",
            A3: "MULJ",
            A4: "PATULJASTI",
            A: "SOM",
            B1: "BIK",
            B2: "KIT",
            B3: "TIGAR",
            B4: "ČEKIĆ",
            B: "AJKULA",
            C1: "MANTA",
            C2: "STIV IRVIN",
            C3: "BODLJA",
            C4: "MORSKI GOLUB",
            C: "RAŽA",
            D1: "ZUBI",
            D2: "ARAPAIMA",
            D3: "JATO",
            D4: "KRV",
            D: "PIRANA",
            Resenje: "AMAZON",
        },
        {
            A1: "ASTERIKS",
            A2: "BROD",
            A3: "FRANCUSKA",
            A4: "RIM",
            A: "GALIJA",
            B1: "ZVEZDA",
            B2: "ISPIT",
            B3: "VOJSKA",
            B4: "APSOLVENT",
            B: "ROK",
            C1: "PLAZMA",
            C2: "ARTERIJE",
            C3: "HEMOGLOBIN",
            C4: "VENE",
            C: "KRV",
            D1: "BLAŽENSTVO",
            D2: "KURT KOBEJN",
            D3: "MIR",
            D4: "MEDITACIJA",
            D: "NIRVANA",
            Resenje: "GRUPA",
        },
        {
            A1: "KONJ",
            A2: "REKA",
            A3: "PLAVI",
            A4: "BELI",
            A: "NIL",
            B1: "TUTANKAMON",
            B2: "VLADAR",
            B3: "VELIKA KUĆA",
            B4: "TITULA",
            B: "FARAON",
            C1: "KEOPS",
            C2: "SFINA",
            C3: "GIZA",
            C4: "GROBNICA",
            C: "PIRAMIDA",
            D1: "DELTA NILA",
            D2: "SVETIONIK",
            D3: "GRAD",
            D4: "BIBLIOTEKA",
            D: "ALEKSANDRIJA",
            Resenje: "EGIPAT",
        },
        {
            A1: "KUĆA",
            A2: "DNEVNI",
            A3: "VODOLIJA",
            A4: "LIČNI",
            A: "HOROSKOP",
            B1: "POLICIJA",
            B2: "VAZDUŠNI",
            B3: "GUST",
            B4: "SEMAFOR",
            B: "SAOBRAĆAJ",
            C1: "JIN I JANG",
            C2: "GRB",
            C3: "LOGOTIP",
            C4: "AMBLEM",
            C: "SIMBOL",
            D1: "SABIRANJE",
            D2: "POZITIVAN",
            D3: "MINUS",
            D4: "MATEMATIKA",
            D: "PLUS",
            Resenje: "ZNAK",
        },
        {
            A1: "ZA NOVO",
            A2: "PRAVILO",
            A3: "DRUŠTVO",
            A4: "DRVO",
            A: "STARO",
            B1: "KADA",
            B2: "DIM",
            B3: "POZORIŠTE",
            B4: "PROZOR",
            B: "ZAVESA",
            C1: "ZLATO",
            C2: "OGRADA",
            C3: "GLAS",
            C4: "BODLJIKAVA",
            C: "ŽICA",
            D1: "PETI",
            D2: "SKUP",
            D3: "KUHINJSKI",
            D4: "HEMIJSKI",
            D: "ELEMENT",
            Resenje: "GVOŽĐE",
        },
    ];


    var otvoreno = {
        A1: false,
        A2: false,
        A3: false,
        A4: false,
        B1: false,
        B2: false,
        B3: false,
        B4: false,
        C1: false,
        C2: false,
        C3: false,
        C4: false,
        D1: false,
        D2: false,
        D3: false,
        D4: false,
        broj: 0,
        A: false,
        B: false,
        C: false,
        D: false,
        Resenje: false,
    };

    var trenutnaAsocijacija;

    init();

    function provera(){
        let ime = localStorage.getItem("plavi");
        if(ime == null || ime =="")
            window.location.href="asocijacije-uputstvo.html";
        plaviIme = ime;
        
        ime = localStorage.getItem("crveni");
        if(ime == null || ime =="")
            window.location.href="asocijacije-uputstvo.html";
        crveniIme = ime;
    }

    $("#dalje").click(function () {
        if (naPotezu == "Plavi") {
            krajPlavog();
            return;
        }
        else {
            krajCrvenog();
            return;
        }
    });

    $("#novaIgra").click(function(){
        window.location.href="asocijacije-igra.html";
    });

    $("#napusti").click(function(){
        window.location.href="asocijacije-uputstvo.html";
    });

    function init() {
        ucitajPodatke();
        pocetniSadrzajPolja();
        eventoviZaKlikove();
        zapocniOdbrojavanje();
    }

    function ucitajPodatke() { //ucitava asocijaciju
        let dohvatiAsoc = localStorage.getItem("asocijacije");
        if (dohvatiAsoc != null) {
            acosijacije = JSON.parse(dohvatiAsoc);
        }
        else {
            localStorage.setItem("asocijacije", JSON.stringify(acosijacije));
        }

        let broj = acosijacije.length;
        let ind = Math.floor(Math.random() * broj);
        trenutnaAsocijacija = acosijacije[ind];
    }

    function pocetniSadrzajPolja() { //postavlja sadrzaj na A1,A2,...
        let p = document.getElementById("plaviIme");
        let c = document.getElementById("crveniIme");

        p.innerText = plaviIme;
        c.innerText = crveniIme;

        let a = document.getElementsByClassName("l");
        for (let i = 0; i < a.length; i++) {
            let elem = a[i];
            elem.innerText = elem.id;
        }
        a = document.getElementsByClassName("r");
        for (let i = 0; i < a.length; i++) {
            let elem = a[i];
            elem.innerText = elem.id;
        }
    }

    function eventoviZaKlikove() {
        for (let i = 0; i < pomocni.length; i++) {
            let elem = document.getElementById(pomocni[i]);
            let vrednost;
            switch (pomocni[i]) {
                case "A1": vrednost = trenutnaAsocijacija.A1; break;
                case "A2": vrednost = trenutnaAsocijacija.A2; break;
                case "A3": vrednost = trenutnaAsocijacija.A3; break;
                case "A4": vrednost = trenutnaAsocijacija.A4; break;
                case "B1": vrednost = trenutnaAsocijacija.B1; break;
                case "B2": vrednost = trenutnaAsocijacija.B2; break;
                case "B3": vrednost = trenutnaAsocijacija.B3; break;
                case "B4": vrednost = trenutnaAsocijacija.B4; break;
                case "C1": vrednost = trenutnaAsocijacija.C1; break;
                case "C2": vrednost = trenutnaAsocijacija.C2; break;
                case "C3": vrednost = trenutnaAsocijacija.C3; break;
                case "C4": vrednost = trenutnaAsocijacija.C4; break;
                case "D1": vrednost = trenutnaAsocijacija.D1; break;
                case "D2": vrednost = trenutnaAsocijacija.D2; break;
                case "D3": vrednost = trenutnaAsocijacija.D3; break;
                case "D4": vrednost = trenutnaAsocijacija.D4; break;
            }

            elem.addEventListener("click", function () {
                if (otvorioPolje == true || otvoreno.broj == 16)
                    return;

                let pom = false;

                switch (pomocni[i]) {
                    case "A1": pom = otvoreno.A1; break;
                    case "A2": pom = otvoreno.A2; break;
                    case "A3": pom = otvoreno.A3; break;
                    case "A4": pom = otvoreno.A4; break;
                    case "B1": pom = otvoreno.B1; break;
                    case "B2": pom = otvoreno.B2; break;
                    case "B3": pom = otvoreno.B3; break;
                    case "B4": pom = otvoreno.B4; break;
                    case "C1": pom = otvoreno.C1; break;
                    case "C2": pom = otvoreno.C2; break;
                    case "C3": pom = otvoreno.C3; break;
                    case "C4": pom = otvoreno.C4; break;
                    case "D1": pom = otvoreno.D1; break;
                    case "D2": pom = otvoreno.D2; break;
                    case "D3": pom = otvoreno.D3; break;
                    case "D4": pom = otvoreno.D4; break;
                }

                if (pom == true)
                    return;

                otvorioPolje = true;
                otvoreno.broj++;
                Omoguci();
                elem.innerText = vrednost;
                switch (pomocni[i]) {
                    case "A1": otvoreno.A1 = true; break;
                    case "A2": otvoreno.A2 = true; break;
                    case "A3": otvoreno.A3 = true; break;
                    case "A4": otvoreno.A4 = true; break;
                    case "B1": otvoreno.B1 = true; break;
                    case "B2": otvoreno.B2 = true; break;
                    case "B3": otvoreno.B3 = true; break;
                    case "B4": otvoreno.B4 = true; break;
                    case "C1": otvoreno.C1 = true; break;
                    case "C2": otvoreno.C2 = true; break;
                    case "C3": otvoreno.C3 = true; break;
                    case "C4": otvoreno.C4 = true; break;
                    case "D1": otvoreno.D1 = true; break;
                    case "D2": otvoreno.D2 = true; break;
                    case "D3": otvoreno.D3 = true; break;
                    case "D4": otvoreno.D4 = true; break;
                }

            });
        }
    }

    function zapocniOdbrojavanje() {
        handlerProteklo = setInterval(stoperica, 1000);
        handlerIgrac = setInterval(potezPlavog, 1000);
    }

    function stoperica() {
        protekloVreme++;
        if (protekloVreme == 240) {
            otvoriA();
            otvoriB();
            otvoriC();
            otvoriD();

            otvoreno.Resenje = true;
            document.getElementById("UnosKonacno").value = trenutnaAsocijacija.Resenje;
            document.getElementById("UnosKonacno").disabled = true;

            $(".l1, .l2, .l3, .l4, #UnosA, .r1, .r2, .r3, .r4, #UnosB, .l8, .l9, .l10, .l11, #UnosC, .r8, .r9, .r10, .r11, #UnosD, #UnosKonacno").each(
                function () {
                    if ($(this).css("background-color") == 'rgb(255, 255, 0)') {
                        $(this).css({
                            "background-color": "green",
                            "color": "white"
                        });
                    }
                }
            );
            zavrsiIgru();
            return;
        }
        let sekunde = 240 - protekloVreme;
        let elem = document.getElementById("preostalo");
        let minuti = Math.floor(sekunde / 60);
        let sek = sekunde % 60;
        let kon = (sek < 10) ? '0' + sek : sek;
        elem.innerText = "Preostalo vreme: " + minuti + ":" + kon;
    }


    function potezPlavog() {
        vremeIgraca++;
        if (vremeIgraca == 10) {
            krajPlavog();
            return;
        }

        document.getElementById("plaviVreme").innerText = "Sekundi za potez: " + (10 - vremeIgraca);
    }

    function potezCrvenog() {
        vremeIgraca++;
        if (vremeIgraca == 10) {
            krajCrvenog();
            return;
        }

        document.getElementById("crveniVreme").innerText = "Sekundi za potez: " + (10 - vremeIgraca);
    }

    function krajCrvenog() {
        clearInterval(handlerIgrac);
        vremeIgraca = 0;
        naPotezu = "Plavi";

        obrisiVisak();

        if (otvorioPolje == false && otvoreno.broj != 16)
            otvoriRandom();

        if (otvorioPolje == true) {
            otvorioPolje = false;
            Onemoguci();
        }

        if (otvoreno.broj == 16)
            Omoguci();

        $("#igrac").css("color", "blue");
        document.getElementById("igrac").innerText = "Plavi igrač na potezu";
        handlerIgrac = setInterval(potezPlavog, 1000);
        document.getElementById("crveniVreme").innerText = "Sekundi za potez: /";
        document.getElementById("plaviVreme").innerText = "Sekundi za potez: 10";
    }

    function krajPlavog() {
        clearInterval(handlerIgrac);
        vremeIgraca = 0;
        naPotezu = "Crveni";

        obrisiVisak();

        if (otvorioPolje == false && otvoreno.broj != 16)
            otvoriRandom();

        if (otvorioPolje == true) {
            otvorioPolje = false;
            Onemoguci();
        }

        if (otvoreno.broj == 16)
            Omoguci();

        handlerIgrac = setInterval(potezCrvenog, 1000);
        $("#igrac").css("color", "red");
        document.getElementById("igrac").innerText = "Crveni igrač na potezu";
        document.getElementById("plaviVreme").innerText = "Sekundi za potez: /";
        document.getElementById("crveniVreme").innerText = "Sekundi za potez: 10";
    }

    $("#UnosA").keypress(function (event) {
        if (otvoreno.A == true)
            return;
        let pokusaj = document.getElementById("UnosA").value;
        let resenje = trenutnaAsocijacija.A;
        if (event.keyCode == 13 && pokusaj.toLowerCase() == resenje.toLowerCase()) {
            let p = otvoriA();

            if (naPotezu == "Plavi") {
                $(".l1, .l2, .l3, .l4, #UnosA, #A").css({
                    "background-color": "blue",
                    "color": "white"
                })
            }

            else {
                $(".l1, .l2, .l3, .l4, #UnosA, #A").css({
                    "background-color": "red",
                    "color": "white"
                })
            }
            if (naPotezu == "Plavi") {
                poeniPlavi = poeniPlavi + p;
                document.getElementById("plaviPoeni").innerText = "Broj poena: " + poeniPlavi;
            }

            else {
                poeniCrveni = poeniCrveni + p;
                document.getElementById("crveniPoeni").innerText = "Broj poena: " + poeniCrveni;
            }
        }

        else if (event.keyCode == 13 && pokusaj.toLowerCase() != resenje.toLowerCase()) {
            if (naPotezu == "Plavi") {
                krajPlavog();
                return;
            }
            else {
                krajCrvenog();
                return;
            }
        }

    });

    $("#UnosB").keypress(function (event) {
        if (otvoreno.B == true)
            return;
        let pokusaj = document.getElementById("UnosB").value;
        let resenje = trenutnaAsocijacija.B;
        if (event.keyCode == 13 && pokusaj.toLowerCase() == resenje.toLowerCase()) {
            let p = otvoriB();
            if (naPotezu == "Plavi") {
                $(".r1, .r2, .r3, .r4, #UnosB, #B").css({
                    "background-color": "blue",
                    "color": "white"
                })
            }

            else {
                $(".r1, .r2, .r3, .r4, #UnosB, #B").css({
                    "background-color": "red",
                    "color": "white"
                })
            }

            if (naPotezu == "Plavi") {
                poeniPlavi = poeniPlavi + p;
                document.getElementById("plaviPoeni").innerText = "Broj poena: " + poeniPlavi;
            }

            else {
                poeniCrveni = poeniCrveni + p;
                document.getElementById("crveniPoeni").innerText = "Broj poena: " + poeniCrveni;
            }
        }

        else if (event.keyCode == 13 && pokusaj.toLowerCase() != resenje.toLowerCase()) {
            if (naPotezu == "Plavi") {
                krajPlavog();
                return;
            }
            else {
                krajCrvenog();
                return;
            }
        }
    });


    $("#UnosC").keypress(function (event) {
        if (otvoreno.C == true)
            return;
        let pokusaj = document.getElementById("UnosC").value;
        let resenje = trenutnaAsocijacija.C;
        if (event.keyCode == 13 && pokusaj.toLowerCase() == resenje.toLowerCase()) {
            let p = otvoriC();
            if (naPotezu == "Plavi") {
                $(".l8, .l9, .l10, .l11, #UnosC, #C").css({
                    "background-color": "blue",
                    "color": "white"
                })
            }

            else {
                $(".l8, .l9, .l10, .l11, #UnosC, #C").css({
                    "background-color": "red",
                    "color": "white"
                })
            }

            if (naPotezu == "Plavi") {
                poeniPlavi = poeniPlavi + p;
                document.getElementById("plaviPoeni").innerText = "Broj poena: " + poeniPlavi;
            }

            else {
                poeniCrveni = poeniCrveni + p;
                document.getElementById("crveniPoeni").innerText = "Broj poena: " + poeniCrveni;
            }
        }

        else if (event.keyCode == 13 && pokusaj.toLowerCase() != resenje.toLowerCase()) {
            if (naPotezu == "Plavi") {
                krajPlavog();
                return;
            }
            else {
                krajCrvenog();
                return;
            }
        }
    });

    $("#UnosD").keypress(function (event) {
        if (otvoreno.D == true)
            return;
        let pokusaj = document.getElementById("UnosD").value;
        let resenje = trenutnaAsocijacija.D;
        if (event.keyCode == 13 && pokusaj.toLowerCase() == resenje.toLowerCase()) {
            let p = otvoriD();
            if (naPotezu == "Plavi") {
                $(".r8, .r9, .r10, .r11, #UnosD, #D").css({
                    "background-color": "blue",
                    "color": "white"
                })
            }

            else {
                $(".r8, .r9, .r10, .r11, #UnosD, #D").css({
                    "background-color": "red",
                    "color": "white"
                })
            }

            if (naPotezu == "Plavi") {
                poeniPlavi = poeniPlavi + p;
                document.getElementById("plaviPoeni").innerText = "Broj poena: " + poeniPlavi;
            }

            else {
                poeniCrveni = poeniCrveni + p;
                document.getElementById("crveniPoeni").innerText = "Broj poena: " + poeniCrveni;
            }
        }

        else if (event.keyCode == 13 && pokusaj.toLowerCase() != resenje.toLowerCase()) {
            if (naPotezu == "Plavi") {
                krajPlavog();
                return;
            }
            else {
                krajCrvenog();
                return;
            }
        }
    });

    $("#UnosKonacno").keypress(function (event) {
        let pokusaj = document.getElementById("UnosKonacno").value;
        let resenje = trenutnaAsocijacija.Resenje;
        if (event.keyCode == 13 && pokusaj.toLowerCase() == resenje.toLowerCase()) {
            if (naPotezu == "Plavi") {
                otvoriSve("Plavi");
            }
            else {
                otvoriSve("Crveni");
            }
        }

        else if (event.keyCode == 13 && pokusaj.toLowerCase() != resenje.toLowerCase()) {
            if (naPotezu == "Plavi") {
                krajPlavog();
                return;
            }
            else {
                krajCrvenog();
                return;
            }
        }
    });


    function Omoguci() {
        if (otvoreno.A == false)
            document.getElementById("UnosA").disabled = false;
        if (otvoreno.B == false)
            document.getElementById("UnosB").disabled = false;
        if (otvoreno.C == false)
            document.getElementById("UnosC").disabled = false;
        if (otvoreno.D == false)
            document.getElementById("UnosD").disabled = false;
        if (otvoreno.Resenje == false)
            document.getElementById("UnosKonacno").disabled = false;
    }

    function Onemoguci() {
        document.getElementById("UnosA").disabled = true;
        document.getElementById("UnosB").disabled = true;
        document.getElementById("UnosC").disabled = true;
        document.getElementById("UnosD").disabled = true;
        document.getElementById("UnosKonacno").disabled = true;
    }

    function otvoriRandom() {
        otvoreno.broj++;
        if (otvoreno.A1 == false) {
            otvoreno.A1 = true;
            document.getElementById("A1").innerText = trenutnaAsocijacija.A1;
            return;
        }
        if (otvoreno.A2 == false) {
            otvoreno.A2 = true;
            document.getElementById("A2").innerText = trenutnaAsocijacija.A2;
            return;
        }
        if (otvoreno.A3 == false) {
            otvoreno.A3 = true;
            document.getElementById("A3").innerText = trenutnaAsocijacija.A3;
            return;
        }
        if (otvoreno.A4 == false) {
            otvoreno.A4 = true;
            document.getElementById("A4").innerText = trenutnaAsocijacija.A4;
            return;
        }
        if (otvoreno.B1 == false) {
            otvoreno.B1 = true;
            document.getElementById("B1").innerText = trenutnaAsocijacija.B1;
            return;
        }
        if (otvoreno.B2 == false) {
            otvoreno.B2 = true;
            document.getElementById("B2").innerText = trenutnaAsocijacija.B2;
            return;
        }
        if (otvoreno.B3 == false) {
            otvoreno.B3 = true;
            document.getElementById("B3").innerText = trenutnaAsocijacija.B3;
            return;
        }
        if (otvoreno.B4 == false) {
            otvoreno.B4 = true;
            document.getElementById("B4").innerText = trenutnaAsocijacija.B4;
            return;
        }
        if (otvoreno.C1 == false) {
            otvoreno.C1 = true;
            document.getElementById("C1").innerText = trenutnaAsocijacija.C1;
            return;
        }
        if (otvoreno.C2 == false) {
            otvoreno.C2 = true;
            document.getElementById("C2").innerText = trenutnaAsocijacija.C2;
            return;
        }
        if (otvoreno.C3 == false) {
            otvoreno.C3 = true;
            document.getElementById("C3").innerText = trenutnaAsocijacija.C3;
            return;
        }
        if (otvoreno.C4 == false) {
            otvoreno.C4 = true;
            document.getElementById("C4").innerText = trenutnaAsocijacija.C4;
            return;
        }
        if (otvoreno.D1 == false) {
            otvoreno.D1 = true;
            document.getElementById("D1").innerText = trenutnaAsocijacija.D1;
            return;
        }
        if (otvoreno.D2 == false) {
            otvoreno.D2 = true;
            document.getElementById("D2").innerText = trenutnaAsocijacija.D2;
            return;
        }
        if (otvoreno.D3 == false) {
            otvoreno.D3 = true;
            document.getElementById("D3").innerText = trenutnaAsocijacija.D3;
            return;
        }
        if (otvoreno.D4 == false) {
            otvoreno.D4 = true;
            document.getElementById("D4").innerText = trenutnaAsocijacija.D4;
            return;
        }
    }

    function otvoriA() {
        let ukupno = 0;
        if (otvoreno.A == false) {
            otvoreno.A = true;
            document.getElementById("UnosA").value = trenutnaAsocijacija.A;
            document.getElementById("UnosA").disabled = true;
            ukupno += 5;
        }

        if (otvoreno.A1 == false) {
            otvoreno.broj++;
            otvoreno.A1 = true;
            document.getElementById("A1").innerText = trenutnaAsocijacija.A1;
            ukupno++;
        }
        if (otvoreno.A2 == false) {
            otvoreno.broj++;
            otvoreno.A2 = true;
            document.getElementById("A2").innerText = trenutnaAsocijacija.A2;
            ukupno++;
        }
        if (otvoreno.A3 == false) {
            otvoreno.broj++;
            otvoreno.A3 = true;
            document.getElementById("A3").innerText = trenutnaAsocijacija.A3;
            ukupno++;
        }
        if (otvoreno.A4 == false) {
            otvoreno.broj++;
            otvoreno.A4 = true;
            document.getElementById("A4").innerText = trenutnaAsocijacija.A4;
            ukupno++;
        }
        return ukupno;
    }

    function otvoriB() {
        let ukupno = 0;
        if (otvoreno.B == false) {
            otvoreno.B = true;
            document.getElementById("UnosB").value = trenutnaAsocijacija.B;
            document.getElementById("UnosB").disabled = true;
            ukupno += 5;
        }

        if (otvoreno.B1 == false) {
            otvoreno.broj++;
            otvoreno.B1 = true;
            document.getElementById("B1").innerText = trenutnaAsocijacija.B1;
            ukupno++;
        }
        if (otvoreno.B2 == false) {
            otvoreno.broj++;
            otvoreno.B2 = true;
            document.getElementById("B2").innerText = trenutnaAsocijacija.B2;
            ukupno++;
        }
        if (otvoreno.B3 == false) {
            otvoreno.broj++;
            otvoreno.B3 = true;
            document.getElementById("B3").innerText = trenutnaAsocijacija.B3;
            ukupno++;
        }
        if (otvoreno.B4 == false) {
            otvoreno.broj++;
            otvoreno.B4 = true;
            document.getElementById("B4").innerText = trenutnaAsocijacija.B4;
            ukupno++;
        }
        return ukupno;
    }

    function otvoriC() {
        let ukupno = 0;
        if (otvoreno.C == false) {
            otvoreno.C = true;
            document.getElementById("UnosC").value = trenutnaAsocijacija.C;
            document.getElementById("UnosC").disabled = true;
            ukupno += 5;
        }

        if (otvoreno.C1 == false) {
            otvoreno.broj++;
            otvoreno.C1 = true;
            document.getElementById("C1").innerText = trenutnaAsocijacija.C1;
            ukupno++;
        }

        if (otvoreno.C2 == false) {
            otvoreno.broj++;
            otvoreno.C2 = true;
            document.getElementById("C2").innerText = trenutnaAsocijacija.C2;
            ukupno++;
        }
        if (otvoreno.C3 == false) {
            otvoreno.broj++;
            otvoreno.C3 = true;
            document.getElementById("C3").innerText = trenutnaAsocijacija.C3;
            ukupno++;
        }
        if (otvoreno.C4 == false) {
            otvoreno.broj++;
            otvoreno.C4 = true;
            document.getElementById("C4").innerText = trenutnaAsocijacija.C4;
            ukupno++;
        }
        return ukupno;
    }

    function otvoriD() {
        let ukupno = 0;
        if (otvoreno.D == false) {
            otvoreno.D = true;
            document.getElementById("UnosD").value = trenutnaAsocijacija.D;
            document.getElementById("UnosD").disabled = true;
            ukupno += 5;
        }

        if (otvoreno.D1 == false) {
            otvoreno.broj++;
            otvoreno.D1 = true;
            document.getElementById("D1").innerText = trenutnaAsocijacija.D1;
            ukupno++;
        }
        if (otvoreno.D2 == false) {
            otvoreno.broj++;
            otvoreno.D2 = true;
            document.getElementById("D2").innerText = trenutnaAsocijacija.D2;
            ukupno++;
        }
        if (otvoreno.D3 == false) {
            otvoreno.broj++;
            otvoreno.D3 = true;
            document.getElementById("D3").innerText = trenutnaAsocijacija.D3;
            ukupno++;
        }
        if (otvoreno.D4 == false) {
            otvoreno.broj++;
            otvoreno.D4 = true;
            document.getElementById("D4").innerText = trenutnaAsocijacija.D4;
            ukupno++;
        }
        return ukupno;
    }

    function otvoriSve(boja) {
        let pa = otvoriA();
        let pb = otvoriB();
        let pc = otvoriC();
        let pd = otvoriD();

        otvoreno.Resenje = true;
        document.getElementById("UnosKonacno").value = trenutnaAsocijacija.Resenje;
        document.getElementById("UnosKonacno").disabled = true;

        $(".l1, .l2, .l3, .l4, #UnosA, .r1, .r2, .r3, .r4, #UnosB, .l8, .l9, .l10, .l11, #UnosC, .r8, .r9, .r10, .r11, #UnosD, #UnosKonacno").each(
            function () {
                if ($(this).css("background-color") == 'rgb(255, 255, 0)') {
                    if (boja == "Plavi") {
                        $(this).css({
                            "background-color": "blue",
                            "color": "white"
                        });
                    }
                    else {
                        $(this).css({
                            "background-color": "red",
                            "color": "white"
                        });
                    }
                }
            }
        );

        if (boja == "Plavi") {
            poeniPlavi = poeniPlavi + pa + pb + pc + pd + 10;
            document.getElementById("plaviPoeni").innerText = "Broj poena: " + poeniPlavi;
        }

        else {
            poeniCrveni = poeniCrveni + pa + pb + pc + pd + 10;
            document.getElementById("crveniPoeni").innerText = "Broj poena: " + poeniCrveni;
        }

        zavrsiIgru();
    }

    function obrisiVisak() {
        if (otvoreno.A == false)
            document.getElementById("UnosA").value = "";
        if (otvoreno.B == false)
            document.getElementById("UnosB").value = "";
        if (otvoreno.C == false)
            document.getElementById("UnosC").value = "";
        if (otvoreno.D == false)
            document.getElementById("UnosD").value = "";
        if (otvoreno.Resenje == false)
            document.getElementById("UnosKonacno").value = "";
    }

    function zavrsiIgru() {
        clearInterval(handlerProteklo);
        clearInterval(handlerIgrac);
        let s = "";
        if (poeniCrveni > poeniPlavi) {
            $("#preostalo").css("color", "red");
            $("#igrac").css("color", "red");
            s = crveniIme + " je pobedinik";
        }
        else if (poeniPlavi > poeniCrveni) {
            $("#preostalo").css("color", "blue");
            $("#igrac").css("color", "blue");
            s = plaviIme + " je pobedinik";
        }
        else {
            $("#preostalo").css("color", "black");
            $("#igrac").css("color", "black");
            s = "Nerešeno";
        }

        document.getElementById("preostalo").innerText = s;
        document.getElementById("plaviVreme").innerText = "Sekundi za potez: / ";
        document.getElementById("crveniVreme").innerText = "Sekundi za potez: / ";
        let rez = "Rezultat: " + plaviIme + " " + poeniPlavi + "-" + poeniCrveni + " " + crveniIme;
        document.getElementById("igrac").innerText = rez;

        document.getElementById("dalje").disabled = true;

    }


});

