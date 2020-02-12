$(document).ready(function () {
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("captcha");
    if(c === "false"){
        showAlertModal("Captcha Error", "There's an error with captcha, if you fail it 3 times you will " +
            "be redirected", ICONS.alert)
    }
});
