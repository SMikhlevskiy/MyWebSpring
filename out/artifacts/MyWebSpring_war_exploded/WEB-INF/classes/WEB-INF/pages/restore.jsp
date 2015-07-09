<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h3>Deleted List</h3>



    <form class="form-inline" role="form" action="/SpringMVC_war_exploded/undelete" method="post">
        <table class="table table-striped">
            <thead>
            <tr>
                <td><b>Selected</b></td>
                <td><b>Photo</b></td>
                <td><b>Name</b></td>
                <td><b>Short Desc</b></td>
                <td><b>Long Desc</b></td>
                <td><b>Phone</b></td>
                <td><b>Price</b></td>
            </tr>
            </thead>

            <c:forEach items="${advs}" var="adv">
                <tr>
                    <td><input type="checkbox" name="selected" value="${adv.id}"></td>
                    <td><img height="40" width="40" src="/SpringMVC_war_exploded/image/${adv.photo.id}" /></td>
                    <td>${adv.name}</td>
                    <td>${adv.shortDesc}</td>
                    <td>${adv.longDesc}</td>
                    <td>${adv.phone}</td>
                    <td>${adv.price}</td>




                </tr>
            </c:forEach>
        </table>
        <table><tr>

            <td> <input type="submit" class="btn btn-default" value="Undelete">

        </table>
    </form>


    <form class="form-inline" role="form" action="/SpringMVC_war_exploded/" method="post">
        <input type="submit" class="btn btn-default" value="Back to Main Page">
    </form>


</div>
</body>
</html>