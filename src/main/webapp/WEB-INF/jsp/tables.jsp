<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <title>SQLCMD</title>
    <script type="text/javascript" src="${ctx}/resources/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/tables.js"></script>
    <script type="text/javascript">
        var markup =
            "<tr>" +
            "<td> <a href=\"find?table={{=}}\"> {{=}}</a></td>" +
            "<td> <a href=\"drop?table={{=}}\"> drop </a></td>" +
            "<td> <a href=\"update?table={{=}}\"> update </a></td>" +
            "</tr>";
        $.template( "row-template", markup );
    </script>

    </head>
    <body>
        <table id="container">
        </table>
    </body>
</html>