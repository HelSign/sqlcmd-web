$(function () {
    $.get("find/content" + $(location).attr('search'), function (elements) {
        $("#tableHeader").tmpl([elements[0]]).appendTo("#table");
        delete elements[0];
        $("#row-template").tmpl(elements).appendTo("#table");
    });

});

