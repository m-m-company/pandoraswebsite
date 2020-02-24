$(document).ready(function () {
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("gameNameAlreadyExists");
    if(c === "true"){
       showAlertModal("Error Game Name", "The game name already exists", ICONS.alert);
    }
});