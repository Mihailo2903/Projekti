<template>
<div id="pozadina">
    <div>
    <ul class="breadcrumb">
        <div v-if="jezik=='srpski'">
             <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
            <li><a @click="skoci('/psi')" href="#">Psi</a></li>
            <li>{{pas.naziv}}</li>
        </div>
        <div v-if="jezik=='engleski'">
             <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
            <li><a @click="skoci('/psi')" href="#">Dogs</a></li>
            <li>{{pas.name}}</li>
        </div>    
    </ul>
   </div>
    <div id="pas1">
       <div v-if="jezik=='srpski'">
            <h1>{{pas.naziv}}</h1>
            <h3>Starost: {{pas.starost2}}</h3>
            <h3>Težina: {{pas.tezina}}</h3>
        </div>
        <div v-if="jezik=='engleski'">
            <h1>{{pas.name}}</h1>
            <h3>Age: {{pas.age}}</h3>
            <h3>Weight: {{pas.tezina}}</h3>
        </div>
        <div v-if="pas.id==1 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
            - Bole je pas rase maltezer koji bi se po ceo dan igrao. <br>
            - Jos je mali i dosta je energičan.  <br>
            - Krzno mu je jako mekano i prijatno na dodir. <br>
            - Obožava da se češka ispod brade.
        </div>
        <div v-if="pas.id==2 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
           - Vukša je vučjak koji je bio policijski pas. <br>
           - Ima odličan njuh i dosta je inteligentan. <br>
           - Vrlo je odan i vezuje se za vlasnika. <br>
           - Pažljiv je i odlično se snalazi sa decom.
        </div>
        <div v-if="pas.id==3 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
           - Boban je mops koji je još uvek mlad. <br>
           - Ima glatku i kratku dlaku i ne linja se. <br>
           - Zna da da šapu, sedne, prevrne se i mnoge druge trikove. <br>
           - Sjajno se slaže sa mačkama, tako da ako već imate mačku neće biti problema.
        </div>

        <div v-if="pas.id==1 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
            - Bole is a Maltese dog that would play all day. <br>
             - He is still small and quite energetic. <br>
             - His fur is very soft and pleasant to the touch. <br>
             - He loves to scratch his chin.
        </div>
        <div v-if="pas.id==2 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
           - Vukša is a shepherd who was a police dog. <br>
            - He has a great sense of smell and is quite intelligent. <br>
            - He is very loyal and attached to the owner. <br>
            - He is careful and gets along well with children.
        </div>
        <div v-if="pas.id==3 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
           - Boban is a pug who is still young. <br>
            - It has smooth and short hair and does not shed. <br>
            - He knows how to paw, sit down, roll over and many other tricks. <br>
            - It gets along great with cats, so if you already have a cat there will be no problems.
        </div>

        <button @click="klik()" class="look">{{naziv}}</button>
        <div :class="prikaz==true ? '':'sakrij'" >
            <div class="container-fluid ">
                <div class="row">
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="pas.slike[0]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="pas.slike[1]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="pas.slike[2]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="pas.slike[3]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="pas.slike[4]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="pas.slike[5]">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<style scoped>
    img{      
        padding: 2%;
        width: 100%;
        height: 100%;
    }

    #pas1{
        padding-bottom: 15px;
        margin-top: 15px;
    }
    .sakrij{
        display: none;
    }

    .look{
    border-radius: 10px;
    background-color: darkgreen;
    height: 50px;
    width: 200px;
    color: white;
    margin-bottom: 10px;
    margin-top: 10px;
}

.look:hover{
    background-color: darkslategray;
}

</style>


<script>
    export default{
        name: "Pas",
        data(){
            return{
                pas: "",
                slike: [],
                prikaz: true,
                naziv: "Sakrij galeriju",
                jezik: "",
            }
        },
        created(){
            let id = Number(this.$route.params.id);
            let psi = JSON.parse(localStorage.getItem("psi"));
            this.pas= psi.find(pas=>pas.id == id);
            this.slike=this.pas.slike;
            document.title= "Azil Cvrle - " + this.pas.naziv;
            if(localStorage.getItem("jezik")==null){
                localStorage.setItem("jezik","srpski");
                this.jezik="srpski";
            }
            else{
                this.jezik=localStorage.getItem("jezik");
            }

            if(this.jezik=="srpski")
                this.naziv="Sakrij galeriju";
            else
                this.naziv="Hide gallery"
        },
        methods:{
            skoci(p){
                this.$router.push(p);
            },
            klik(){
                if(this.prikaz)
                   { this.prikaz=false; 
                     if(this.jezik=="srpski")
                        this.naziv="Prikaži galeriju";
                    else
                        this.naziv="Show gallery"
                   }
                else
                   { 
                     this.prikaz=true; 
                     if(this.jezik=="srpski")
                        this.naziv="Sakrij galeriju";
                    else
                        this.naziv="Hide gallery"
                   }
            }
        }
    }

</script>


