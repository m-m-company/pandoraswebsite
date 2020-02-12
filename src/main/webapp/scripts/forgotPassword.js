$(document).ready(function () {
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("emailNotExists");
    if(c === "true"){
        showAlertModal("Email error", "The email does not exists", ICONS.alert);
    }
});