
<div id="create"> <fieldset>
    <legend>Add table:</legend>
<form method="POST" id="createForm" action="createTable" name="create">

        Table name<input id="tableName"/><br>
        Column1<input class="column" name="column1"/>
        Column2<input  class="column" name="column2"/>
        Column3<input  class="column" name="column3"/>
    <input type="button" id="addMoreColumns" value="+">
    <br><button type="submit" id="createTableBtn">Submit</button>

</form>
 </fieldset>
</div>