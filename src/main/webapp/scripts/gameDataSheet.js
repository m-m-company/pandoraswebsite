$(document).ready(function () {
    let id = window.location.search.split("=")[1];
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
                populateComment(data[i].author, username, src);
            }
        }
    });
});

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