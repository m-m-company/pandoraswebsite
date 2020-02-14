$(document).ready(() => {
    $.ajax({
        type: 'GET',
        url: '/getFriendRequests',
        success: (data) => {
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
        }
    })
});

function acceptRequest(event) {
    console.log(event.target.id);
}

function refuseRequest(event) {

}