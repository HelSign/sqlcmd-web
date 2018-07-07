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

    var showTableData = function (tableName) {
        show("#table");
        $.get("find/"+tableName+"/content", function (elements) {
            $("#tableHeader").tmpl([elements[0]]).appendTo("#table");
            delete elements[0];
            $("#row-template").tmpl(elements).appendTo("#table");
        });
    }

    var dropTable = function () {
        show("#tables");
        $.post("drop" + $(location).attr('search')).done(function (mes) {
            alert(mes);
        });
        window.location.hash = "#tables";
    }

    var showCreateTableForm = function () {
        show("#create");
    }

    var hideAll = function () {
        $("#menu").hide();
        $("#table").hide();
        $("#tables").hide();
        $("#create").hide();
    }

    var show = function (element) {
        $(element).show();
    }

    var loadPage = function (data) {
        hideAll();
        var pagePart = data[0];

        if (pagePart == "menu")
            showMenu();
        else if (pagePart == "tables")
            showTablesList();
        else if (pagePart == "find")
            showTableData(data[1]);
        else if (pagePart == "create")
            showCreateTableForm();
        else if (pagePart == "drop")
            dropTable();
        else showMenu();
    }

    $('button[id="createTableBtn"]').click(function (e) {
        e.preventDefault();
        $.post({
            url: 'createTable',
            data: $('form[name=create]').serialize()
        }).done(function (mes) {
            alert(mes);
        });

        window.location.hash = "#tables";
    });



    var load = function () {
        var hash = window.location.hash.substring(1);
        var parts = hash.split('/');
        loadPage(parts);
    }

    $(window).on("hashchange", function (event) {
        load();
    });

    load();

    //var myURL = document.location;
    //  $("#linkTable").click( function(){alert("dfdfdf");});
   // $(".linkTable").click(function(){
       // $(this).hide();
     //   window.location.search="table="+$(this).text();
       // window.location.hash = "#find";
        //showTableData();
    //});
});