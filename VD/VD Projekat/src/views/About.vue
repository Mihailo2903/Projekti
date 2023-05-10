<template>
  <div id="pozadinaKat">
    <div>
        <ul class="breadcrumb" v-if="jezik=='srpski'">
            <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
            <li>O nama</li>
        </ul>
        <ul class="breadcrumb" v-else>
            <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
            <li>About us</li>
        </ul>
    </div>
    <br>

    <div class="textual" style="padding-bottom: 0;" id="pozadina">
      <h1 style="font-size: 40px; font-weight: bold;" v-if="jezik=='srpski'">O nama</h1>
      <h1 style="font-size: 40px; font-weight: bold;" v-else>About us</h1>
      <p v-if="jezik=='srpski'">
        Mi smo azil posvećen pomaganju životinja, preko njihovog udomljavanja i pronalaženja
        <br>
        Godinama unazad imamo srećne mušterije koje su prezadovoljne udomljenim životinjama
        <br>
        Na internacionalnom takmičenju INTASSY najboljih azila smo osvojili prvo mesto
      </p>
      <p v-else>
        We are an asylum dedicated to helping animals, through their adoption and finding
        <br>
        For years, we have had happy customers who are very satisfied with the adopted animals
        <br>
        We won first place at the international competition INTASSY of the best asylums
      </p>
      <hr>
    </div>
    <br>
    <div style="font-weight:bold; font-size:20px;" id="pozadina">

      <h3 v-if="jezik=='srpski'">Neke od prethodno udomljenih životinja:</h3>
      <h3 v-else>Some of the previously adopted animals:</h3>
      <div class="container-fluid ">
        <div class="row">

            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/puma'+'.jpg'">
            </div>
            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/goldenretriever'+'.jpg'">
            </div>
            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/persiancar'+'.jpg'">
            </div>

            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/russiancat'+'.jpg'">
            </div>
            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/sovic'+'.jpg'">
            </div>
            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/africangrey'+'.jpg'">
            </div>

            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/dachshund'+'.jpg'">
            </div>
            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/littlealexander'+'.jpg'">
            </div>
            <div class="col-sm-12 col-md-6 col-lg-4">
                <img :src="'images/quackers'+'.jpg'">
            </div>

          </div>
          <br>
        </div>

        <div>
          <div style="font-size: 30px;" v-if="jezik=='srpski'">Kontakt:</div>
          <div style="font-size: 30px;" v-else>Contact:</div>
          <div v-if="jezik=='srpski'">Telefon: +381 12 3456 789</div>
          <div v-else>Telephone: +381 12 3456 789</div>
          <br>
          <div>
            <div style="font-size: 30px;" v-if="jezik=='srpski'">Lokacija:</div>
            <div style="font-size: 30px;" v-else>Location:</div>

            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5659.832266758494!2d20.446478326363273!3d44.82327317620584!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x475a6549076b5057%3A0xccd2d7e871a1db5a!2z0KHRgtCw0YDQuCDQs9GA0LDQtCwg0JHQtdC-0LPRgNCw0LQ!5e0!3m2!1ssr!2srs!4v1656529573299!5m2!1ssr!2srs" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>

          </div>
        </div>
    </div>
  </div>
</template>

<style scoped>
  .textual{
      word-wrap: break-word;
  }

  img{
    height: 200px;
    widows: 250px;
    padding-bottom: 15px;
  }
</style>

<script>
export default {
  name: 'About',
  data() {
    return{
      jezik: ''
    }
  },
  created(){
    this.jezik = localStorage.getItem("jezik")
    document.title= "Azil Cvrle - O nama"
  },
  methods:{
    skoci(p){
        this.$router.push(p);
    },
  }
}
</script>
