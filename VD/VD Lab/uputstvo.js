$(document).ready(function(){

localStorage.setItem("plavi","");
localStorage.setItem("crveni","");


var plaviUneo = false;
var crveniUneo = false;


$("#plavi").keypress(function(event){
    if(event.keyCode != 13)
        return;
    ime = document.getElementById("plavi").value;
    if(ime == null || ime == "")
        return;

    plaviUneo=true;
    localStorage.setItem("plavi",ime);

    if(plaviUneo && crveniUneo)
        window.location.href="asocijacije-igra.html";
});


$("#crveni").keypress(function(event){
    if(event.keyCode != 13)
        return;
        ime = document.getElementById("crveni").value;
    if(ime == null || ime == "")
        return;

    crveniUneo=true;
    localStorage.setItem("crveni",ime);

    if(plaviUneo && crveniUneo)
        window.location.href="asocijacije-igra.html";
});

});