$(function(){
    $.get("menu/content", function(elements) {
            $("row-template").tmpl(elements).appendTo("#menu");
    });
});