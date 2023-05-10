<template>
    <div id="pozadinaKat"> 
        <div id="oglas" class="textual">
            <div>
                <ul class="breadcrumb" v-if="jezik=='srpski'">
                    <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
                    <li><a @click="skoci('/izgubljeni/')" href="#">Izgubljeni ljubimci</a></li>
                    <li>Oglas_{{oglas.id}}</li>
                </ul>
                <ul class="breadcrumb" v-else>
                    <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
                    <li><a @click="skoci('/izgubljeni/')" href="#">Lost pets</a></li>
                    <li>Ad_{{oglas.id}}</li>
                </ul>
            </div>
            <div>
                <br>
                <div id="content">
                    <h4>{{oglas.imeLjubimca}}</h4>
                    <p>{{oglas.opis}}</p>
                    <b>{{oglas.kontakt}} - {{oglas.username}}</b>
                    <hr>
                </div>
                <button @click="exportToPdf()" class="myButton"  >Export to PDF</button><br><br>

                <h5 v-if="jezik=='srpski'">Komentari:</h5>
                <h5 v-else>Comments:</h5>
                <div v-for="kom in komentariObjave" :key="kom.username" class="comment">
                    {{kom.username}}: <br>
                    {{kom.tekstKomentara}}
                    <br>
                </div>
                
                <br><br>
                <div v-if="ulogovanKorisnik!=''">
                    <h6 v-if="jezik=='srpski'">Dodajte komentar:</h6>
                    <h6 v-else>Post a comment:</h6>
                    <table style="margin-left: 35%">
                        <tr>
                            <td v-if="jezik=='srpski'">Tekst:&nbsp;</td>
                            <td v-else>Text:&nbsp;</td>
                            <td>
                                <textarea id="tekstKomentara" name="tekstKomentara" rows="4" cols="23" v-model="tekstKomentara" style="background-color: lightgreen;"></textarea>
                            </td>
                            <td colspan="2" style="margin-left: 30%">
                                <button @click="komentarisi()" class="myButton" v-if="jezik=='srpski'">Komentariši</button>
                                <button @click="komentarisi()" class="myButton" v-else>Post</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .myButton{
        border-radius: 10px;
        background-color: darkgreen;
        height: 30px;
        color: white;
    }

    #content{
        background-color: lightgreen;
        width: 60%;
        margin-left: 20%;
    }

    .comment{
        background-color: lightgreen;
        width: 60%;
        margin-left: 20%;
        margin-bottom: 5px;
    }
    
    .textual{
        word-wrap: break-word;
    }
</style>

<script>
import { jsPDF } from "jspdf";

export default{
    name: 'Oglas',
    data() {
        return{
            oglas: '',
            id: '',
            komentariObjave: [],
            tekstKomentara: '',
            ulogovanKorisnik: '',
            jezik: ''
        }
    },
    created(){
        var sviOglasi = JSON.parse(localStorage.getItem('oglasi'))
        this.id = Number(this.$route.params.id);
        this.oglas = sviOglasi.find(og=>og.id == this.id)
        var sviKomentari = JSON.parse(localStorage.getItem('komentari'))
        this.komentariObjave = sviKomentari.filter(kom=>kom.id==this.id)

        if(localStorage.getItem('ulogovani') == null){
            this.ulogovanKorisnik = ''
        } else {
            this.ulogovanKorisnik = localStorage.getItem('ulogovani')
        }
        this.jezik = localStorage.getItem("jezik")
        document.title= "Azil Cvrle - Oglas " + this.id
    },
    methods:{
        komentarisi(){
            var noviKomentar = {
                id: this.id,
                username: this.ulogovanKorisnik,
                tekstKomentara: this.tekstKomentara
            }
            var sviKomentari = JSON.parse(localStorage.getItem('komentari'))
            sviKomentari.push(noviKomentar)
            localStorage.setItem('komentari', JSON.stringify(sviKomentari))
            this.komentariObjave.push(noviKomentar)
            alert('Dodat komentar')
        },
        exportToPdf(){
            var doc = new jsPDF("l", "mm", [670, 1270]);
            doc.setFontSize(12);
            let oglas = document.getElementById("content");
            doc.html(oglas, {
                callback:function (doc) {
                    doc.save();
                },
            });
        },
        skoci(p){
            this.$router.push(p);
        }
    }
}
</script>
