<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User statistics</title>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="css/bootstrap-4.4.1-dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/bootstrap-4.4.1-dist/css/bootstrap.css">
    <link rel="stylesheet" href="css/userStatsStyle.css">
    <link rel="stylesheet" href="css/sidebarMenu.css">
    <link rel="stylesheet" href="css/devStats.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
</head>
<body>
<jsp:include page="profileMenu.html"></jsp:include>
<div style="margin-left: 20%">
    <ul class="nav nav-tabs">
        <li class="nav-item"><a class="nav-link active" role="tab" data-toggle="tab" href="#tab-1">General Stats</a>
        </li>
        <c:set var="i" value="2" scope="page"></c:set>
        <c:forEach items="${uploadedGames}" var="game">
            <li class="nav-item"><a class="nav-link" role="tab" data-toggle="tab" href="#tab-${i}">${game.getName()}</a>
            </li>
            <c:set var="i" value="${i+1}" scope="page"></c:set>
        </c:forEach>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" role="tabpanel" id="tab-1">
            <div class="row justify-content-center" style="margin-bottom: 5%">
                <div class="col-3">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Total sells</p>
                        <p class="text-center">${totalSells}</p>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center" style="margin-bottom: 5%">
                <div class="col-3">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Total earnings</p>
                        <p class="text-center">${totalEarnings}</p>
                    </div>
                </div>
            </div>
        </div>
        <c:set var="i" value="2" scope="page"></c:set>
        <c:forEach items="${uploadedGames}" var="game">
            <div class="tab-pane" role="tabpanel" id="tab-${i}">
                <div class="row">
                    <div class="col">
                        <div class="bg-dark border rounded border-info">
                            <p class="text-center">Review starrings</p>
                            <c:if test="${averageStarring.size() > i}">
                                <p class="text-center">Average: ${averageStarring.get(Integer.parseInt(i))}</p>
                            </c:if>
                            <c:if test="${averageStarring.size() <= i}">
                                <p class="text-center">Average: 0</p>
                            </c:if>

                        </div>
                    </div>
                    <div class="col-7">
                        <div>
                            <canvas id="starringsGame-${i}" class="canvas-size"></canvas>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="bg-dark border rounded border-info">
                            <p class="text-center">Sells</p>
                            <c:if test="${sellsPerGame.size() > i}">
                                <p class="text-center">Total: ${sellsPerGame.get(Integer.parseInt(i))}</p>
                            </c:if>
                            <c:if test="${sellsPerGame.size() < i}">
                                <p class="text-center">Total: 0</p>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-7">
                        <div>
                            <canvas id="sellsGame-${i}" class="canvas-size"></canvas>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="bg-dark border rounded border-info">
                            <p class="text-center">Price</p>
                        </div>
                    </div>
                    <div class="col-7">
                        <div>
                            <canvas id="priceGame-${i}" class="canvas-size"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <c:set var="i" scope="page" value="${i+1}"></c:set>
        </c:forEach>
    </div>
</div>

<!-- script for charts -->
<script>
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    let i = ${i};
    for (let c = 2; c < i; c++) {
        var ctx = document.getElementById('starringsGame-' + c).getContext('2d');
        var chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ${starringKeys}[c],
                datasets: [{
                    label: 'Avg. starring',
                    backgroundColor: 'rgb(173, 216, 240)',
                    borderColor: 'rgb(255, 165, 0)',
                    data: ${starringValues}[c]
                }]
            },
            options: {}
        });
    }
    i = ${i};
    for (let c = 2; c < i; c++) {
        var ctx = document.getElementById('sellsGame-' + c).getContext('2d');
        var chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ${sellsKeys}[c],
                datasets: [{
                    label: 'Avg. starring',
                    backgroundColor: 'rgb(173, 216, 240)',
                    borderColor: 'rgb(255, 165, 0)',
                    data: ${sellsValues}[c]
                }]
            },
            options: {}
        });
    }
    i = ${i};
    for (let c = 2; c < i; c++) {
        var ctx = document.getElementById('priceGame-' + c).getContext('2d');
        var chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ${pricesKeys}[c],
                datasets: [{
                    label: 'Avg. starring',
                    backgroundColor: 'rgb(173, 216, 240)',
                    borderColor: 'rgb(255, 165, 0)',
                    data: ${pricesValues}[c]
                }]
            },
            options: {}
        });
    }
</script>
<footer>
    <jsp:include page="footer.html"></jsp:include>
</footer>
</body>
</html>
