$(document).ready(function () {
    $("#regForm").submit(function (e) {
        let pswd =$("#password");
        let cpswd = $("#confirm");
        if(pswd.val().length <8){
            showAlertModal("Password Error", "The password must at least 8 chars long", ICONS.alert);
            e.preventDefault(); //QUESTO FA FALLIRE IL SUBMIT, AL SERVER NON ARRIVA LA RICHIESTA POST!
        }
        if (pswd.val() !== cpswd.val()) {
            showAlertModal("Password Error", "The passwords does not match", ICONS.alert);
            e.preventDefault();
        }
    })
});