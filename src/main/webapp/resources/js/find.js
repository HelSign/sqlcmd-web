$(function(){
        $.get("find/content"+$(location).attr('search'), function (elements) {
            $.tmpl("row-template", elements).appendTo("#container");
        });

});

