<template>

<div>
    <div v-if="jezik == 'srpski'">
        <nav class="navbar navbar-expand-lg navbar-dark poz">
        <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <div>
                <img src="/images/bruh2.png" class="logo">
            </div>
        </a>
        <!-- <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav"  aria-expanded="false" aria-label="Toggle navigation">
        </button> -->
        <div class="navbar-collapse" id="main_nav">
            <ul class="navbar-nav ">

                <li class="nav-item active fontt"><a class="nav-link fontt " @click="skoci('/')"> Početna &nbsp; </a></li>
                

                <li class="nav-item fontt" style="float:left !important;"><a class="nav-link fontt" @click="skoci('/about')"> O nama &nbsp; </a></li>
                

                <li class="nav-item dropdown fontt">
                <a class="nav-link dropdown-toggle fontt" href="#" data-bs-toggle="dropdown"> Životinje </a>
                    <ul class="dropdown-menu naran1">
                    <li><a class="dropdown-item naran menuitem"  @click="skoci('/psi')"> Psi </a></li>
                    <li><a class="dropdown-item naran menuitem"  @click="skoci('/macke')"> Mačke </a></li>
                    <li><a class="dropdown-item naran menuitem"  @click="skoci('/ptice')"> Ptice </a></li>
                    </ul>
                </li>

                
                <li class="nav-item fontt"><a class="nav-link fontt"  @click="skoci('/izgubljeni')"> Izgubljeni ljubimci &nbsp;</a></li>

                
                <li class="nav-item fontt"><a class="nav-link fontt" @click="skoci('/dodaj')"> Dodaj oglas &nbsp;  </a></li>

            
                <li class="nav-item fontt"><a class="nav-link fontt" @click="skoci('/nalog')"> Moj nalog &nbsp;</a></li>
                <a class="navbar-brand" href="#">
                    <div>
                        <img src="/images/engleski.png" class="logo" @click="changeLanguage('engleski')">
                    </div>
                </a>
                <a class="navbar-brand" href="#">
                    <div>
                        <img src="/images/srpski2.png" class="logo"  @click="changeLanguage('srpski')">
                    </div>
                </a>                      
            </ul>
        </div> <!-- navbar-collapse.// -->
        </div> <!-- container-fluid.// -->
        </nav>
    </div>

    <div v-if="jezik=='engleski'">
        <nav class="navbar navbar-expand-lg navbar-dark poz">
        <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <div>
                <img src="/images/bruh2.png" class="logo">
            </div>
        </a>
        <!-- <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav"  aria-expanded="false" aria-label="Toggle navigation">
        </button> -->
        <div class="navbar-collapse" id="main_nav">
            <ul class="navbar-nav ">

                <li class="nav-item active fontt"><a class="nav-link fontt " @click="skoci('/')"> Home &nbsp; </a></li>
                

                <li class="nav-item fontt" style="float:left !important;"><a class="nav-link fontt" @click="skoci('/about')"> About us &nbsp; </a></li>
                

                <li class="nav-item dropdown fontt">
                <a class="nav-link dropdown-toggle fontt" href="#" data-bs-toggle="dropdown"> Animals </a>
                    <ul class="dropdown-menu naran1">
                    <li><a class="dropdown-item naran menuitem"  @click="skoci('/psi')"> Dogs </a></li>
                    <li><a class="dropdown-item naran menuitem"  @click="skoci('/macke')"> Cats </a></li>
                    <li><a class="dropdown-item naran menuitem"  @click="skoci('/ptice')"> Birds </a></li>
                    </ul>
                </li>

                
                <li class="nav-item fontt"><a class="nav-link fontt"  @click="skoci('/izgubljeni')"> Lost pets &nbsp;</a></li>

                
                <li class="nav-item fontt"><a class="nav-link fontt" @click="skoci('/dodaj')"> Add an ad &nbsp;  </a></li>

            
                <li class="nav-item fontt"><a class="nav-link fontt" @click="skoci('/nalog')"> My account &nbsp;</a></li>
                <a class="navbar-brand" href="#">
                    <div>
                        <img src="/images/engleski.png" class="logo" @click="changeLanguage('engleski')">
                    </div>
                </a>
                <a class="navbar-brand" href="#">
                    <div>
                        <img src="/images/srpski2.png" class="logo"  @click="changeLanguage('srpski')">
                    </div>
                </a>                      
            </ul>
        </div> <!-- navbar-collapse.// -->
        </div> <!-- container-fluid.// -->
        </nav>
    </div>
        
</div>
</template>

<style>

.logo{
    width: 48px;
    height: 48px;
}

.menuitem{
    color:greenyellow;
    font-size: 20px;
}

.navbar-collapse{
    color: aqua;
}

.naran, .naran1{
    background-color: darkslategrey;
}

.naran:hover{
    background-color: greenyellow;
}

.fontt{
    font-size: 26px;
    font-weight: bold;
    color: white !important;
}

.fontt:hover{
    background-color:rgba(75, 75, 75, 0.8);
}

.fonttt{
    font-size: 36px;
}

.poz{
    background-image: url("/images/bg.jpg");
    background-repeat: repeat;
}

@media all and (min-width: 92px) {
	.navbar .nav-item .dropdown-menu{ display: none; }

	.navbar .nav-item:hover .dropdown-menu{ display: block; }
	.navbar .nav-item .dropdown-menu{ margin-top:0; }
} 

</style>

<script>
    export default{
        name:"Menu",
        data(){
            return{
                jezik: "",
            }
        },
        methods:{
            skoci(path){
                this.$router.push(path);
                this.$forceUpdate();
            },
            changeLanguage(p){
                this.jezik=p;
                localStorage.setItem("jezik",p);
                window.location.reload();
            }
        },
        created(){
            if(localStorage.getItem("jezik")==null){
                localStorage.setItem("jezik","srpski");
                this.jezik="srpski";
            }
            else{
                this.jezik=localStorage.getItem("jezik");
            }
        }
    }
</script>
