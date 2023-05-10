<template>
<div id="pozadinaKat">
    <div v-if="jezik=='srpski'">
    <div>
    <ul class="breadcrumb">
        <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
        <li>Ptice</li>
    </ul>
   </div>
    <h1>Ptice u azilu Cvrle</h1>

    <div style="font-weight:bold">
        Pretražite: <input type="text" name="nazivP" v-model="nazivP" @keyup="filter()" style="background-color: lightgreen;">
        <br>
        <input type="radio" name="pretraga" value="n" v-model="rb1" id="rbn" @select="filter()" checked> <label for="rbn">Po nazivu </label> &nbsp;
        <input type="radio" name="pretraga" value="g" v-model="rb1" id="rbg" @select="filter()"> <label for="rbg">Po starosti</label> <br>

        Sortiraj: <select name="select" v-model="sortiranje" class="sortiranje" style="background-color: lightgreen;">
            <option value="n">Po nazivu</option>
            <option value="g">Po starosti</option>
            
        </select> <br>
        <button @click="sort()" class="sort">Sortiraj</button>
    </div>

    <div id="ptice">
        <div class="container">
            <div class="row">
                <div v-for="ptica in ptice" :key="ptica.id" class="col-sm-12 col-md-6 col-lg-4">
                    <div class="ptica">
                        {{ptica.naziv}} <br>
                        Težina: {{ptica.tezina}} <br>
                        Starost: {{ptica.starost2}} <br>
                        <img :src="ptica.profilna"> <br>
                        <button class="look" @click="pogledaj(ptica)">Pogledaj</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    {{greska}}
    </div>

    <div v-if="jezik=='engleski'">
    <div>
    <ul class="breadcrumb">
        <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
        <li>Birds</li>
    </ul>
   </div>
    <h1>Birds in asylum Cvrle</h1>

    <div style="font-weight:bold">
        Search: <input type="text" name="nazivP" v-model="nazivP" @keyup="filter()" style="background-color: lightgreen;">
        <br>
        <input type="radio" name="pretraga" value="n" v-model="rb1" id="rbn" @select="filter()" checked> <label for="rbn">By name</label> &nbsp;
        <input type="radio" name="pretraga" value="g" v-model="rb1" id="rbg" @select="filter()"> <label for="rbg">By age</label> <br>

        Sort: <select name="select" v-model="sortiranje" class="sortiranje" style="background-color: lightgreen;">
            <option value="n">By name</option>
            <option value="g">By age</option>
            
        </select> <br>
        <button @click="sort()" class="sort">Sort</button>
    </div>

    <div id="ptice">
        <div class="container">
            <div class="row">
                <div v-for="ptica in ptice" :key="ptica.id" class="col-sm-12 col-md-6 col-lg-4">
                    <div class="ptica">
                        {{ptica.name}} <br>
                        Weight: {{ptica.tezina}} <br>
                        Age: {{ptica.age}} <br>
                        <img :src="ptica.profilna"> <br>
                        <button class="look" @click="pogledaj(ptica)">View</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    {{greska}}
    </div>

</div>
</template>

<style scoped>
#ptice{
    padding-bottom: 15px;
    margin-top: 15px;
    font-size: 20px;
}

.look{
    border-radius: 10px;
    background-color: darkgreen;
    height: 50px;
    width: 40%;
    color: white;
    margin-bottom: 10px;
    margin-top: 10px;
}

.sort{
    border-radius: 10px;
    background-color: darkgreen;
    height: 40px;
    width: 120px;
    color: white;
    margin-bottom: 10px;
    margin-top: 10px;
}

.look:hover, .sort:hover{
    background-color: darkslategray;
}

.ptica{
    background-color: lightgreen;
    font-weight: bold;
    text-align: center; align-content: center;
    padding-top: 10px;
}

input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus,
input:-webkit-autofill:active {
    -webkit-box-shadow: 0 0 0 30px lightgreen inset !important;
}
</style>


<script>
    export default{
        name:"Ptice",
        data(){
            return{
                ptice: [],
                nazivP: "",
                rb1: "n",
                sortiranje: "n",
                greska: "",
                jezik: "",
            }
        },
        created(){            
            if(localStorage.getItem("ptice")==null){
                this.ptice = [
                    {
                    id: 1,
                    naziv: "Tukan Tule",
                    name: "Tule the Toucan",
                    tezina: "500g",
                    starost: 2,
                    starost2: "2 godine",
                    age: "2 years",
                    opis: "Opis",
                    profilna: "/images/tukan1.jfif",
                    slike:["/images/tukan1.jfif","/images/tukan2.jfif","/images/tukan3.jfif"
                    ,"/images/tukan4.jfif","/images/tukan5.jfif","/images/tukan6.jfif"],
                    },
                    {
                    id: 2,
                    naziv: "Tigrice Ceca i Cvrle",
                    name: "Budgies Ceca and Cvrle",
                    tezina: "100g",
                    starost: 1.5,
                    starost2: "1.5 godina",
                    age: "1.5 years",
                    opis: "Opis",
                    profilna: "/images/tigrica1.jfif",
                    slike:["/images/tigrica1.jfif","/images/tigrica2.jfif","/images/tigrica3.jfif"
                    ,"/images/tigrica4.jfif","/images/tigrica5.jfif","/images/tigrica6.jfif"],
                    },
                    {
                    id: 3,
                    naziv: "Ara Tara",
                    name: "Tara the Macaw",
                    tezina: "800g",
                    starost: 4,
                    starost2: "4 godine",
                    age: "4 years",
                    opis: "Opis",
                    profilna: "/images/ara1.jfif",
                    slike:["/images/ara1.jfif","/images/ara2.jfif","/images/ara3.jfif"
                    ,"/images/ara4.jfif","/images/ara5.jfif","/images/ara6.jfif"],
                    }
                    ];
                localStorage.setItem("ptice",JSON.stringify(this.ptice));
            }
            else
                this.ptice=JSON.parse(localStorage.getItem("ptice"));

            document.title= "Azil Cvrle - Ptice"

            if(localStorage.getItem("jezik")==null){
                localStorage.setItem("jezik","srpski");
                this.jezik="srpski";
            }
            else{
                this.jezik=localStorage.getItem("jezik");
            }
        },

        methods:{
            pogledaj(ptica){
                this.$router.push("/ptice/"+ptica.id);
            },
            skoci(p){
                this.$router.push(p);
            },
            filter(){
                let pom= [];
                this.ptice=JSON.parse(localStorage.getItem("ptice"));
                let na = this.nazivP.toUpperCase();

                if(this.rb1=="n"){
                    if(this.jezik=="engleski"){
                        for(let i=0; i<this.ptice.length;i++){
                            let st = this.ptice[i].name.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.ptice[i]);
                            }
                        }               
                    }
                    else{
                        for(let i=0; i<this.ptice.length;i++){
                        let st = this.ptice[i].naziv.toUpperCase();                  
                        if(st.includes(na)){
                            pom.push(this.ptice[i]);
                        }
                    }
                    }
                }
                else{
                    if(this.jezik=="engleski"){
                        for(let i=0; i<this.ptice.length;i++){
                            let st = this.ptice[i].age.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.ptice[i]);
                            }
                        }
                    }
                    else{
                        for(let i=0; i<this.ptice.length;i++){
                            let st = this.ptice[i].starost2.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.ptice[i]);
                            }
                        }
                    }
                }
                this.ptice=pom;
            },
            komparatorNaziv(a,b){
                if (this.jezik=="srpski")
                    return (a.naziv<b.naziv?-1:(a.naziv>b.naziv?1:0)); 
                else
                    return (a.name<b.name?-1:(a.name>b.name?1:0));
            },
            komparatorStarost(a,b){
                return a.starost-b.starost;
            },
            sort(){
                if(this.sortiranje!="n" && this.sortiranje!="g")
                    return;
                else if (this.sortiranje=="n"){
                    this.ptice.sort(this.komparatorNaziv);
                }
                else{
                    this.ptice.sort(this.komparatorStarost);
                }
            }
        }
    }

</script>