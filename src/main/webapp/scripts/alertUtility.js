const ICONS = Object.freeze({"alert":"fas fa-exclamation-triangle", "info":"fas fa-info-circle"});
function showAlertModal(title, text, icon) {
    $("#alertContent").text(text);
    $("#alertTitle").text(title);
    $("#alertIcon").removeClass().addClass(icon);
    $("#alertModal").modal();
}

function showConfirmModal(title, text, icon, event, fun) {
    let button = event.target;
    if(event.target.tagName == "I"){
        button = event.target.parentNode;
    }
    sessionStorage.setItem("idToProcess", button.id);
    $("#confirmIcon").removeClass().addClass(icon);
    $("#confirmTitle").text(title);
    $("#confirmContent").text(text);
    document.getElementById("confirmBtn").onclick = () => fun();
    $("#confirmModal").modal();
}