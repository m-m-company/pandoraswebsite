<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/searchBar.css">
</head>

<div class="filter">
    <form method="post" action="/searchGames">
        <input placeholder="search game" name="gameName" value=""><br>
        <select name="category">
            <option value="noCategory">Category</option>
            <c:forEach items="${tags}" var="tag">
                <option value="${tag}">${tag}</option>
            </c:forEach>
        </select>
        <select name="price">
            <option value="noPrice">Price</option>
            <option value="0-10">0€-9.99€</option>
            <option value="10-20">10€-19.99€</option>
            <option value="20-30">20€-29.99€</option>
            <option value="30-40">30€-30.99€</option>
            <option value="40-50">40€-40.99€</option>
        </select>
        <select name="rating">
            <option value="noRating">Rating</option>
            <option value="rated1">1⭐</option>
            <option value="rated2">2⭐</option>
            <option value="rated3">3⭐</option>
            <option value="rated4">4⭐</option>
            <option value="rated5">5⭐</option>
        </select>
    </form>
</div>