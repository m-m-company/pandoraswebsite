$(document).ready(()=>{
    if(Cookies.get("logged") === "false" || Cookies.get("logged") === undefined){
        $("#addGameLink").attr("href", "#Login");
        $("#addGameLink").attr("data-toggle", "modal");
        $("#libraryLink").attr("href", "#Login");
        $("#libraryLink").attr("data-toggle", "modal");
        $("#profileLink").attr("href", "#Login");
        $("#profileLink").attr("data-toggle", "modal");
    }
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("registered");
    if(c === "true"){
        alert("REGISTRAZIONE EFFETTUATA");
    }
    //TODO : testami
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
            console.log("ciao");
            $.ajax({
               type: "POST",
               url: "/login",
               data: {
                   email: googleUser.getBasicProfile().getEmail()
               },
               success: function () {
                    window.location.replace("/");
               },
               error: function () {
                    alert("Something has gone wrong with google login");
               }
            });
        },
        error: function () {
            alert("NOPE");
            //TODO: da gestire se l'email è già presente nel database
        }
    });
}

