<template>
<div id="pozadina">
    <div>
    <ul class="breadcrumb">
        <div v-if="jezik=='srpski'">
             <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
            <li><a @click="skoci('/ptice')" href="#">Ptice</a></li>
            <li>{{ptica.naziv}}</li>
        </div>
        <div v-if="jezik=='engleski'">
             <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
            <li><a @click="skoci('/ptice')" href="#">Birds</a></li>
            <li>{{ptica.name}}</li>
        </div>    
    </ul>
   </div>
    <div id="ptica1">
        <div v-if="jezik=='srpski'">
            <h1>{{ptica.naziv}}</h1>
            <h3>Starost: {{ptica.starost2}}</h3>
            <h3>Težina: {{ptica.tezina}}</h3>
        </div>
        <div v-if="jezik=='engleski'">
            <h1>{{ptica.name}}</h1>
            <h3>Age: {{ptica.age}}</h3>
            <h3>Weight: {{ptica.tezina}}</h3>
        </div>
        <div v-if="ptica.id==1 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
            - Tule je mali tukan iz Amazonske prašume i jedan je od najposlušnijih stanovnika našeg azila. <br>
            - Obožava da češka ljude svojim velikim narandžastim kljunom. <br>
            - Odlično se snalazi u prostoru i možete ga slobodno pustiti da leti napolju, jer će se uvek vratiti.<br>
            - Veoma je tih za razliku od nekih drugih ptica, i odličan je ako volite mir i tišinu.
        </div>
        <div v-if="ptica.id==2 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
           - Cvrle i Ceca su tigrice koje su od rođenja zajedno. <br>
           - Njihova ljubav je toliko jaka da ne bi podneli da se razdvoje i moraju se uzeti u paru. <br>
           - Mogu da nauče par stotina reči kao i da pevaju i igraju uz neke pesme. <br>
           - Najbolje ih je ne puštati da lete van kuće jer bi mogli da se izgube.
        </div>
        <div v-if="ptica.id==3 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
           - Tara je odrasla ženka plave are koja je kod nas oko 6 meseci. <br>
           - Jedna je od najpopulrnijih vrsta papagaja i svako bi poželeo da je ima.<br>
           - Obožava da ide u šetnje sa vlasnikom i da mu pritom stoji na ruci. <br>
           - Može čak i leteti pored Vas dok šetate, vrlo je pametna i neće odleteti.
        </div>

        <div v-if="ptica.id==1 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
           - Tule is a small toucan from the Amazon rainforest and he is one of the most obedient inhabitants of our asylum. <br>
             - He loves to tickle people with his big orange beak. <br>
             - He manages well in space and you are free to let him fly outside, because he will always come back. <br>
             - He is very quiet unlike some other birds, and he is great if you like peace and quiet.
        </div>
        <div v-if="ptica.id==2 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
           - Cvrle and Ceca are budgies who have been together since birth. <br>
            - Their love is so strong that they could not bear to separate and you will need to take both of them. <br>
            - They can learn a few hundred words as well as sing and dance with some songs. <br>
            - It is best not to let them fly out of the house because they could get lost.
        </div>
        <div v-if="ptica.id==3 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
           - Tara is an adult female of the blue macaw who has been with us for about 6 months. <br>
            - It is one of the most popular species of parrots and everyone would like to have it. <br>
            - She loves to go for walks with the owner and stand on his arm. <br>
            - She can even fly next to you while you are walking, she is very smart and will not fly away.
        </div>

        <button @click="klik()" class="look">{{naziv}}</button>
        <div :class="prikaz==true ? '':'sakrij'" >
            <div class="container-fluid ">
                <div class="row">
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="ptica.slike[0]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="ptica.slike[1]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="ptica.slike[2]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="ptica.slike[3]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="ptica.slike[4]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="ptica.slike[5]">
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

    #ptica1{
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
        name: "Ptica",
        data(){
            return{
                ptica: "",
                slike: [],
                prikaz: true,
                naziv: "Sakrij galeriju",
                jezik: "",

            }
        },
        created(){
            let id = Number(this.$route.params.id);
            let ptice = JSON.parse(localStorage.getItem("ptice"));
            this.ptica= ptice.find(ptica=>ptica.id == id);
            this.slike=this.ptica.slike;
            document.title= "Azil Cvrle - " + this.ptica.naziv;
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


