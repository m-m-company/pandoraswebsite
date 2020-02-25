$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "getTags",
        success: function (data) {
            data.map(tag => {
                $("#tagsList").append("<option value=\""+tag+"\">"+tag+"</option>")
            });
        },
        error: function () {
            showAlertModal("ERROR", "There is an error with tags. Retry later", ICONS.alert);
        }
    });
});