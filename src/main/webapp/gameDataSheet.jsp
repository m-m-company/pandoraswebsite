<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>gameDataSheet</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
    <script src="scripts/gameDataSheet.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/gameDataSheet.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css">
    <script src="https://www.youtube.com/iframe_api"></script>
</head>

<body style="background-color: #284c67;">
    <jsp:include page="header.jsp" />
    <div class="row" id="firstRow">
        <div class="col-xl-7" style="width: 60%;">
            <div class="carousel slide" data-ride="carousel" id="carousel-1">
                <div class="carousel-inner" role="listbox" id="slides">

                </div>
                <div><a class="carousel-control-prev" href="#carousel-1" role="button" data-slide="prev"><span class="carousel-control-prev-icon"></span><span class="sr-only">Previous</span></a><a class="carousel-control-next" href="#carousel-1" role="button"
                        data-slide="next"><span class="carousel-control-next-icon"></span><span class="sr-only">Next</span></a></div>
                <ol id="indicators" class="carousel-indicators">

                </ol>
            </div>
        </div>
        <div class="col float-left" style="width: 40%;">
            <h1 class="text-center color-orange" id="gameName"></h1>
            <div>
                <textarea readonly class="border rounded" style="font-size: 20px; width: 100%; background-color: #e9ecef !important; resize: none;" rows="5" id="gameDescription"></textarea>
                <div>
                    <label class="d-block label-game-info" id="releaseDate">Release Date: </label>
                    <label class="d-block label-game-info">Developer : <a id="devLink"></a></label>
                </div>
                <button class="btn btn-primary border rounded background-color-orange" type="submit" id="richiediAssistenza"><a id="helpMail">Ask for Assistance</a></button>
            </div>
        </div>
    </div>
    <div class="row" id="secondRow">
        <div class="col-xl-7" style="width: 60%;">
            <ul class="d-inline" id="pCategory">This game belongs to this categories:
            </ul>
        </div>
        <div class="col float-left" style="width: 40%;">
            <div class="float-left">
                <p id="price"> Price: </p>
            </div>
            <c:if test="${logged}">
                <c:if test="${canBuy}">
                <!-- START PAYPAL PAYMENTS-->
                <div class ="float-right" id="paypal-button"></div>
                <script src="https://www.paypalobjects.com/api/checkout.js"></script>
                <script>
                    paypal.Button.render({
                        // Configure environment
                        env: 'sandbox',
                        client: {
                            sandbox: 'AYhJSV3hNi-glHZ2JbxjUXQrRf38UglWWi_HqB83rql0-0yZL_LeR1wr61bHRHsYXLwUArT6yFGadowe',
                            production: 'demo_production_client_id'
                        },
                        // Customize button (optional)
                        locale: 'it_IT',
                        style: {
                            size: 'large',
                            color: 'gold',
                            shape: 'pill',
                        },

                        // Enable Pay Now checkout flow (optional)
                        commit: true,

                        // Set up a payment
                        payment: function(data, actions) {
                            return actions.payment.create({
                                transactions: [{
                                    amount: {
                                        total: '${gamePrice}',
                                        currency: 'EUR'
                                    }
                                }]
                            });
                        },
                        // Execute the payment
                        onAuthorize: function(data, actions) {
                            return actions.payment.execute().then(function()
                            {
                                showAlertModal("SUCCESS", "Your payment has been registered", ICONS.alert);
                                $.ajax({
                                    type: "POST",
                                    url: "/paymentSuccess",
                                    data: {
                                        idGame: ${gameId},
                                        price: ${gamePrice}
                                    },
                                    success: function () {
                                        window.location.replace("/library");
                                    }
                                });
                            });
                        }
                    }, '#paypal-button');

                </script>
                <!-- END PAYPAL PAYMENTS-->
            </c:if>
                <c:if test="${not canBuy}">
                    <h5 class="color-orange" id="giàAcquistato">You've already got this game!</h5>
                </c:if>
            </c:if>
            <c:if test="${not logged}">
                <a href="#login" data-toggle="modal" class="background-color-orange border rounded" type="button" id="btnLogin2">Login</a>
            </c:if>
        </div>
    </div>
    <div class="row" id="thirdRow">
        <div class="col">
            <div class="card">
                <div class="card-header">
                    <h3>Comments and reviews</h3>
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
                                                    <a href="#">Loading...</a>
                                                    <div>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                        <span></span>
                                                    </div>
                                                    <p style="display: inline"></p>
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
    <div class="row" id="fourthRow">
        <table id="ranking" class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th class="th-color">#</th>
                    <th class="th-color">Username</th>
                    <th class="th-color">Score</th>
                </tr>
            </thead>
            <tbody id="ranks">

            </tbody>
        </table>
    </div>
    <jsp:include page="footer.html" />

</body>

</html>