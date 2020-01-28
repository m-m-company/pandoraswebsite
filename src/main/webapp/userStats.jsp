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
<div style="margin-left: 20%">
    <ul class="nav nav-tabs">
        <li class="nav-item"><a class="nav-link active" role="tab" data-toggle="tab" href="#tab-1">General Stats</a>
        </li>
        <li class="nav-item"><a class="nav-link" role="tab" data-toggle="tab" href="#tab-2">&lt;Game1&gt;</a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" role="tabpanel" id="tab-1">
            <div class="row">
                <div class="col">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Hours Played</p>
                        <p class="text-center">Total: </p>
                    </div>
                </div>
                <div class="col-7">
                    <canvas id="hoursChart" class="canvas-size"></canvas>
                </div>
            </div>
            <div class="row">
                <div class="col-5">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Best ranks</p>
                        <p class="text-center"></p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-header">
                            <h3>I tuoi commenti e recensioni</h3>
                        </div>
                        <div class="card-body" style="height: auto; width: auto">
                            <ul class="list-group" id="commentList">
                                <li class="list-group-item" style="margin-bottom:6px;">
                                    <div class="media">
                                        <div class="media-body">
                                            <div class="media" style="overflow:visible;">
                                                <div><img class="mr-3" style="width: 25px; height:25px;"
                                                          src="assets/logo.png"></div>
                                                <div class="media-body" style="overflow:visible;">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <a href="#">Sara Doe</a>
                                                            <div>
                                                                <span></span>
                                                                <span></span>
                                                                <span></span>
                                                                <span></span>
                                                                <span></span>
                                                            </div>
                                                            <p><br>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane" role="tabpanel" id="tab-2">
            <div class="row">
                <div class="col">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Hours Played</p>
                        <p class="text-center">Total: </p>
                    </div>
                </div>
                <div class="col-7">
                    <canvas id="hoursPlayedGame-1" class="canvas-size"></canvas>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="bg-dark border rounded border-info">
                        <p class="text-center">Your scores</p>
                    </div>
                </div>

                <div class="col-7">
                    <canvas id="scoresGame-1" class="canvas-size"></canvas>
                </div>
            </div>
        </div>
    </div>

    <!-- script for charts -->
    <script>
        var ctx = document.getElementById('hoursChart').getContext('2d');
        var chart = new Chart(ctx, {
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
        var ctx = document.getElementById('gamesChart').getContext('2d');
        var chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ${gamesPlayedKeys},
                datasets: [{
                    label: 'Games',
                    backgroundColor: 'rgb(173, 216, 240)',
                    borderColor: 'rgb(255, 165, 0)',
                    data: ${gamesPlayedValues}
                }]
            },
            options: {}
        });
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
