<template>
<div id="pozadinaKat">
    <div v-if="jezik == 'srpski'">
        <div>
    <ul class="breadcrumb">
        <li><a @click="skoci('/')" href="#">Azil Cvrle</a></li>
        <li>Mačke</li>
    </ul>
   </div>
    <h1>Mačke u azilu Cvrle</h1>

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
                <div v-for="macka in macke" :key="macka.id" class="col-sm-12 col-md-6 col-lg-4">
                    <div class="macka">
                        {{macka.naziv}} <br>
                        Težina: {{macka.tezina}} <br>
                        Starost: {{macka.starost2}} <br>
                        <img :src="macka.profilna"> <br>
                        <button class="look" @click="pogledaj(macka)">Pogledaj</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    {{greska}}
    <br>
    </div>


<div v-if="jezik == 'engleski'">
        <div>
    <ul class="breadcrumb">
        <li><a @click="skoci('/')" href="#">Asylum Cvrle</a></li>
        <li>Cats</li>
    </ul>
   </div>
    <h1>Cats in asylum Cvrle</h1>

    <div style="font-weight:bold">
        Search: <input type="text" name="nazivP" v-model="nazivP" @keyup="filter()" style="background-color: lightgreen;">
        <br>
        <input type="radio" name="pretraga" value="n" v-model="rb1" id="rbn" @select="filter()" checked> <label for="rbn">By name </label> &nbsp;
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
                <div v-for="macka in macke" :key="macka.id" class="col-sm-12 col-md-6 col-lg-4">
                    <div class="macka">
                        {{macka.name}} <br>
                        Weight: {{macka.tezina}} <br>
                        Age: {{macka.age}} <br>
                        <img :src="macka.profilna"> <br>
                        <button class="look" @click="pogledaj(macka)">View</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    {{greska}}
    <br>
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

.macka{
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
        name:"Macke",
        data(){
            return{
                macke: [],
                nazivP: "",
                rb1: "n",
                sortiranje: "n",
                greska: "",
                jezik: "",
            }
        },
        created(){            
            if(localStorage.getItem("macke")==null){
                this.macke = [
                    {
                    id: 1,
                    naziv: "Serval Sele",
                    name : "Sele the Serval",
                    tezina: "11kg",
                    starost: 3.5,
                    starost2: "3.5 godine",
                    age: "3.5 years",
                    opis: "Opis",
                    profilna: "/images/serval1.jfif",
                    slike:["/images/serval1.jfif","/images/serval2.jfif","/images/serval3.jfif"
                    ,"/images/serval4.jfif","/images/serval5.jfif","/images/serval6.jfif"],
                    },
                    {
                    id: 2,
                    naziv: "Karakal Kale",
                    name: "Kale the Caracal",
                    tezina: "13kg",
                    starost: 2.5,
                    starost2: "2.5 godine",
                    age: "2.5 years",
                    opis: "Opis",
                    profilna: "/images/karakal1.jfif",
                    slike:["/images/karakal1.jfif","/images/karakal2.jfif","/images/karakal3.jfif"
                    ,"/images/karakal4.jfif","/images/karakal5.jfif","/images/karakal6.jfif"],
                    },
                    {
                    id: 3,
                    naziv: "Ocelot Ole",
                    name: "Ole the Ocelot",
                    tezina: "12kg",
                    starost: 1.5,
                    starost2: "1.5 godina",
                    age: "1.5 years",
                    opis: "Opis",
                    profilna: "/images/ocelot1.jfif",
                    slike:["/images/ocelot1.jfif","/images/ocelot2.jfif","/images/ocelot3.jfif"
                    ,"/images/ocelot4.jfif","/images/ocelot5.jfif","/images/ocelot6.jfif"],
                    }
                    ];
                localStorage.setItem("macke",JSON.stringify(this.macke));
            }
            else
                this.macke=JSON.parse(localStorage.getItem("macke"));
            document.title= "Azil Cvrle - Mačke"

            if(localStorage.getItem("jezik")==null){
                localStorage.setItem("jezik","srpski");
                this.jezik="srpski";
            }
            else{
                this.jezik=localStorage.getItem("jezik");
            }
        },

        methods:{
            pogledaj(macka){
                this.$router.push("/macke/"+macka.id);
            },
            skoci(p){
                this.$router.push(p);
            },
            filter(){
                let pom= [];
                this.macke=JSON.parse(localStorage.getItem("macke"));
                let na = this.nazivP.toUpperCase();

                if(this.rb1=="n"){
                    if(this.jezik=="engleski"){
                        for(let i=0; i<this.macke.length;i++){
                            let st = this.macke[i].name.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.macke[i]);
                            }
                        }               
                    }
                    else{
                        for(let i=0; i<this.macke.length;i++){
                        let st = this.macke[i].naziv.toUpperCase();                  
                        if(st.includes(na)){
                            pom.push(this.macke[i]);
                        }
                    }
                    }
                }
                else{
                    if(this.jezik=="engleski"){
                        for(let i=0; i<this.macke.length;i++){
                            let st = this.macke[i].age.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.macke[i]);
                            }
                        }
                    }
                    else{
                        for(let i=0; i<this.macke.length;i++){
                            let st = this.macke[i].starost2.toUpperCase();                  
                            if(st.includes(na)){
                                pom.push(this.macke[i]);
                            }
                        }
                    }
                }
                this.macke=pom;
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
                    this.macke.sort(this.komparatorNaziv);
                }
                else{
                    this.macke.sort(this.komparatorStarost);
                }
            }

        }
        
    }

</script>