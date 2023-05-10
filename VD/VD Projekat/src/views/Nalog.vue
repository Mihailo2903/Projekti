<template>
    <div id="pozadinaKat">
        <div>
            <ul class="breadcrumb" v-if="jezik=='srpski'">
                <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
                <li>Moj nalog</li>
            </ul>
            <ul class="breadcrumb" v-else>
                <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
                <li>My account</li>
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
            <h3 v-if="jezik=='srpski'">Vaše objave:</h3>
            <h3 v-else>Your ads:</h3>
            <div v-for="oglas in mojiOglasi" :key="oglas.id" style="width: 60%; margin-left: 20%; font-weight:bold; font-size:20px; " id="pozadina">
                <h4>{{oglas.imeLjubimca}}</h4>
                <p>{{oglas.opis}}</p>
                <!-- <b>{{oglas.kontakt}} - {{oglas.username}}</b> -->
                &ensp;&ensp;
                <button @click="obrisi(oglas.id)" class="myButton" v-if="jezik=='srpski'">Obrisi oglas</button>
                <button @click="obrisi(oglas.id)" class="myButton" v-else>Delete this ad</button>
                &ensp;&ensp;
                <button @click="idiNaOglas(oglas.id)" class="myButton" v-if="jezik=='srpski'">Pređi na stranicu oglasa</button>
                <button @click="idiNaOglas(oglas.id)" class="myButton" v-else>Go to the ad page</button>
                <hr>
            </div>

            <br><br>
            <br><br>
            <div>
                <button @click="logout()" class="myButton" v-if="jezik=='srpski'">Odjavi se</button>
                <button @click="logout()" class="myButton" v-else>Log out</button>
            </div>
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
            jezik: ''
        }
    },
    created(){
        if(localStorage.getItem('ulogovani') == null){
            this.ulogovanKorisnik = ''
        } else {
            this.ulogovanKorisnik = localStorage.getItem('ulogovani')
            var sviOglasi = JSON.parse(localStorage.getItem('oglasi'))
            this.mojiOglasi = sviOglasi.filter(og=>og.username == this.ulogovanKorisnik)
        }
        this.sviKorisnici = JSON.parse(localStorage.getItem('korisnici'))
        this.jezik = localStorage.getItem("jezik")
        document.title= "Azil Cvrle - Nalog"
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
            var sviOglasi = JSON.parse(localStorage.getItem('oglasi'))
            this.mojiOglasi = sviOglasi.filter(og=>og.username == this.ulogovanKorisnik)
        },
        logout(){
            localStorage.removeItem('ulogovani')
            this.ulogovanKorisnik = ''
            this.$router.push('/')
        },
        obrisi(id_oglasa){
            var ret = confirm('Da li ste sigurni da zelite da obrisete oglas?')
            if(!ret) return

            var sviOglasi = JSON.parse(localStorage.getItem('oglasi'))
            
            var targetoglas = sviOglasi.find(targetoglas=>targetoglas.id == id_oglasa)
            var index = sviOglasi.indexOf(targetoglas)
            sviOglasi.splice(index, 1)

            localStorage.setItem('oglasi', JSON.stringify(sviOglasi))
            this.mojiOglasi = sviOglasi.filter(og=>og.username == this.ulogovanKorisnik)
            
        },
        skoci(p){
            this.$router.push(p);
        },
        idiNaOglas(oglasId){
            this.$router.push('/oglas/'+oglasId);
        },
    }
}
</script>
