<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/profileEditForm.css">
    <link rel="stylesheet" href="css/sidebarMenu.css">
</head>

<body>
<div class="background-photo">
    <jsp:include page="profileMenu.html"></jsp:include>
    <div class="container profile profile-view" id="profile">
        <form method="POST" enctype="multipart/form-data">
            <div class="profile-center">
                <div></div>
                <label class="avatar">
                    <c:if test="${!toShow.getImage()}">
                        <c:if test="${toShow.isGoogleUser()}">
                            <jsp:include page="/printGoogleImage?id=${toShow.getId()}&width=200&height=200"></jsp:include>
                        </c:if>
                        <c:if test="${!toShow.isGoogleUser()}">
                            <img src="https://www.gravatar.com/avatar/1234566?size=200&d=mm" width="200" height="200">
                        </c:if>
                    </c:if>
                    <c:if test="${toShow.getImage()}">
                        <img src="/printImage?${toShow.getId()}" width="200" height="200">
                    </c:if>
                    <c:if test="${!toShow.isGoogleUser()}">
                        <input type="file" name="profileImage" onclick="$('#saveCancel').show()" style="display: none">
                    </c:if>
                </label>
                <c:if test="${!toShow.isGoogleUser()}">
                    <label>Click on the image to change it</label>
                </c:if>
                <h1>Profile </h1>
                <hr>
                <c:if test="${!toShow.isGoogleUser()}">
                    <div class="form-row">
                        <div class="col-sm-10 col-md-10">
                            <div class="form-group">
                                <label>Username</label>
                                <input class="form-control" type="text"
                                       name="username" id="inputUsername"
                                       value="${toShow.getUsername()}" readonly>
                            </div>
                        </div>
                        <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                            <button type="button" class="btn btn-dark btn-sm" id="btnChangeUsername">
                                <i class="fa fa-edit" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-10">
                            <div class="form-group">
                                <label>Email </label>
                                <input class="form-control" type="email"
                                       autocomplete="off" id="inputEmail"
                                       name="email" value="${toShow.getEmail()}"
                                       readonly>
                            </div>
                        </div>
                        <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                            <button type="button" class="btn btn-dark btn-sm" id="btnChangeEmail">
                                <i class="fa fa-edit" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-sm-10 col-md-10">
                            <div class="form-group">
                                <label>Password </label>
                                <input class="form-control" type="password"
                                       name="password" autocomplete="off"
                                       value="${toShow.getPassword()}"
                                       id="inputPassword" readonly/>
                            </div>
                        </div>
                        <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                            <button type="button" class="btn btn-dark btn-sm" id="btnChangePassword">
                                <i class="fa fa-edit" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </c:if>
                <div class="form-row">
                    <div class="col-10">
                        <label>Description</label>
                        <input id="inputDescription" class="form-control" name="description"
                               type="text" value="${toShow.getDescription()}"
                               readonly>
                    </div>
                    <div class="col text-center align-self-center col-2" style="padding-top: 30px;">
                        <button type="button" class="btn btn-dark btn-sm" id="btnChangeDescription">
                            <i class="fa fa-edit" aria-hidden="true"></i>
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
        </form>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script src="scripts/profileScript.js"></script>
</body>

</html>