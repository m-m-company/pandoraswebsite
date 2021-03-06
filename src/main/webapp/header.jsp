<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="google-signin-client_id" content="254902414036-0872j3k7esabpdm90bjauogg7qq3eebq.apps.googleusercontent.com">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/login.css">
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@beta/dist/js.cookie.min.js"></script>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <script src="scripts/header.js"></script>
    <script src="scripts/googleSignIn.js"></script>
    <script src="scripts/alertUtility.js"></script>
</head>
<header>
    <nav class="navbar navbar-expand-md custom-header">
        <div class="container-fluid">
            <div><a href="/"><img alt="logo" src="Assets/logo.png"></a></div>
            <ul class="nav navbar-nav nav-fill w-100">
                <li class="nav-item link" role="presentation"><a id="upload" class="header-link" href="upload">UPLOAD</a></li>
                <li class="nav-item link" role="presentation"><a class="header-link" href="help">HELP</a></li>
            </ul>
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav ml-auto">
                    <% if (request.getSession().getAttribute("logged") == null || !(boolean) request.getSession().getAttribute("logged")) {%>
                        <li class="nav-link"><a class="header-link" href="register"><span class="fas fa-user"></span> Sign Up</a></li>
                        <li class="nav-link"><a class="header-link" href="#login" data-toggle="modal"><span class="fas fa-door-open"></span> Login</a></li>
                    <%} else if (request.getSession().getAttribute("logged") != null && (boolean) request.getSession().getAttribute("logged")) {%>
                        <li class="nav-item dropdown show">
                            <a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="true" href="#">
                                <% if ( !((User) request.getSession().getAttribute("user")).getImage() && !(((User) request.getSession().getAttribute("user")).isGoogleUser())) { %>
                                    <img class="dropdown-image" src="https://www.gravatar.com/avatar/1234566?size=200&d=mm" width="50" height="50">
                                <% } else { %>
                                    <% if ( (((User) request.getSession().getAttribute("user"))).isGoogleUser()) { %>
                                        <jsp:include page="/printGoogleImage?class=dropdown-image&width=50&height=50"></jsp:include>
                                    <% } else { %>
                                        <img class="dropdown-image" src="/printImage" width="50" height="50">
                                    <% } %>
                                <% } %>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right" role="menu">
                                <a class="dropdown-item" role="presentation" href="profile">My profile</a>
                                <a class="dropdown-item" role="presentation" href="library">My library</a>
                                <a class="dropdown-item" role="presentation" href="friends">My friends</a>
                                <a class="dropdown-item" role="presentation" href="logout" onclick="signOut()">Logout</a>
                            </div>
                        </li>
                    <%}%>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div id="login" class="modal fade modal-get-offer">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="login-dark">
                <form>
                    <h2 class="sr-only">Login Form</h2>
                    <div class="illustration"><i class="icon ion-ios-locked-outline"></i></div>
                    <div class="form-group"><input class="form-control" type="email" id="logEmail" name="email" placeholder="Email">
                    </div>
                    <div class="form-group"><input class="form-control" type="password" id="logPassword" name="password"
                                                   placeholder="Password"></div>
                    <div class="form-group">
                        <label id="errorLabel" style="display: none"> Wrong email or password! Retry </label>
                        <button type="button" class="btn btn-primary btn-block" id="loginBtn">Login</button>
                    </div>
                    <div class="form-group">
                        <% if (request.getSession().getAttribute("logged") == null || !(boolean) request.getSession().getAttribute("logged")) {%>
                        <div class="g-signin2" data-onsuccess="onSignIn" data-longtitle="true" data-width="auto"></div>
                        <script>
                            function onSignIn(googleUser) {
                                googleSignIn(googleUser);
                            }
                        </script>
                        <% } %>
                    </div>
                    <a class="forgot" href="forgotPassword">Did you forgot the password?</a>
                    <a class="forgot" href="register">Don't you have an account? Register!</a>
                </form>
            </div>
        </div>
    </div>
</div>
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