let alreadyDone = false;

function resetCommentList(){
    $("#commentList").empty().append("<li class=\"list-group-item\" style=\"margin-bottom:6px;\">\n" +
        "                            <div class=\"media\">\n" +
        "                                <div class=\"media-body\">\n" +
        "                                    <div class=\"media\" style=\"overflow:visible;\">\n" +
        "                                        <div><img class=\"mr-3\" style=\"width: 25px; height:25px;\"\n" +
        "                                                  src=\"assets/logo.png\"></div>\n" +
        "                                        <div class=\"media-body\" style=\"overflow:visible;\">\n" +
        "                                            <div class=\"row\">\n" +
        "                                                <div class=\"col-md-12\">\n" +
        "                                                    <a href=\"#\">Loading...</a>\n" +
        "                                                    <div>\n" +
        "                                                        <span></span>\n" +
        "                                                        <span></span>\n" +
        "                                                        <span></span>\n" +
        "                                                        <span></span>\n" +
        "                                                        <span></span>\n" +
        "                                                    </div>\n" +
        "                                                    <p style=\"display: inline\"></p>\n" +
        "                                                </div>\n" +
        "                                            </div>\n" +
        "                                        </div>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                        </li>");


}

$(document).ready(function () {
    $("#gameDetails").hide();
    $("#addComment").click(function (event) {
        $.ajax({
            type: "POST",
            url: "/addComment",
            data: {
                content: $("#commentContent").val(),
                stars: $("#commentStars").val(),
                game: sessionStorage.getItem("gameID")
            },
            success: function () {
                resetCommentList();
                insertComments(sessionStorage.getItem("gameID"));
            }
        })
    });
    $("#download").click(function (event) {
        window.location.replace("/downloadGame?id=" + sessionStorage.getItem("gameID"));
    })
});

function onYouTubeIframeAPIReady() {
}

function showGame(event) {
    resetCommentList();
    $.ajax({
        type: "GET",
        url: "/getGameDetails",
        data: {
            name: event.target.id
        },
        success: function (game) {
            sessionStorage.setItem("gameID", game.id);
            $("#gameDetails").show();
            $("#title").text(game.name);
            $("#description").text(game.description);
            $("#specs").text(game.specifics);
            insertPreviews(game);
            insertRank(game);
            insertComments(game.id);
        },
        error: function (error) {
            showAlertModal("Generic error", "You broke something, reload the page and retry", ICONS.alert);
        }
    })
}


function insertPreviews(game) {
    $.ajax({
        type: "GET",
        url: "/getPreviewGame",
        data: {
            name: game.name
        },
        success: function (data) {
            $("#slides").empty();
            data.map(function (img, index) {
                let active = "active";
                if (index !== 0)
                    active = "";
                $("#slides").append(
                    "<div class=\"carousel-item size-div-preview " + active + "\">" +
                    "" +
                    "<img class='w-100 h-100 d-block float-left size-div-preview' src='" + img + "'></img>" +
                    "</div>"
                )
            })
        },
        error: function () {
            $("#slides").empty().append("<h1 class='text-center'> No preview images </h1>");
        }
    });
    $.ajax({
        type: "GET",
        url: "/getGameLinks",
        data: {
            gameID: game.id
        },
        success: function (data) {
            data[0].map(function (link, index) {
                $("#slides").append("<div class='carousel-item size-div-preview text-center'> <div id='player-" + index + "'></div> </div>");
                let player = new YT.Player('player-' + index, {
                    videoId: link,
                    host: 'http://www.youtube.com',
                    events: {
                        'onReady': onPlayerReady,
                        'onStateChange': onPlayerStateChange
                    }
                });

                function onPlayerReady() {
                }

                function onPlayerStateChange() {
                }
            });
            data[1].map(function (img) {
                $("#slides").append(
                    "<div class=\"carousel-item size-div-preview\">" +
                    "" +
                    "<img class='w-100 h-50 d-block float-left size-div-preview' src='" + img + "'></img>" +
                    "</div>"
                )
            })
        }
    })
}

function insertRank(game) {
    $.ajax({
        type: "GET",
        url: "/getRank",
        data: {
            id: game.id
        },
        success: function (data) {
            data.map(function (score) {
                $("#ranks").append("<li>".concat(score.value).concat(" <- ").concat(score.username).concat("</li>"));
            })
        }
    })
}

function insertComments(id) {
    $.ajax({
        type: "GET",
        url: "/getComments",
        data: {
            idGame: id
        },
        success: (data) => {
            let commentList = document.getElementById("commentList");
            let row = commentList.children[0];
            for (let i = 0; i < data.length; ++i) {
                if (commentList.children[i] === undefined) {
                    let newRow = $(row).clone();
                    $(commentList).append(newRow);
                }
                let username = commentList.children[i].children[0].children[0].children[0].children[1].children[0].children[0].children[0];
                let src = commentList.children[i].children[0].children[0].children[0].children[0].children[0];
                let rating = commentList.children[i].children[0].children[0].children[0].children[1].children[0].children[0].children[1];
                let stars = data[i].stars;
                for (let j = 1; j <= 5; ++j) {
                    rating.children[j - 1].className = "fa fa-star";
                    if (j <= stars) {
                        rating.children[j - 1].style = "color: orange;"
                    } else {
                        rating.children[j - 1].style = "";
                    }
                }
                let p = commentList.children[i].children[0].children[0].children[0].children[1].children[0].children[0].children[2];
                $(p).html(data[i].comment);
                populateComment(data[i].author, username, src, p.parentNode, data[i].id);
            }
        }
    });
}

function populateComment(idAuthor, username, src, div, idReview) {
    $.ajax({
        type: "POST",
        url: "/getUserForComment",
        data: {
            id: idAuthor
        },
        success: function (data) {
            console.log(data);
            $(username).html(data[1]);
            username.href = "profile?id=" + data[0];
            if (data[3] === "true") {
                src.src = "/printImage?id=" + data[0];
            } else if (data[3] === "false") {
                src.src = "https://www.gravatar.com/avatar/1234566?size=200&d=mm";
            } else {
                src.src = data[3];
            }
            if (data[2] === "true" && !alreadyDone) {
                $(div).append("<button type=\"button\" class=\"btn btn-dark btn-sm fa fa-edit\" id='" + idReview + "' onclick='modifyComment(event)'>\n" +
                    "                                                </button>");
                $(div).append("<button type=\"button\" class=\"btn btn-danger btn-sm fa fa-trash\" id='" + idReview + "' onclick='deleteComment(event)'>\n" +
                    "                                                </button>");
                alreadyDone = true;
            }
        },
        error: function () {
            showAlertModal("Generic error", "There's an error with comments, try later", ICONS.alert);
        },
    })
}

function modifyComment(event) {
    let p = event.target.parentNode.children[2];
    console.log(p);
    let text = $(p).html();
    $(p).replaceWith("<input type='text' placeholder='Insert your comment' value='" + text + "'>");
    event.target.onclick = updateComment;
    $(event.target).removeClass("btn-dark fa-edit").addClass("btn-success fa-check-circle");
}

function updateComment(event) {
    $.ajax({
        type: "POST",
        url: "/updateComment",
        data: {
            idReview: event.target.id,
            content: $(event.target.parentNode.children[2]).val()
        },
        success: function () {
            window.location.reload()
        },
        error: function () {
            showAlertModal("Comment error", "Impossible to modify the preview, retry.", ICONS.alert);
        }
    });
}

function deleteComment(event) {
    $.ajax({
        type: "GET",
        url: "/deleteComment",
        data: {
            id: event.target.id
        },
        success: function () {
            $(event.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode).remove();
        },
        error: function () {
            showAlertModal("Review error", "Impossible to delete the comment. Reload the page", ICONS.alert);
        }
    });
}
