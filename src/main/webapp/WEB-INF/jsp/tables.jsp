<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <table id="tables" class="container">
            <script type="text/javascript">
            var markup =
                "<tr>" +
                "<td> <a href=\"main?table={{=}}#find\"> {{=}}</a></td>" +
                "<td> <a href=\"drop?table={{=}}\"> drop </a></td>" +
                "<td> <a href=\"update?table={{=}}\"> update </a></td>" +
                "</tr>";
            $.template( "row-template", markup );
        </script>
        </table>
