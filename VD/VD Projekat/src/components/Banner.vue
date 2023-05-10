<template>
   <div id="pozadina">
        <div style="background-color: rgb(209, 209, 209); height: 27px;">
            <br>
        </div>
        <br>
        <div v-for="reklama in reklame" :key="reklama.id" class="textual" style="border: 13px 3px 1.5px 1.5px; border-color: greenyellow; border-style: solid;">
            <b>{{reklama.header}}</b><br>
            {{reklama.tekst}}<br>
            {{reklama.slika}}
        </div>
   </div>
</template>

<style scoped>
    .textual{
        word-wrap: break-word;
    }
</style>

<script>
export default{
        name:"Banner",
        data(){
            return{
                reklame:[]
            }
        },
        created(){
            if(localStorage.getItem('reklame') == null){
                this.reklame = [
                    {
                        id: '1',
                        header: 'REKLAMA',
                        tekst: 'REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT...',
                        slika: 'REKLAMA SLIKA...'
                    },{
                        id: '2',
                        header: 'REKLAMA',
                        tekst: 'REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT...',
                        slika: 'REKLAMA SLIKA...'
                    },{
                        id: '3',
                        header: 'REKLAMA',
                        tekst: 'REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT...',
                        slika: 'REKLAMA SLIKA...'
                    },{
                        id: '4',
                        header: 'REKLAMA',
                        tekst: 'REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT REKLAMA TEXT...',
                        slika: 'REKLAMA SLIKA...'
                    }
                ]
                localStorage.setItem('reklame', JSON.stringify(this.reklame))
            } else {
                this.reklame = JSON.parse(localStorage.getItem('reklame'))
            }
        }
}
</script>