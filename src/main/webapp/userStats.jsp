<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User statistics</title>
    <meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="css/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap-4.4.1-dist/css/bootstrap.css">
    <link rel="stylesheet" href="css/userStatsStyle.css">
    <link rel="stylesheet" href="css/sidebarMenu.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="profileMenu.html"></jsp:include>
<c:set var="userLibrary" scope="page" value="${user.getLibrary()}"></c:set>
<div style="margin-left: 20%">
    <ul class="nav nav-tabs">
        <li class="nav-item"><a class="nav-link active" role="tab" data-toggle="tab" href="#tab-1">General Stats</a>
        </li>
        <c:set var="i" value="2" scope="page"></c:set>
        <c:forEach items="${userLibrary}" var="game">
            <li class="nav-item"><a class="nav-link" role="tab" data-toggle="tab" href="#tab-${i}">${game.getName()}</a></li>
            <c:set var="i" value="${i + 1}" scope="page"></c:set>
        </c:forEach>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" role="tabpanel" id="tab-1">
            <div class="row">
                <div class="col">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Hours Played</p>
                        <p class="text-center">Total: ${totalHoursPlayed} </p>
                    </div>
                </div>
                <div class="col-7">
                    <canvas id="hoursChart" class="canvas-size"></canvas>
                </div>
            </div>
        </div>
        <c:set var="j" value="2" scope="page"></c:set>
        <c:set var="k" value="0" scope="page"></c:set>
        <c:forEach var="game" items="${userLibrary}">
            <div class="tab-pane" role="tabpanel" id="tab-${j}">
                <div class="row">
                    <div class="col">
                        <div class="bg-dark border rounded border-info">
                            <p class="text-center">Hours Played</p>
                            <p class="text-center">Total:
                                <c:if test="${totalGameHoursPlayed.size() != 0}">
                                    ${totalGameHoursPlayed.get(k)}
                                </c:if>
                                <c:if test="${totalGameHoursPlayed.size() != 0}">
                                    0
                                </c:if>
                            </p>
                        </div>
                    </div>
                    <div class="col-7">
                        <canvas id="hoursPlayedGame-${k}" class="canvas-size chartsHoursPlayed"></canvas>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="bg-dark border rounded border-info">
                            <p class="text-center">Your scores</p>
                        </div>
                    </div>
                    <div class="col-7">
                        <canvas id="scoresGame-${k}" class="canvas-size chartsScoresGame"></canvas>
                    </div>
                </div>
            </div>
            <c:set var="k" value="${k + 1}"></c:set>
            <c:set var="j" value="${j + 1}"></c:set>
        </c:forEach>
        </div>
    <!-- script for charts -->
    <script>
        let ctx = document.getElementById('hoursChart').getContext('2d');
        let chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ${hoursPlayedKeys},
                datasets: [{
                    label: 'Hours',
                    backgroundColor: 'rgb(173, 216, 240)',
                    borderColor: 'rgb(255, 165, 0)',
                    data: ${hoursPlayedValues}
                }]
            },
            options: {}
        });
        let gamesHoursCharts = document.getElementsByClassName("chartsHoursPlayed");
        for (let c =0; c<gamesHoursCharts.length; c++){
            let canvas = gamesHoursCharts[c];
            let ctx = canvas.getContext('2d');
            console.log(${arrayHashMapHoursKeys});
            let chart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: ${arrayHashMapHoursKeys}[c],
                    datasets: [{
                        label: 'Hours',
                        backgroundColor: 'rgb(173, 216, 240)',
                        borderColor: 'rgb(255, 165, 0)',
                        data: ${arrayHashMapHoursValues}[c]
                    }]
                },
                options: {}
            });
        }
        let scoresCharts = document.getElementsByClassName("chartsScoresGame");
        for (let c =0; c<scoresCharts.length; c++){
            let canvas = scoresCharts[c];
            let ctx = canvas.getContext('2d');
            let chart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: ${scoresKeys}[c],
                    datasets: [{
                        label: 'Scores',
                        backgroundColor: 'rgb(173, 216, 240)',
                        borderColor: 'rgb(255, 165, 0)',
                        data: ${scoresValues}[c]
                    }]
                },
                options: {}
            });
        }
    </script>


    <jsp:include page="footer.html"></jsp:include>
</body>
<script>
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>
</html>
