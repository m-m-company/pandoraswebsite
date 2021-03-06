<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap-4.4.1-dist/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="css/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
    <script src="scripts/alertUtility.js"></script>
    <script src="scripts/library.js"></script>
    <link rel="stylesheet" href="css/library.css">
</head>
<body>
    <c:if test="${user.getLibrary().size() == 0}">
        <h1>Your library is empty. Buy something <a href="${pageContext.request.contextPath}/">here!</a></h1>
    </c:if>
    <c:if test="${user.getLibrary().size() != 0}">
        <div class="row background-library">
            <div class="col-2" style="background-color: rgba(255,255,255,0.9);">
                <jsp:include page="libraryList.jsp"></jsp:include>
            </div>
            <div id="gameDetails" class="col-10" style="background-color: rgba(255,255,255,0.6);">
                <jsp:include page="libraryGamePage.html"></jsp:include>
            </div>
        </div>
    </c:if>
</body>

<div id="alertModal" class="modal fade" role="dialog" tabindex="-1">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header"><i id="alertIcon" class="fas fa-exclamation-triangle" style="padding-top: 0.5rem"></i>
                <h4 class="modal-title" id="alertTitle"></h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">x</span></button>
            </div>
            <div class="modal-body">
                <p id="alertContent"></p>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" data-dismiss="modal">Okay</button>
            </div>
        </div>
    </div>
</div>
</html>