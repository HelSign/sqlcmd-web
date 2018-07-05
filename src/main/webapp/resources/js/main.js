$(function () {

    var showMenu = function () {
        show("#menu");
        $.get("menu/content", function (elements) {
            $("row-template").tmpl(elements).appendTo("#menu");
        });
    }
    var showTablesList = function () {
        show("#tables");
        $.get("tables/content", function (elements) {
            $.tmpl("row-template", elements).appendTo("#tables");
        });
    }

    var showTableData = function () {
        show("#table");
        $.get("find/content" + $(location).attr('search'), function (elements) {
            $("#tableHeader").tmpl([elements[0]]).appendTo("#table");
            delete elements[0];
            $("#row-template").tmpl(elements).appendTo("#table");
        });

    }

    var hideAll = function () {
        $("#menu").hide();
        $("#table").hide();
        $("#tables").hide();
    }
    var show = function (element) {
        $(element).show();
    }
    var loadPage = function (pagePart) {
        hideAll();
        if (pagePart == "menu")
            showMenu();
        else if (pagePart == "tables")
            showTablesList();
        else //if (pagePart == "find")
            showTableData();
    }
    var load = function () {
        var hash = window.location.hash.substring(1);
        loadPage(hash);
    }

    $(window).on("hashchange", function (event) {
        load();
    });

    load();
});