<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
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
</head>
<header>
    <nav class="navbar navbar-expand-md custom-header">
        <div class="container-fluid">
            <div><a href="/"><img alt="logo" src="Assets/logo.png"></a></div>
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <div class="nav navbar-nav ml-auto">
                    <% if (request.getSession().getAttribute("logged") == null || !(boolean) request.getSession().getAttribute("logged")) {%>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="nav-link"><a class="header-link" href="register"><span class="fas fa-user"></span> Sign Up</a></li>
                        <li class="nav-link"><a class="header-link" href="#login" data-toggle="modal"><span class="fas fa-door-open"></span> Login</a></li>
                    </ul>
                    <%} else if (request.getSession().getAttribute("logged") != null && (boolean) request.getSession().getAttribute("logged")) {%>
                    <div class="nav-item dropdown show">
                        <a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="true" href="#">
                            <% if ( ((User) request.getSession().getAttribute("user")).getImage() == null && !(((User) request.getSession().getAttribute("user")).isGoogleUser())) { %>
                                <img class="dropdown-image" src="https://www.gravatar.com/avatar/1234566?size=200&d=mm" width="50" height="50">
                            <% } else { %>
                                <% if ( (((User) request.getSession().getAttribute("user"))).isGoogleUser()) { %>
                                    <jsp:include page="/printGoogleImage"></jsp:include>
                                <% } else { %>
                                    <img class="dropdown-image" src="/printImage" width="50" height="50">
                                <% } %>
                            <% } %>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" role="menu">
                            <a class="dropdown-item" role="presentation" href="profile">Settings</a>
                            <a class="dropdown-item" role="presentation" href="library">Library</a>
                            <a class="dropdown-item" role="presentation" href="upload">Upload</a>
                            <a class="dropdown-item" role="presentation" href="help">Help</a>
                            <a class="dropdown-item" role="presentation" href="logout" onclick="signOut()">Logout</a>
                        </div>
                    </div>
                    <%}%>
                </div>
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
                        <label id="errorLabel" style="display: none"> Email o password errate! Riprova </label>
                        <button type="button" class="btn btn-primary btn-block" id="loginBtn">Login</button>
                    </div>
                    <div class="form-group">
                        <% if (request.getSession().getAttribute("logged") == null || !(boolean) request.getSession().getAttribute("logged")) {%>
                        <div class="g-signin2" data-onsuccess="onSignIn"></div>
                        <script>
                            function onSignIn(googleUser) {
                                googleSignIn(googleUser);
                            }
                        </script>
                        <% } %>
                    </div>
                    <a class="forgot" href="forgotPassword">Hai dimenticato la tua password?</a>
                    <a class="forgot" href="register">Non hai un account? Registrati</a>
                </form>
            </div>
        </div>
    </div>
</div>
</html>