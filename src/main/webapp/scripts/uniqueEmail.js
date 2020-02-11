$(document).ready(function () {
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("emailAlreadyExists");
    if(c === "true"){
        showAlertModal("Email error", "This email already exists, try to login", ICONS.alert);
    }
});
