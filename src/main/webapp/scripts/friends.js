$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/getFriends",
        success: function (friends) {
            let friendsContainer = document.getElementById("container-friends");
            console.log(friendsContainer);
            friends.map(function (friend) {
                let img;
                if (friend.googleUser){
                    $.ajax({
                        type: "GET",
                        url: "printGoogleImage",
                        data: {
                            id: friend.id,
                            class: "rounded-circle",
                            width: "70%",
                            height: "70%"
                        },
                        success: function (text) {
                            img = text;
                        },
                        error: function () {
                            //TODO: modal
                            alert("Impossibile caricare l'immagine. Riprova più tardi");
                        },
                        async: false
                    });
                }
                else {
                    if (friend.profile_image){
                        img = "<img class=\"rounded-circle\" src=\"/printImage?id="+friend.id+"\">"
                    }
                    else{
                        img = "<img class=\"rounded-circle\" src=\"https://www.gravatar.com/avatar/1234566?size=200&d=mm\">"
                    }
                }
                $(friendsContainer).append("<div class=\"col-md-6 col-lg-4 item\">\n" +
                    "                    <div class=\"box\">"+img+"\n" +
                    "                        <h3 class=\"name\">"+friend.username+"</h3>\n" +
                    "                        <p class=\"description\">"+friend.description+"</p>\n" +
                    "                        <div class=\"social\"><button type='button' id='"+friend.id+"'" +
                    "                           class='btn btn-danger' onclick='deleteFriend(event)'>" +
                    "                           <i class='fas fa-user-minus'></i></button></div>\n" +
                    "                    </div>\n" +
                    "                </div>")
            })
        },
        error: function () {
            //TODO: Modal
            alert("Impossibile caricare gli amici. Riprova più tardi");
        }
    });
});

function deleteFriend(event) {
    let button = event.target;
    if(event.target.tagName == "I"){
        button = event.target.parentNode;
    }
    $.ajax({
       type: "POST",
       url: "/deleteFriend",
       data: {
           idFriend: button.id
       },
       success: function () {
            $(button.parentNode.parentNode.parentNode).remove();
       },
       error: function () {
           //TODO: modal
           alert("Impossibile eliminare l'amico. Riprova più tardi");
       }
    });
}