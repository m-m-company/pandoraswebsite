let friends = [];
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/getFriends",
        success: function(users){
            users.map((u) => friends.push(u.id));
            populateContainer(users);
        },
        error: function () {
            //TODO: Modal
            alert("Impossibile caricare gli amici. Riprova più tardi");
        }
    });
});

function searchUsers(event) {
    event.preventDefault();
    $.ajax({
        type : "POST",
        url: "/friends",
        data: {
            username: $("#usernameSearched").val()
        },
        success: function (users) {
            populateContainer(users);
        },
        error: function () {
            //TODO: da rifare
            alert("Nessun amico trovato");
        }
    })
}

function populateContainer(users) {
    let usersContainer = document.getElementById("container-users");
    $(usersContainer).empty();
    users.map(function (user) {
        let img;
        if (user.googleUser){
            $.ajax({
                type: "GET",
                url: "printGoogleImage",
                data: {
                    id: user.id,
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
            if (user.image){
                img = "<img class=\"rounded-circle\" src=\"/printImage?id="+user.id+"\" width='70%' height='70%'>"
            }
            else{
                img = "<img class=\"rounded-circle\" src=\"https://www.gravatar.com/avatar/1234566?size=200&d=mm\">"
            }
        }
        let btnClass = "btn btn-primary";
        let f = "sendFriendRequest(event)";
        let icon = "fas fa-user-plus";
        if (friends.includes(user.id)){
            btnClass = "btn btn-danger";
            f = "deleteFriend(event)";
            icon = "fas fa-user-minus";
        }
        $(usersContainer).append("<div class=\"col-md-6 col-lg-4 item\">\n" +
            "                    <div class=\"box\">"+img+"\n" +
            "                        <h3 class=\"name\"><a href='profile?id="+user.id+"'>"+user.username+"</a></h3>\n" +
            "                        <p class=\"description\">"+user.description+"</p>\n" +
            "                        <div class=\"social\"><button type='button' id='"+user.id+"'" +
            "                           class='"+btnClass+"' onclick='"+f+"'>" +
            "                           <i class='"+icon+"'></i></button></div>\n" +
            "                    </div>\n" +
            "                </div>")
    });
}

function sendFriendRequest(event) {
    let button = event.target;
    if(event.target.tagName == "I"){
        button = event.target.parentNode;
    }
    $.ajax({
        type: "POST",
        url: "/sendFriendRequest",
        data: {
            idFriend: button.id
        },
        success: function () {
            showAlertModal("SUCCESS", "The request has been send", ICONS.info);
            $(button).replaceWith("<button type='button' id='"+button.id+"'" +
                "                           class='btn btn-warning' onclick='deleteFriendRequest(event)'>" +
                "                           <i class='fas fa-user-times'></i></button>")
        },
        error: function () {
            showAlertModal("ERROR", "Impossible to add this user. Retry later", ICONS.alert)
        }
    });
}

function deleteFriendRequest(event) {
    let button = event.target;
    if(event.target.tagName == "I"){
        button = event.target.parentNode;
    }
    $.ajax({
       type: "POST",
       url: "/deleteFriendRequest",
       data: {
           idFriend: button.id
       },
       success: function () {
           showAlertModal("SUCCESS", "The request has been deleted", ICONS.info);
           $(button).replaceWith("<button type='button' id='"+button.id+"'" +
               "                           class='btn btn-primary' onclick='sendFriendRequest(event)'>" +
               "                           <i class='fas fa-user-plus'></i></button>")
       },
       error: function () {
           showAlertModal("ERROR", "Impossible to delete this request. Retry later", ICONS.alert);
       }
    });
}

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
            friends.splice(friends.indexOf(button.id), 1);
        },
        error: function () {
            showAlertModal("ERROR", "Impossible to delete this friend. Retry later", ICONS.alert)
        }
    });
}