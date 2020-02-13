$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/getFriends",
        success: function (friends) {
            let friendsContainer = document.getElementById("container-friends");
            friends.map(function (friend) {
                let img;
                if (friend.googleUser){
                    img = "jsp:include page=\"printGoogleImage?id="+friend.id+"&class=rounded-circle\""
                }
                else {
                    img = ""
                }
                friendsContainer.append("<div class=\"col-md-6 col-lg-4 item\">\n" +
                    "                    <div class=\"box\"><img class=\"rounded-circle\" src=\"\">\n" +
                    "                        <h3 class=\"name\">Ben Johnson</h3>\n" +
                    "                        <p class=\"description\">Aenean tortor est, vulputate quis leo in, vehicula rhoncus lacus. Praesent aliquam in tellus eu gravida. Aliquam varius finibus est, et interdum justo suscipit id. Etiam dictum feugiat tellus, a semper massa. </p>\n" +
                    "                        <div class=\"social\"></div>\n" +
                    "                    </div>\n" +
                    "                </div>")
            })
        },
        error: function () {
            //TODO: Modal
            alert("Impossibile caricare gli amici. Riprova più tardi");
        }
    });
})