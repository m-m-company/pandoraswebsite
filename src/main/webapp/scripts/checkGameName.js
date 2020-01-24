$(document).ready(function () {
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("gameNameAlreadyExists");
    if(c === "true"){
        alert("IL NOME DEL GIOCO E' GIA' STATO UTILIZZATO");
    }
});