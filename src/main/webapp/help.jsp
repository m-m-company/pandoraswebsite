<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Help</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
    <link rel="stylesheet" href="css/help.css">
</head>

<body>
<div class="background-help">
    <div class="top-help">
        <div class="jumbotron-contact jumbotron-contact-sm">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 col-lg-12">
                        <h1 class="h1">
                            Contact us <small>Feel free to contact us</small></h1>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <div class="well well-sm">
                        <form method="post">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="name">Name</label>
                                        <c:if test="${name != null}">
                                            <input type="text" class="form-control" id="name" name="name"
                                                   placeholder="Your name" value="${name}" required="required"/>
                                        </c:if>
                                        <c:if test="${name == null}">
                                            <input type="text" class="form-control" id="name" name="name"
                                                   placeholder="Your name" required="required"/>
                                        </c:if>
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email Address</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><span
                                                    class="glyphicon glyphicon-envelope"></span></span>
                                            <c:if test="${email == null}">
                                                <input type="email" class="form-control" id="email" name="email"
                                                       placeholder="Your email" required="required"/>
                                            </c:if>
                                            <c:if test="${email != null}">
                                                <input type="email" class="form-control" id="email" name="email"
                                                       placeholder="Your email" value="${email}" required="required"/>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="subject">Subject</label>
                                        <input required class="form-control" type="text" id="subject" name="subject"
                                               placeholder="Your subject here"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="name">
                                            Message</label>
                                        <textarea name="content" id="message" class="form-control" rows="9" cols="25"
                                                  required="required"
                                                  placeholder="Message"></textarea>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-primary pull-right" id="btnContactUs">Send
                                        Message
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <form>
                        <address>
                            <strong>You are sending this to:</strong><br>
                            <a href="mailto:#">${emailTo}</a>
                        </address>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>

</html>