$(document).ready(function () {
    $("#btnChangeUsername").click(function () {
        $("#inputUsername").attr("readonly", false);
        $("#saveCancel").show();
    });
    $("#btnChangeEmail").click(function () {
        $("#inputEmail").attr("readonly", false);
        $("#saveCancel").show();
    });
    $("#btnChangePassword").click(function () {
        $("#saveCancel").show();
        $("#inputPassword").attr("readonly", false);
    });
    $("#btnChangeDescription").click(function () {
        $("#saveCancel").show();
        $("#inputDescription").attr("readonly", false);
    });
});