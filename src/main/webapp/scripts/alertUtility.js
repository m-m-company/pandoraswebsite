const ICONS = Object.freeze({"alert":"fas fa-exclamation-triangle", "info":"fas fa-info-circle"});
function showAlertModal(title, text, icon) {
    $("#alertContent").text(text);
    $("#alertTitle").text(title);
    $("#alertIcon").removeClass().addClass(icon);
    $("#alertModal").modal();
}