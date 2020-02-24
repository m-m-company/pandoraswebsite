<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>friendPage</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Actor">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/Profile-Card-1.css">
    <link rel="stylesheet" href="css/boxedThings.css">
</head>

<body>
<c:set value="${toShow.getLibrary()}" var="friendLibrary" scope="page"></c:set>
<c:if test="${isFriend == true}">
    <div class="profile-card">
        <div class="profile-back"></div>
        <c:if test="${toShow.getImage()}">
            <img class="rounded-circle profile-pic" src="/printImage?id=${toShow.getId()}">
        </c:if>
        <c:if test="${!toShow.getImage()}">
            <img class="rounded-circle profile-pic" src="https://www.gravatar.com/avatar/1234566?size=200&d=mm">
        </c:if>
        <h3 class="profile-name">${toShow.getUsername()}</h3>
        <p class="profile-bio">${toShow.getDescription()}</p>
        <a href="mailto:${toShow.getEmail()}">${toShow.getEmail()}</a>
        <section>
            <div>
                <a id="btnShow" class="btn btn-outline-primary" onclick="doCollapse();">Hide friend library</a>
                <div class="collapse show" id="collapse-1">
                    <div>
                        <div class="team-boxed">
                            <div class="container">
                                <div class="intro">
                                    <h2 class="text-center">Friend's library </h2>
                                </div>
                                <div class="row people">
                                    <c:forEach var="game" items="${friendLibrary}">
                                        <div class="col-md-6 col-lg-4 item">
                                            <div class="box">
                                                <img class="rounded-circle" src="${game.getFrontImage()}"/>
                                                <h3 class="name"><a href="${pageContext.request.contextPath}/GameDataSheet?id=${game.getId()}">${game.getName()}</a></h3>
                                                <p class="title">${game.getTagsString()}</p>
                                                <p class="description">Hours played:${game.getHoursPlayedOfIdUser(toShow.getId())}.<br> Best score: ${game.getBestScoreOfIdUser(toShow.getId())}.</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</c:if>
<c:if test="${isFriend == false}">
    <h1>You must be a friend of ${toShow.getUsername()} to see his profile.</h1>
</c:if>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script>
    let x = false;
    function doCollapse() {
        $('#collapse-1').collapse('toggle');
        if (x)
            $('#btnShow').text("Hide Friend's library");
        else
            $('#btnShow').text("Show Friend's library");
        x = !x;
    }
</script>
</body>

</html>