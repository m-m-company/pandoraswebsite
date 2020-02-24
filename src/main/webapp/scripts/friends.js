let friends = [];
let sentRequests = [];
let receivedRequests = [];
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/getFriends",
        success: function(users){
            users.map((u) => friends.push(u.id));
            populateContainer(users);
        },
        error: function () {
            showAlertModal("ERROR", "Failed loading friends. Retry later", ICONS.alert);
        }
    });
    $.ajax({
        type: "GET",
        url: "/getReceivedFriendRequests",
        success: function (users) {
            users.map((u) => receivedRequests.push(u.id));
        },
        error: function () {
            showAlertModal("ERROR", "Failed loading received friend request. Retry later", ICONS.alert);
        }
    });
    $.ajax({
        type: "GET",
        url: "/getSentFriendRequests",
        success: function (users) {
            users.map((u) => sentRequests.push(u.id))
        },
        error: function () {
            showAlertModal("ERROR", "Failed loading sent friend request. Retry later", ICONS.alert);
        }
    })
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
            showAlertModal("NOT FOUND", "Your search produced no results", ICONS.alert);
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
                    showAlertModal("Image error", "Impossible to load the image of "+user.username, ICONS.alert)
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
            f = "showConfirmModal(\"Danger!\", \"Are you sure to delete this friend?\", ICONS.alert, event, deleteFriend)";
            icon = "fas fa-user-minus";
        }
        if (receivedRequests.includes(user.id)){
            btnClass = "btn btn-success";
            f = "acceptFriendRequest(event)";
            icon = "fas fa-user-plus";
        }
        if (sentRequests.includes(user.id)){
            btnClass = "btn btn-warning";
            f = "showConfirmModal(\"Danger!\", \"Are you sure to cancel this request?\", ICONS.alert, event, deleteFriendRequest)";
            icon = "fas fa-user-times";
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
            sentRequests.push(button.id);
            showAlertModal("SUCCESS", "The request has been sent", ICONS.info);
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
    let userId = sessionStorage.getItem("idToProcess")
    $.ajax({
       type: "POST",
       url: "/deleteFriendRequest",
       data: {
           idFriend: userId
       },
       success: function () {
           sentRequests.splice(sentRequests.indexOf(userId), 1);
           showAlertModal("SUCCESS", "The request has been deleted", ICONS.info);
           $("#"+userId).replaceWith("<button type='button' id='"+userId+"'" +
               "                           class='btn btn-primary' onclick='sendFriendRequest(event)'>" +
               "                           <i class='fas fa-user-plus'></i></button>")
       },
       error: function () {
           showAlertModal("ERROR", "Impossible to delete this request. Retry later", ICONS.alert);
       }
    });
}

function deleteFriend(event) {
    let userId = sessionStorage.getItem("idToProcess");
    $.ajax({
        type: "POST",
        url: "/deleteFriend",
        data: {
            idFriend: userId
        },
        success: function () {
            friends.splice(friends.indexOf(userId), 1);
            showAlertModal("SUCCESS", "You have removed this friend", ICONS.info);
            $("#"+userId).replaceWith("<button type='button' id='"+userId+"'" +
                "                           class='btn btn-primary' onclick='sendFriendRequest(event)'>" +
                "                           <i class='fas fa-user-plus'></i></button>")
        },
        error: function () {
            showAlertModal("ERROR", "Impossible to delete this friend. Retry later", ICONS.alert)
        }
    });
}

function acceptFriendRequest(event) {
    let button = event.target;
    if(event.target.tagName == "I"){
        button = event.target.parentNode;
    }
    $.ajax({
       type: "POST",
       url: "/acceptFriendRequest",
       data: {
           idUser: button.id
       },
       success: function () {
           friends.push(button.id);
           showAlertModal("SUCCESS", "You have accepted this request", ICONS.info);
           $(button).replaceWith("<button type='button' id='"+button.id+"'" +
               "                           class='btn btn-danger' onclick='deleteFriend(event)'>" +
               "                           <i class='fas fa-user-minus'></i></button>")
       },
       error: function () {

       }
    });
}