<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>SQLCMD</title>
</head>
<body>

  <% out.println("List of commands:<br>");
     out.println("To connect to database<br>");
     out.println("To see list of tables<br>");
     out.println("To delete table<br>");
     out.println("To create table<br>");
     out.println("To insert rows into table<br>");
     out.println("To update rows in table<br>");
     out.println("To delete rows from table<br>");
     out.println("To delete ALL rows from table<br>");
     out.println("To see data in table<br>");
     out.println("To see list of commands use command help<br>");

  %>
</body>
</html>