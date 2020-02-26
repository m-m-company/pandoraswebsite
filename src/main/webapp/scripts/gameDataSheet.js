$(document).ready(function () {
    $("#thirdRow").hide();
    $("#fourthRow").hide();
    let id = window.location.search.split("=")[1];
    insertComments(id);
    insertPreview(id);
    insertTags(id);
    insertGameData(id);
    insertRank(id);
});

function insertComments(id) {
    $.ajax({
        type: "GET",
        url: "/getComments",
        data: {
            idGame: id
        },
        success: (data) => {
            if(data.length !== 0){
                $("#thirdRow").show();
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
                    populateComment(data[i].author, username, src);
                }
            }
        }
    });
}

function populateComment(idAuthor, username, src) {
    $.ajax({
        type: "POST",
        url: "/getUserForComment",
        data: {
            id: idAuthor
        },
        success: function (data) {
            $(username).html(data[1]);
            username.href = "profile?id=" + data[0];
            if (data[3] === "true") {
                src.src = "/printImage?id=" + data[0];
            }
            else if (data[3] === "false") {
                src.src = "https://www.gravatar.com/avatar/1234566?size=200&d=mm";
            }
            else {
                src.src = data[3];
            }
        },
        error: function () {
            showAlertModal("Generic error", "There's an error with comments, try later", ICONS.alert);
        },
    })
}

function insertPreview(id) {
    insertPreviewsFromServer(id);
}

function insertPreviewsFromServer(id) {
    $.ajax({
        type: "GET",
        url: "/getPreviewGame",
        data: {
            idGame: id
        },
        success: function (data) {
            $("#slides").empty();
            data.map(function (img, index) {
                let active = "active";
                if (index !==0 )
                    active = "";
                $("#slides").append(
                    "<div class=\"carousel-item size-div-preview "+active+"\">" +
                    "" +
                    "<img class='w-100 h-100 d-block float-left size-div-preview' src='"+img+"'></img>" +
                    "</div>"
                )
            });
            insertPreviewsExternalLinks(id);
        },
        error: function () {
            $("#slides").empty().append("<h1 class='text-center'> No preview images </h1>");
        }
    });
}

function insertPreviewsExternalLinks(id) {
    $.ajax({
        type: "GET",
        url: "/getGameLinks",
        data: {
            gameID: id
        },
        success: function (data) {
            data[0].map(function (link, index) {
                $("#slides").append("<div class='carousel-item size-div-preview text-center'> <div id='player-"+index+"'></div> </div>");
                let player = new YT.Player('player-'+index,{
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
            })
            data[1].map(function (img) {
                $("#slides").append(
                    "<div class=\"carousel-item size-div-preview\">" +
                    "" +
                    "<img class='w-100 h-50 d-block float-left size-div-preview' src='"+img+"'></img>" +
                    "</div>"
                )
            });
            calculateTotalSize();
        }
    });
}

function calculateTotalSize() {
    let totalSize = document.getElementById("slides").children.length;
    console.log(totalSize);
    $("#indicators").append("<li data-target=\"#carousel-1\" data-slide-to = 0 class=\"active\"></li>");
    for (let i = 1; i < totalSize; ++i){
        $("#indicators").append("<li data-target=\"#carousel-1\" data-slide-to = "+i+"></li>");
    }
}

function insertTags(id) {
    $.ajax({
        type: "GET",
        url: "getGameTags",
        data: {
            idGame: id
        },
        success: function (data) {
            data.map(tag => $("#pCategory").append("<li class=\"d-inline\">"+tag+"</li>"))
        }
    })
}

function insertGameData(id) {
    $.ajax({
        type: "GET",
        url: "getGameDetails",
        data: {
            idGame: id
        },
        success: function (data) {
            $("#gameName").text(data.name);
            $("#gameDescription").text(data.description);
            $("#releaseDate").text($("#releaseDate").text() + data.release);
            $("#devLink").attr("href", "/profile?id=" + data.idDeveloper);
            $("#gameSpecs").text(data.specifics);
            $.ajax({
                type: "GET",
                url: "getDeveloperName",
                data: {
                    idDeveloper: data.idDeveloper
                },
                success: function (name) {
                    console.log("name");
                    $("#devLink").text(name);
                }
            });
            $("#helpMail").attr("href", "/help?emailTo=" + data.supportEmail);
            $("#price").text($("#price").text() + data.price + "€");
        }
    })
}

function insertRank(id) {
    $.ajax({
        type: "GET",
        url: "getRank",
        data: {
            id: id
        },
        success: function (data) {
            if(data.length !== 0) {
                $("#fourthRow").show();
                data.map((rank, index) => {
                    $("#ranks").append("<tr>\n" +
                        "                   <td class=\"td-color\">" + index + "</td>\n" +
                        "                   <td class=\"td-color\">" + rank.username + "</td>\n" +
                        "                   <td class=\"td-color\">" + rank.value + "</td>\n" +
                        "               </tr>")
                });
            }
        }
    });
}