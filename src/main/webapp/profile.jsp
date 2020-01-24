<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/Profile-Edit-Form-1.css">
    <link rel="stylesheet" href="css/Profile-Edit-Form.css">
    <link rel="stylesheet" href="css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="css/Sidebar-Menu.css">
</head>

<body>
<div id="wrapper">
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand"><a href="#">Home </a></li>
            <li><a href="#">Dashboard </a></li>
            <li><a href="#">Dashboard </a></li>
            <li><a href="#">Dashboard </a></li>
        </ul>
    </div>
    <div class="page-content-wrapper">
        <div class="container-fluid"><a class="btn btn-dark" role="button" id="menu-toggle" href="#menu-toggle"><i
                class="fa fa-bars"></i></a>
            <div class="row">
                <div class="col-md-12">
                    <div>
                        <h1>User <small>menu </small></h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container profile profile-view" id="profile">
    <c:if test="${param['id']} != null && ${user.getId()} != ${param['id']}">
        <button class="btn btn-primary float-right" type="button"><i class="fa fa-user-plus" aria-hidden="true"></i>Add
            as a friend
        </button>
    </c:if>
    <form>
        <div class="form-row profile-row">
            <div class="col-md-4 relative">
                <div class="avatar">
                    <div class="avatar-bg center"></div>
                </div>
                <input type="file" class="form-control" name="avatar-file"></div>
            <div class="col-md-8">
                <h1>Profile </h1>
                <hr>
                <div class="form-row">
                    <div class="col-sm-10 col-md-10" id="profile">
                        <div class="form-group"><label>Username</label><input class="form-control" type="text"
                                                                              name="firstname" id="inputUsername"
                                                                              value="${user.getUsername()}" readonly>
                        </div>
                    </div>
                    <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                        <button class="btn btn-dark btn-sm" id="btnChangeUsername"><i class="fa fa-edit"
                                                                                      aria-hidden="true"></i></button>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-10">
                        <div class="form-group"><label>Email </label><input class="form-control" type="email"
                                                                            autocomplete="off" id="inputEmail"
                                                                            name="email" value="${user.getEmail()}"
                                                                            readonly>
                        </div>
                    </div>
                    <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                        <button class="btn btn-dark btn-sm" id="btnChangeEmail"><i class="fa fa-edit"
                                                                                   aria-hidden="true"></i></button>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-sm-10 col-md-10">
                        <div class="form-group"><label>Password </label><input class="form-control" type="password"
                                                                               name="password" autocomplete="off"
                                                                               value="${user.getPassword()}"
                                                                               id="inputPassword" readonly></div>
                    </div>
                    <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                        <button class="btn btn-dark btn-sm" id="btnChangePassword"><i class="fa fa-edit"
                                                                                      aria-hidden="true"></i></button>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-10"><label>Description</label><input id="inputDescription" class="form-control"
                                                                         type="text" value="${user.getDescription()}"
                                                                         readonly></div>
                    <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                        <button class="btn btn-dark btn-sm" id="btnChangeDescription"><i class="fa fa-edit"
                                                                                         aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
                <hr>
                <div class="form-row" id="saveCancel">
                    <div class="col-md-12 content-right">
                        <button class="btn btn-primary form-btn" type="submit">SAVE</button>
                        <button class="btn btn-danger form-btn" type="reset">CANCEL</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="jumbotron">
        <h1>Friends</h1>
        <div class="container">
            <ul class="list-inline text-center"></ul>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script>
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>
<script src="scripts/profileScript.js"></script>
</body>

</html>