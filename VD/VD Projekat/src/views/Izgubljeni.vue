<template>
    <div id="pozadinaKat">
        <div>
            <ul class="breadcrumb" v-if="jezik=='srpski'">
                <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
                <li>Izgubljeni ljubimci</li>
            </ul>
            <ul class="breadcrumb" v-else>
                <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
                <li>Lost pets</li>
            </ul>
        </div>
        <div>
            <h2 v-if="jezik=='srpski'"><b>Svi oglasi:</b></h2>
            <h2 v-else><b>All ads:</b></h2>
            <div v-for="oglas in sviOglasi" :key="oglas.id" id="pozadina" style="width: 60%; margin-left: 20%; font-weight:bold; font-size:20px;">
                <h4 style="font-weight: bold; font-size: 30px;">{{oglas.imeLjubimca}}</h4>
                <hr>
                <p class="textual">{{oglas.opis}}</p>
                <hr>
                <b>{{oglas.kontakt}} - {{oglas.username}}</b>&ensp;&ensp;&ensp;&ensp;
                <button @click="idiNaOglas(oglas.id)" class="myButton" v-if="jezik=='srpski'">Pređi na stranicu oglasa</button>
                <button @click="idiNaOglas(oglas.id)" class="myButton" v-else>Go to the ad page</button>
                <hr>
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

    .textual{
        word-wrap: break-word;
    }
</style>

<script>
export default {
    name: 'Izgubljeni',
    data() {
        return{
            sviOglasi: [],
            jezik: ''
        }
    },
    created(){
        this.sviOglasi = JSON.parse(localStorage.getItem('oglasi'))
        this.jezik = localStorage.getItem("jezik")
        document.title= "Azil Cvrle - Izgubljeni ljubimci"
    },
    methods:{
        idiNaOglas(oglasId){
            this.$router.push('/oglas/'+oglasId);
        },
        skoci(p){
            this.$router.push(p);
        },
    }
}
</script>
