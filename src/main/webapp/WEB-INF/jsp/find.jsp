<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>


<table id="table" class="container" border="1">
    <script id="tableHeader" type="text/x-jquery-tmpl">
            <tr>
                {{each $data}}
                    <th><h1> {{= $value}} </h1> </th>
                {{/each}}
                <td></td>
            </tr>
    </script>
    <script id="row-template" type="text/x-jquery-tmpl">
            <tr>
                {{each $data}}
                    <td> {{= $value}}</td>
                {{/each}}
                <td></td>
            </tr>
        </script>
</table>
<div id="newRow">
    <form action="addData" id="addData" method="POST" name="addData">
     <input type="hidden" name="tableName" id="tableName" value="test">
        <input type="text" name="Column1"/>
        <script id="tableFooter" type="text/x-jquery-tmpl">
      {{each $data}}
                    <input type="text" name="{{= $value}}"/>
                {{/each}}
 </script>

            <button type="submit" id="addDataBtn">+</button>
      </form>




</div>