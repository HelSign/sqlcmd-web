$(function(){
    // send ajax to
    // on answer
    $.get("menu/content", function(elements) {
        var container = $("#menu_container");
        for (var index in elements) {
            var element = elements[index];
            container.append('<a href="' + element + '">' + element + '</a></br>');
        }
    });
});