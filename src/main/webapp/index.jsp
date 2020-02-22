<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Homepage</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script src="scripts/alertUtility.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/indexStyle.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
</head>

<body>
<jsp:include page="header.jsp"/>
<h1 class="text-center">Best sellers</h1>
<div class="carousel slide bestSellers-size-slide" data-ride="carousel" id="carousel-1">
    <div class="carousel-inner bestSellers-size-inner" role="listbox">
        <c:set var="bI" scope="page" value="0"></c:set>
        <c:forEach var="best" items="${bestSellers}">
            <c:if test="${bI == 0}">
            <div class="carousel-item bestSellers-size-item active">
            </c:if>
                <c:if test="${bI != 0}">
                <div class="carousel-item bestSellers-size-item">
                    </c:if>
                <a href="/GameDataSheet?gameId=${best.getId()}"><img class="d-block bestSellers-size-img"
                                                                                   src="gameFiles/${best.getName()}/${best.getFrontImage()}"></a><!--TODO:front image-->
            </div>
                <c:set value="${1+bI}" var="bI"></c:set>
        </c:forEach>
        <div>
            <a class="carousel-control-prev" href="#carousel-1" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carousel-1" role="button" data-slide="next">
                <span class="carousel-control-next-icon"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <ol class="carousel-indicators">
            <c:set var="bI" value="0"></c:set>
            <c:forEach items="${bestSellers}">
                <c:if test="${bI == 0}">
                    <li data-target="#carousel-1" data-slide-to="${bI}" class="active"></li>
                </c:if>
                <c:if test="${bI != 0}">
                    <li data-target="#carousel-1" data-slide-to="${bI}"></li>
                </c:if>
                <c:set value="${1+bI}" var="bI"></c:set>
            </c:forEach>
        </ol>
    </div>
</div>
<c:forEach var="tagName" items="${hashMapTagsAndGames.keySet()}">
<div class="categories-container"> <!--TODO: qui dentro va fatto un foreach sull'array dei tag -->
    <div class="category-games border rounded">
        <h2>${tagName}</h2>
        <div class="container-fluid category-games-container-fluid-size">
            <div id="multi-item-${tagName}" class="carousel slide carousel-multi-item category-games-multi-item-size"
                 data-ride="carousel">

                <div class="controls-top bs">
                    <a class="btn-floating" href="#multi-item-${tagName}" data-slide="prev"><i
                            class="fa fa-chevron-left color-arrow"></i></a>
                    <a class="btn-floating" href="#multi-item-${tagName}" data-slide="next"><i
                            class="fa fa-chevron-right color-arrow"></i></a>
                </div>

                <ol class="carousel-indicators">
                    <c:set var="i" value="0"></c:set>
                    <c:forEach items="${hashMapTagsAndGames.get(tagName)}">
                        <li data-target="#multi-item-shooter" data-slide-to="${i}" class="active"></li>
                        <c:set var="i" value="${1+i}"></c:set>
                    </c:forEach>
                </ol>
                <c:set var="index" scope="page" value="${0}"></c:set>
                <c:set var="active" scope="page" value="active"></c:set>
                <div class="carousel-inner category-games-inner-size" role="listbox">
                    <c:forEach items="${hashMapTagsAndGames.get(tagName)}" var="game">
                    <c:if test="${index > 5}">
                        <c:set var="active" scope="page" value=""></c:set>
                    </c:if>
                    <c:if test="${index%6 == 0}">
                    <c:if test="${index > 0}">
                </div> <!-- row -->
            </div> <!-- item -->
            </c:if>
            <div class="carousel-item ${active} category-games-item" >
                <div class="row category-games-row">
                    </c:if>
                    <div class="col-md-2 clearfix d-none d-md-block image-game-div" >
                        <a href="/GameDataSheet?gameId=${game.getId()}"><img class="card-img-top image-game" src="gameFiles/${game.getName()}/${game.frontImage}"></a>
                    </div>
                    <c:set var="index" scope="page" value="${index + 1}"></c:set>

                    </c:forEach>
                </div> <!-- row -->
            </div> <!-- item -->
        </div>
            </div>
        </div>
    </div>
    </c:forEach>
    <jsp:include page="footer.html"/>
</body>
<div id="alertModal" class="modal fade" role="dialog" tabindex="-1">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header"><i id="alertIcon" class="fas fa-exclamation-triangle"
                                         style="padding-top: 0.5rem"></i>
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
<script>
    let urlString = window.location;
    let url = new URL(urlString);
    let c = url.searchParams.get("registered");
    if (c === "true") {
        showAlertModal("Congratulations", "The registration has gone well", ICONS.info);
    }
</script>
</html>