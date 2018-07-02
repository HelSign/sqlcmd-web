<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
    <title>SQLCMD</title>
    <script type="text/javascript" src="${ctx}/resources/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/find.js"></script>
     <script id="tableHeader" type="text/x-jquery-tmpl">
            <tr>
                {{each $data}}
                    <th><h1> {{= $value}} </h1> </th>
                {{/each}}
            </tr>
    </script>
    <script id="row-template" type="text/x-jquery-tmpl">
            <tr>
                {{each $data}}
                    <td> {{= $value}}</td>
                {{/each}}
            </tr>
        </script>
</head>
<body>
<table id="container" border="1">
</table>
</body>
</html>