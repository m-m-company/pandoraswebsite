$(document).ready(()=>{
    if(Cookies.get("logged") === "false" || Cookies.get("logged") === undefined){
        $("#addGameLink").attr("href", "#Login");
        $("#addGameLink").attr("data-toggle", "modal");
        $("#libraryLink").attr("href", "#Login");
        $("#libraryLink").attr("data-toggle", "modal");
        $("#profileLink").attr("href", "#Login");
        $("#profileLink").attr("data-toggle", "modal");
    }

    $("#loginBtn").click((event)=>{
        $.ajax({
            type: "POST",
            url: "/login",
            data: {
                email: $("#logEmail").val(),
                password: $("#logPassword").val()
            },
            success: function () {
                $("#errorLabel").hide();
                window.location.replace(window.location);
            },
            error: function () {
                $("#errorLabel").show();
            }
        })
    })
});

function onLoad() {
    gapi.load('auth2', function() {
        gapi.auth2.init();
    });
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
}

function googleSignIn(googleUser) {
    $.ajax({
        type: "POST",
        url: "/googleLogin",
        data: {
            token: googleUser.getAuthResponse().id_token
        },
        success: function () {
            $.ajax({
               type: "POST",
               url: "/login",
               data: {
                   email: googleUser.getBasicProfile().getEmail()
               },
               success: function () {
                    window.location.replace("/");
               }
            });
        },
        error: function () {
            showAlertModal("Email already exists", "This email has been already used for sign in", ICONS.info);
            signOut();
        }
    });
}

