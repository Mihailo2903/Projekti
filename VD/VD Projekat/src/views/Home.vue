<template>
  <div class="textual" id="pozadinaKat">
    <div>
        <ul class="breadcrumb">
            <li>
              <div v-if="jezik=='srpski'">
                Azil Cvrle
              </div>
              <div v-else>
                Asylum Cvrle
              </div>
            </li>
        </ul>
    </div>

    <div class="home" style="margin: 0 0 0 0; padding: 0 0 0 0;" v-if="jezik=='srpski'">
      <br>
      <div style="font-weight:bold; font-size:30px;" id="pozadina">
        <h1><b>Dobrodošli na vebsajt azila Cvrle</b></h1>
        <h6>
          Ovde možete pronaći informacije o životinjama u našem azilu, kao i o izgubljenim životinjama
        </h6>
        <hr>
      </div>
      <br>
      <h3>Najnoviji oglasi o izgubljenim ljubimcima:</h3>
      <div v-for="oglas in najnovijioglasi" :key="oglas.id" style="width: 60%; margin-left: 20%; font-weight:bold; font-size:20px;" id="pozadina">
        <h4 style="font-size: 30px; font-weight: bold;">{{oglas.imeLjubimca}}</h4>
        <hr>
        <p>{{oglas.opis}}</p>
        <hr>
        <b>{{oglas.kontakt}} - {{oglas.username}}</b>&ensp;&ensp;&ensp;&ensp;
        <button @click="idiNaOglas(oglas.id)" class="myButton">Pređi na stranicu oglasa</button>
        <hr>
      </div>
      <br>
      <div v-if="najnovijioglasi.length==0">
        <h6>Nema oglasa :)</h6>
      </div>
    </div>

    <div class="home" style="margin: 0 0 0 0; padding: 0 0 0 0;" v-else>
      <br>
      <div style="font-weight:bold; font-size:30px;" id="pozadina">
        <h1><b>Welcome to the Cvrle Asylum website</b></h1>
        <h6>
          Here you can find information about the animals in our shelter, as well as about lost animals
        </h6>
        <hr>
      </div>
      <br>
      <h3>Latest ads about lost pets:</h3>
      <div v-for="oglas in najnovijioglasi" :key="oglas.id" style="width: 60%; margin-left: 20%; font-weight:bold; font-size:20px;" id="pozadina">
        <h4 style="font-size: 30px; font-weight: bold;">{{oglas.imeLjubimca}}</h4>
        <hr>
        <p>{{oglas.opis}}</p>
        <hr>
        <b>{{oglas.kontakt}} - {{oglas.username}}</b>&ensp;&ensp;&ensp;&ensp;
        <button @click="idiNaOglas(oglas.id)" class="myButton">Pređi na stranicu oglasa</button>
        <hr>
      </div>
      <br>
      <div v-if="najnovijioglasi.length==0">
        <h6>No :)</h6>
      </div>
    </div>
  </div>
</template>

<style scoped>

  .textual{
      word-wrap: break-word;
  }

  .myButton{
      border-radius: 10px;
      background-color: darkgreen;
      height: 40px;
      color: white;
  }
</style>

<script>
export default {
  name: 'Home',
  data(){
    return{
      najnovijioglasi: [],
      jezik: ''
    }
  },
  created(){
    if(localStorage.getItem('korisnici') == null){
      var korisnici = [
        {
          username: 'mihailo',
          password: '123',
        },{
          username: 'vukasin',
          password: '123',
        },{
          username: 'admin',
          password: '123',
        }
      ];
      localStorage.setItem('korisnici', JSON.stringify(korisnici));
      document.title = "Azil Cvrle"
    }
    if(localStorage.getItem('oglasi') == null){
      var oglasi = []
      localStorage.setItem('oglasi', JSON.stringify(oglasi))
      this.najnovijioglasi = []
      localStorage.setItem('autoincrementpk_oglasi', '1')
    } else {
      this.najnovijioglasi = JSON.parse(localStorage.getItem('oglasi')).slice(0, 3)
    }
    if(localStorage.getItem('komentari') == null){
      var komentari = []
      localStorage.setItem('komentari', JSON.stringify(komentari));
    }

    if(localStorage.getItem("jezik")==null){
      localStorage.setItem("jezik","srpski");
      this.jezik = "srpski"
    }
    this.jezik = localStorage.getItem("jezik")

    document.title="Azil Cvrle";

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
