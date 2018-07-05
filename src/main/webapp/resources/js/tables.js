$(function(){
    $.get("tables/content", function(elements) {
           $.tmpl("row-template",elements).appendTo("#tables");
    });
});