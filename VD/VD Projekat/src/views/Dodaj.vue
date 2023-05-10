<template>
    <div class="mid" id="pozadinaKat">
        <div>
            <ul class="breadcrumb" v-if="jezik=='srpski'">
                <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
                <li>Moj nalog</li>
            </ul>
            <ul class="breadcrumb" v-else>
                <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
                <li>Add an ad</li>
            </ul>
        </div>
        <div v-if="ulogovanKorisnik==''">
        <br><br>
            <h3 v-if="jezik=='srpski'">Ulogujte se</h3>
            <h3 v-else>Login</h3>
            <center>
            <table style="align-content: center; align-items: center; vertical-align: middle;">
                <tr>
                    <td v-if="jezik=='srpski'">Korisničko ime</td>
                    <td v-else>Username</td>
                    <td>
                        <input type="text" v-model="username" name="username" id="pozadina">
                    </td>
                </tr>
                <tr>
                    <td v-if="jezik=='srpski'">Šifra</td>
                    <td v-else>Password</td>
                    <td>
                        <input type="text" v-model="password" name="password" id="pozadina">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button @click="login()" class="myButton" v-if="jezik=='srpski'">Ulogujte se</button>
                        <button @click="login()" class="myButton" v-else>Log in</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="color: red;">
                        {{errormsg}}
                    </td>
                </tr>
            </table>
            </center>
            <br>
        </div>

        <div v-else class="textual">
            <div style="width: 60%; margin-left: 20%; font-weight:bold; font-size:30px;" id="pozadina">
                <div v-if="jezik=='srpski'">Dobrodošli {{ulogovanKorisnik}}</div>
                <div v-else>Welcome {{ulogovanKorisnik}}</div>
                <hr>
            </div>
            <h6 v-if="jezik=='srpski'">Dodajte oglas:</h6>
            <h6 v-else>Add an ad:</h6>
            <center>
            <table>
                <tr>
                    <td v-if="jezik=='srpski'">Ime ljubimca</td>
                    <td v-else>Pet name</td>
                    <td>
                        <input type="text" name="imeljub" v-model="imeLjubimca" id="pozadina">
                    </td>
                </tr>
                <tr>
                    <td v-if="jezik=='srpski'">Opis</td>
                    <td v-else>Description</td>
                    <td>
                        <textarea name="tarea" rows="4" cols="23" v-model="opis" id="pozadina"></textarea>
                    </td>
                </tr>
                <tr>
                    <td v-if="jezik=='srpski'">Kontakt</td>
                    <td v-else>Contact</td>
                    <td>
                        <input type="text" name="kontkt" v-model="kontakt" id="pozadina">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button @click="dodaj()" class="myButton" v-if="jezik=='srpski'">Objavi</button>
                        <button @click="dodaj()" class="myButton" v-else>Post an ad</button>
                    </td>
                </tr>
            </table>
            </center>
            <br>
        </div>

    </div>
</template>

<style scoped>
    .myButton{
        border-radius: 10px;
        background-color: darkgreen;
        height: 40px;
        color: white;
    }

    input:-webkit-autofill,
    input:-webkit-autofill:hover,
    input:-webkit-autofill:focus,
    input:-webkit-autofill:active {
        -webkit-box-shadow: 0 0 0 30px lightgreen inset !important;
    }

    .textual{
        word-wrap: break-word;
    }
</style>

<script>
export default {
    name: 'Dodaj',
    data() {
        return{
            ulogovanKorisnik: '',
            username: '',
            password: '',
            sviKorisnici: [],
            errormsg: '',
            mojiOglasi: [],

            imeLjubimca: '',
            opis: '',
            kontakt: '',
            jezik: ''
        }
    },
    created(){
        if(localStorage.getItem('ulogovani') == null){
            this.ulogovanKorisnik = ''
        } else {
            this.ulogovanKorisnik = localStorage.getItem('ulogovani')
        }
        this.sviKorisnici = JSON.parse(localStorage.getItem('korisnici'))
        this.jezik = localStorage.getItem("jezik")
        document.title= "Azil Cvrle - Dodaj"
    },
    methods:{
        login(){
            var user = this.sviKorisnici.find(user=>user.username == this.username && user.password == this.password)
            if(!user){
                if(this.jezik=='srpski'){
                    this.errormsg = 'Korisničko ime i/ili šifra ne odgovaraju'
                } else {
                    this.errormsg = 'Username and/or password do not match'
                }
                return
            }
            this.errormsg = ''
            localStorage.setItem('ulogovani', user.username)
            this.ulogovanKorisnik = user.username
        },
        dodaj(){
            var currid = parseInt(JSON.parse(localStorage.getItem('autoincrementpk_oglasi')))
            currid = currid + 1
            localStorage.setItem('autoincrementpk_oglasi', currid)
            currid = currid - 1

            var noviOglas = {
                id: currid,
                username: this.ulogovanKorisnik,
                imeLjubimca: this.imeLjubimca,
                opis: this.opis,
                kontakt: this.kontakt
            }
            var sviOglasi = JSON.parse(localStorage.getItem('oglasi'))
            sviOglasi.unshift(noviOglas)
            localStorage.setItem('oglasi', JSON.stringify(sviOglasi))
            alert('Uspesno dodavanje oglasa')
        },
        skoci(p){
            this.$router.push(p);
        },
    }
}
</script>
