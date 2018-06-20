<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>SQLCMD</title>
    </head>
    <body>
        <table>
            <c:forEach items="${operations}" var="userOperations">
               <tr>
                    <td>  ${userOperations.dbConnection.userName}</td>
                    <td>  ${userOperations.dbConnection.dbName}</td>
                    <td>  ${userOperations.operation}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>