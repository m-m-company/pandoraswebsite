function onLoad() {
    gapi.load('auth2', function() {
        gapi.auth2.init();
    });
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut();
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
                },
                error: function () {
                    showAlertModal("ERROR", "Something has gone wrong with google login", ICONS.alert);
                }
            });
        },
        error: function () {
            showAlertModal("ERROR", "There is an error with login from google. Retry later", ICONS.alert)
        }
    });
}