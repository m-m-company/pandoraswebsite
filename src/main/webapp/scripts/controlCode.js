let cont = 0;

function checkCode(event) {
    event.preventDefault();
    $.ajax({
       type: "POST",
       url: "/controlCode",
       data: {
           code : $("#code").val()
       },
        success : function(){
           window.location.replace("/nextPage");
        },
        error : function () {
            showAlertModal("Code error", "The code is incorrect", ICONS.alert);
            cont++;
            if(cont >= 3){
                window.location.href = "http://www.google.it";
            }
        }
    });
}

function resendCode(event) {
    $.ajax({
        type: "GET",
        url: "/sendCode",
        success: function () {
            showAlertModal("Resent", "The code is in your mailbox", ICONS.info);
        },
        error: function () {
            showAlertModal("Generic error", "There is a generic error, contact the customer care service", ICONS.alert);
        }
    });
}

function goBack(event) {
    window.location.replace("/previousPage");
}