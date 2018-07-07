<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>


<table id="table" class="container" border="1">
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
</table>
