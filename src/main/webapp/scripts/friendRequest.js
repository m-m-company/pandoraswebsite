$(document).ready(() => {
    $.ajax({
        type: 'GET',
        url: '/getReceivedFriendRequests',
        success: (data) => {
            $("#loading").remove();
            if (data.length === 0){
                $("#rowRequests").append("<h3 style='margin-left: 20px' class='text-center'>You don't have any request</h3>");
            }
            data.map((user) => {
                $("#rowRequests").append("<div class=\"col-12 col-sm-6 col-md-5 col-lg-3 user-item\">\n" +
                    "        <div class=\"user-container\">" +
                    "           <a class=\"user-avatar\" href=\"#\">\n" +
                    "            <img class=\"rounded-circle img-fluid\"" +
                    "                 src=\"https://www.gravatar.com/avatar/1234566?size=200&d=mm\" width=\"48\"" +
                    "                 height=\"48\"" +
                    "                 ></a>\n" +
                    "            <p class=\"user-name\"><a href=\"profile?id=" + user.id + "\">" + user.username + "</a><span>" + user.description + " </span>\n" +
                    "                <button class=\"btn btn-success\" id='" + user.id + "' type=\"button\" onclick='acceptRequest(event)' style=\"margin-left: 0px;\">Accept</button>\n" +
                    "                <button class=\"btn btn-danger\" id='" + user.id + "' type=\"button\" onclick='refuseRequest(event)'>Refuse</button>\n" +
                    "            </p>\n" +
                    "        </div>\n" +
                    "    </div>")
            });
        },
        error: function () {
            showAlertModal("ERROR", "Impossible to show the requests. Retry later", ICONS.alert);
        }
    });
    $.ajax({
        type: 'GET',
        url: '/getSentFriendRequests',
        success: (data)=>{
            if (data.length === 0){
                $("#rowSent").append("<h3 style='margin-left: 20px' class='text-center'>You don't have any request</h3>");
            }
            data.map((user) => {
                $("#rowSent").append("<div class=\"col-12 col-sm-6 col-md-5 col-lg-3 user-item\">\n" +
                    "        <div class=\"user-container\">" +
                    "           <a class=\"user-avatar\" href=\"#\">\n" +
                    "            <img class=\"rounded-circle img-fluid\"" +
                    "                 src=\"https://www.gravatar.com/avatar/1234566?size=200&d=mm\" width=\"48\"" +
                    "                 height=\"48\"" +
                    "                 ></a>\n" +
                    "            <p class=\"user-name\"><a href=\"profile?id=" + user.id + "\">" + user.username + "</a><span>" + user.description + " </span>\n" +
                    "                <button class=\"btn btn-danger\" id='" + user.id + "' type=\"button\" onclick='cancelRequest(event)' style=\"margin-left: 0px;\">Cancel friend request</button>\n" +
                    "            </p>\n" +
                    "        </div>\n" +
                    "    </div>")
            });
        }
    })
});

function acceptRequest(event) {
    $.ajax({
        type: "POST",
        url: "/acceptFriendRequest",
        data: {
            idUser: event.target.id
        },
        success: function () {
            $(event.target.parentNode.parentNode.parentNode).remove();
            showAlertModal("SUCCESS", "You have a new friend. Now you can see his profile", ICONS.info);
        },
        error: function () {
            showAlertModal("ERROR", "Impossible to add a new friend. Retry later", ICONS.alert)
        }
    });
}

function refuseRequest(event) {
    $.ajax({
       type: "POST",
       url: "/deleteFriendRequest",
       data: {
           idFriend: event.target.id,
       },
       success: function () {
           $(event.target.parentNode.parentNode.parentNode).remove();
           showAlertModal("SUCCESS", "You have refused this request", ICONS.info)
       },
       error: function () {
           showAlertModal("ERROR", "Impossible to refuse this request. Retry later", ICONS.alert)
       }
    });
}

function cancelRequest(event) {
    $.ajax({
        type: 'POST',
        url: '/deleteFriendRequest',
        data:{
            idFriend: event.target.id
        },
        success: function () {
            $(event.target.parentNode.parentNode.parentNode).remove();
            showAlertModal("SUCCESS", "You've cancelled the request", ICONS.info)
        },
        error: function () {
            showAlertModal("ERROR", "Impossible to cancel this request. Retry later", ICONS.alert)
        }
    })
}