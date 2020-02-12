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
                window.location.reload();
            }
        })
    });
    $("#download").click(function (event) {
        window.location.replace("/downloadGame?id=" + sessionStorage.getItem("gameID"));
    })
});

function showGame(event) {
    $.ajax({
        type: "GET",
        url: "/getGameDetails",
        data: {
            name: event.target.id //TODO : check on other browser
        },
        success: function (game) {
            sessionStorage.setItem("gameID", game.id);
            $("#gameDetails").show();
            $("#title").text(game.name);
            $("#description").text(game.description);
            $("#specs").text(game.description);
            insertPreviews(game);
            insertRank(game);
            insertComments(game);
        },
        error: function (error) {
            alert("Hai messo mani dove non dovevi, verrai punito severamente per questo");
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
            $("#carousel").empty();
            data.map(function (b64) {
                $("#carousel").append(
                    "<div class=\"swiper-slide\"\n" +
                    "                 style=\"background-image: url(data:image/*;base64," + b64 + ");\"></div>\n" +
                    "            "
                )
            })
        },
        error: function () {
            $("#carousel").empty();
            $("#carousel").append("<h1 class='text-center'> Non ci sono immagini di preview disponibili </h1>");
            $("#preview-swap").empty();
        }
    });
    $.ajax({
        type: "GET",
        url: "/getGameLinks",
        data: {
            gameID: game.id
        },
        success: function (data) {
            data.map(function (link) {
                $("#carousel").append(
                    "<div class=\"swiper-slide\">" +
                    "<iframe width=\"560\" height=\"315\" src=\"" + link + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
                    " </div>\n"
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

function insertComments(game) {
    $.ajax({
        type: "GET",
        url: "/getComments",
        data: {
            idGame: game.id
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
                for (let j = 1; j <= stars; ++j) {
                    rating.children[j - 1].className = "fa fa-star";
                    if (j <= stars) {
                        rating.children[j - 1].style = "color: orange;"
                    } else {
                        rating.children[j - 1].style = "";
                    }
                }
                let p = commentList.children[i].children[0].children[0].children[0].children[1].children[0].children[0].children[2];
                $(p).html(data[i].comment);
                console.log(p.parentNode);
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
            } else {
                src.src = "https://www.gravatar.com/avatar/1234566?size=200&d=mm";
            }
            if (data[2] === "true") {
                $(div).append("<button type=\"button\" class=\"btn btn-dark btn-sm fa fa-edit\" id='" + idReview + "' onclick='modifyComment(event)'>\n" +
                    "                                                </button>");
                $(div).append("<button type=\"button\" class=\"btn btn-danger btn-sm fa fa-trash\" id='" + idReview + "' onclick='deleteComment(event)'>\n" +
                    "                                                </button>")
            }
        },
        error: function () {
            alert("QUALCOSA NON VA NELLA SEZIONE COMMENTI");
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
            //TODO: modal
            alert("impossibile modificare il commento. Riprova")
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
            //TODO: modal
            alert("impossibile eliminare il commento. Ricarica")
        }
    });
}
