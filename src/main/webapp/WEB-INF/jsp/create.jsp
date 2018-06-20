<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <title>SQLCMD</title>
    </head>
    <body>
           <form:form method="POST" action="create" modelAttribute="table">
             <fieldset>
                <legend>Personal information:</legend>
                    Table name<br><form:input path="tableName" /><br>
                    Column1<br><form:input path="column1" /><br>
                    Column2<br><form:input path="column2" /><br>
                    Column3<br><form:input path="column3" /><br>
                    <input type="submit" value="Submit">
             </fieldset>
         </form:form>
    </body>
</html>