<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>SQLCMD</title>
</head>
<body>
<form:form method="POST" action="connect" modelAttribute="connection">
    <fieldset>
        <legend>Personal information:</legend>
        Database name<br><form:input path="dbName"/><br>
        User name<br><form:input path="userName"/><br>
        User password<br><form:input path="password"/><br>
        <input type="submit" value="Submit">
    </fieldset>
</form:form>
</body>
</html>