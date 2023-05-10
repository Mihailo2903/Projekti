<template>
<div id="pozadina">
    <div>
    <ul class="breadcrumb">
        <div v-if="jezik=='srpski'">
             <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
            <li><a @click="skoci('/macke')" href="#">Mačke</a></li>
            <li>{{macka.naziv}}</li>
        </div>
        <div v-if="jezik=='engleski'">
             <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
            <li><a @click="skoci('/macke')" href="#">Cats</a></li>
            <li>{{macka.name}}</li>
        </div>    
    </ul>
   </div>
    <div id="macka1">
        <div v-if="jezik=='srpski'">
            <h1>{{macka.naziv}}</h1>
            <h3>Starost: {{macka.starost2}}</h3>
            <h3>Težina: {{macka.tezina}}</h3>
        </div>
        <div v-if="jezik=='engleski'">
            <h1>{{macka.name}}</h1>
            <h3>Age: {{macka.age}}</h3>
            <h3>Weight: {{macka.tezina}}</h3>
        </div>
        <div v-if="macka.id==1 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
            - Sele je serval koji važi za jednu od najlepših vrsta mačaka. <br>
            - Lovokradice su ga ulovile u Sudanu i pokušali da ga prevezu preko Balkana, ali su u tome sprečeni.<br>
            - Iako deluje umiljato, u stanju je da čoveku nanese ozbiljne povrede (ali Sele je mnogo dobar pa to neće učiniti). <br>
            - Izuzetno je pametan i čuvaće vlasnika po svaku cenu. Bolji je od većine pasa što se tiče ovoga.
        </div>
        <div v-if="macka.id==1 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
            - Sele is a serval that is considered one of the most beautiful types of cats. <br>
             - Poachers caught him in Sudan and tried to transport him across the Balkans, but were prevented from doing so. <br>
             - Although he seems kind, he is able to inflict serious injuries on a person (but Sele is very good, so he won't do that). <br>
             - He is extremely smart and will protect the owner at any cost. He is better than most dogs in this regard.
        </div>
        <div v-if="macka.id==2 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
           - Kale je mladi karakal koji obožava da se mazi. <br>
           - Njegova domovina je Kenija, spašen je od švercera koji su probali da ga ilegalno prodaju. <br>
           - Vrlo je spretan i može da skoči do 3m u vis. <br>
           - Pomalo je bojažljiv zbog tužne prošlosti i treba mu neko da mu pruži bezuslovnu ljubav.
        </div>
        <div v-if="macka.id==2 && jezik=='engleski' " style="font-weight:bold; font-size:20px;">
          - Kale is a young caracal who loves to cuddle. <br>
            - His homeland is Kenya, he was saved from smugglers who tried to sell him illegally. <br>
            - He is very agile and can jump up to 3m in height. <br>
            - He is a bit timid because of his sad past and he needs someone to give him unconditional love.
        </div>
        <div v-if="macka.id==3 && jezik=='srpski'" style="font-weight:bold; font-size:20px;">
           - Ole je ocelot poreklom iz Brazila. <br>
           - Kao i ostale mačke u našem azilu, spašen je od ilegalne prodaje i dodeljeno mu je utočište kod nas.<br>
           - Voli da provodi vreme na drveću i vrlo je vešt u lovljenju golubova, tako da više nećete imati problema sa njima. <br>
           - Ako ste ikada poželeli da imate jaguara, ali je previše zahtevan za održavanje, Ole je najbolja alternativa i pravi izbor za vas.
        </div>
         <div v-if="macka.id==3 && jezik=='engleski'" style="font-weight:bold; font-size:20px;">
           - Ole is an ocelot originally from Brazil. <br>
            Like the other cats in our shelter, he was rescued from illegal sale and given refuge with us. <br>
            - He likes to spend time in the trees and is very skilled in catching pigeons, so you will no longer have problems with them. <br>
            - If you have ever wanted to have a jaguar, but it is too demanding to maintain, Ole is the best alternative and the right choice for you.
        </div>

        <button @click="klik()" class="look">{{naziv}}</button>
        <div :class="prikaz==true ? '':'sakrij'" >
            <div class="container-fluid ">
                <div class="row">
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="macka.slike[0]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="macka.slike[1]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="macka.slike[2]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="macka.slike[3]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="macka.slike[4]">
                    </div>
                    <div class="col-sm-12 col-md-6 col-lg-4">
                        <img :src="macka.slike[5]">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<style scoped>

    #pozadina{
        background-color: lightgreen;
    }

    img{      
        padding: 2%;
        width: 100%;
        height: 100%;
    }

    #macka1{
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
        name: "Macka",
        data(){
            return{
                macka: "",
                slike: [],
                prikaz: true,
                naziv: "Sakrij galeriju",
                jezik: "",
            }
        },
        created(){
            let id = Number(this.$route.params.id);
            let macke = JSON.parse(localStorage.getItem("macke"));
            this.macka= macke.find(macka=>macka.id == id);
            this.slike=this.macka.slike;
            document.title= "Azil Cvrle - " + this.macka.naziv;
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


