<template>
<div id="pozadinaKat">
    <div v-if="jezik=='srpski'">
        <div>
        <ul class="breadcrumb">
            <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
            <li>Psi</li>
        </ul>
        </div>
        <h1>Psi u azilu Cvrle</h1>

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
                    <div v-for="pas in psi" :key="pas.id" class="col-sm-12 col-md-6 col-lg-4">
                        <div class="pas">
                            {{pas.naziv}} <br>
                            Težina: {{pas.tezina}} <br>
                            Starost: {{pas.starost2}} <br>
                            <img :src="pas.profilna"> <br>
                            <button class="look" @click="pogledaj(pas)">Pogledaj</button>
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
            <li>Dogs</li>
        </ul>
        </div>
        <h1>Dogs in asylum Cvrle</h1>

        <div style="font-weight:bold">
            Search: <input type="text" name="nazivP" v-model="nazivP" @keyup="filter()" style="background-color: lightgreen;">
            <br>
            <input type="radio" name="pretraga" value="n" v-model="rb1" id="rbn" @select="filter()" checked> <label for="rbn">By name </label> &nbsp;
            <input type="radio" name="pretraga" value="g" v-model="rb1" id="rbg" @select="filter()"> <label for="rbg">By age</label> <br>

            Sort: <select name="select" v-model="sortiranje" class="sortiranje" style="background-color: lightgreen;">
                <option value="n">By name </option>
                <option value="g">By age</option>
                
            </select> <br>
            <button @click="sort()" class="sort">Sort</button>
        </div>

        <div id="ptice">
            <div class="container">
                <div class="row">
                    <div v-for="pas in psi" :key="pas.id" class="col-sm-12 col-md-6 col-lg-4">
                        <div class="pas">
                            {{pas.name}} <br>
                            Weight: {{pas.tezina}} <br>
                            Age: {{pas.age}} <br>
                            <img :src="pas.profilna"> <br>
                            <button class="look" @click="pogledaj(pas)">View</button>
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

.look:hover{
    background-color: darkslategray;
}

.pas{
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
        name:"Psi",
        data(){
            return{
                psi: [],
                nazivP: "",
                rb1: "n",
                sortiranje: "n",
                greska: "",
                jezik: "",
            }
        },
        created(){            
            if(localStorage.getItem("psi")==null){
                this.psi = [
                    {
                    id: 1,
                    naziv: "Maltezer Bole",
                    name: "Bole the Maltese",
                    tezina: "3.5 kg",
                    starost: 2,
                    starost2: "2 godine",
                    age: "2 years",
                    opis: "Opis",
                    profilna: "/images/malt1.jfif",
                    slike:["/images/malt1.jfif","/images/malt2.jfif","/images/malt3.jfif"
                    ,"/images/malt4.jfif","/images/malt5.jfif","/images/malt6.jfif"],
                    },
                    {
                    id: 2,
                    naziv: "Vučjak Vukša",
                    name: "Vukša the Shepherd",
                    tezina: "28kg",
                    starost: 3,
                    starost2: "3 godine",
                    age: "3 years",
                    opis: "Opis",
                    profilna: "/images/vucjak1.jfif",
                    slike:["/images/vucjak1.jfif","/images/vucjak2.jfif","/images/vucjak3.jfif"
                    ,"/images/vucjak4.jfif","/images/vucjak5.jfif","/images/vucjak6.jfif"],
                    },
                    {
                    id: 3,
                    naziv: "Mops Boban",
                    name: "Boban the Pug",
                    tezina: "3kg",
                    starost: 2.5,
                    starost2: "2.5 godine",
                    age: "2.5 years",
                    opis: "Opis",
                    profilna: "/images/mops1.jfif",
                    slike:["/images/mops1.jfif","/images/mops2.jfif","/images/mops3.jfif"
                    ,"/images/mops4.jfif","/images/mops5.jfif","/images/mops6.jfif"],
                    },
                    ];
                localStorage.setItem("psi",JSON.stringify(this.psi));
            }
            else
                this.psi=JSON.parse(localStorage.getItem("psi"));

            document.title= "Azil Cvrle - Psi"

            if(localStorage.getItem("jezik")==null){
                localStorage.setItem("jezik","srpski");
                this.jezik="srpski";
            }
            else{
                this.jezik=localStorage.getItem("jezik");
            }
        },

        methods:{
            pogledaj(pas){
                this.$router.push("/psi/"+pas.id);
            },
            skoci(p){
                this.$router.push(p);
            },
            filter(){
                let pom= [];
                this.psi=JSON.parse(localStorage.getItem("psi"));
                let na = this.nazivP.toUpperCase();

                if(this.rb1=="n"){
                    if(this.jezik=="engleski"){
                        for(let i=0; i<this.psi.length;i++){
                            let st = this.psi[i].name.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.psi[i]);
                            }
                        }               
                    }
                    else{
                        for(let i=0; i<this.psi.length;i++){
                        let st = this.psi[i].naziv.toUpperCase();                  
                        if(st.includes(na)){
                            pom.push(this.psi[i]);
                        }
                    }
                    }
                }
                else{
                    if(this.jezik=="engleski"){
                        for(let i=0; i<this.psi.length;i++){
                            let st = this.psi[i].age.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.psi[i]);
                            }
                        }
                    }
                    else{
                        for(let i=0; i<this.psi.length;i++){
                            let st = this.psi[i].starost2.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.psi[i]);
                            }
                        }
                    }
                }
                this.psi=pom;
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
                    this.psi.sort(this.komparatorNaziv);
                }
                else{
                    this.psi.sort(this.komparatorStarost);
                }
            }
        }
    }

</script>