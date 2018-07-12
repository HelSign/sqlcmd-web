$(function () {
    var tableNameG;

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
        tableNameG = tableName;
        show("#table");
        $("#tableName").val(tableName);
        $.get("find/" + tableName + "/content", function (elements) {
            var columnNames = elements[0];
            delete elements[0];
            $("#tableHeader").tmpl([columnNames]).appendTo("#table");
            $("#row-template").tmpl(elements).appendTo("#table");
            addColumnsToForm(columnNames);

        });
    }

    var addColumnsToForm = function (columns) {
        var htmlForm = "";
        var colNum = columns.length;
        for (i = 0; i < colNum; i++) {
            htmlForm += "<input name=\"" + columns[i] + "\"/>";
        }
        $("#addData").prepend(htmlForm);
    }


    var showCreateTableForm = function () {
              show("#create");
    }

    $('button[id="addMoreColumns"]').click(function (e) {
        e.preventDefault();
        var columnNameItems = $("#create .column");
        var lastColumn;
        lastColumn = columnNameItems.length+1;
        var htmlForm = "<input class=\"column\" name=\"Column" + lastColumn + "\"/>";
        $("#create").append(htmlForm);
    });

    var dropTable = function () {
        show("#tables");
        $.post("drop" + $(location).attr('search')).done(function (mes) {
            alert(mes);
        });
        window.location.hash = "#tables";
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
        tableName = data[1];
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

        var arrayColumn = [];
        tableNameG = $("#tableName").val();

        var columnNameItems = $("#create .column");
        columnNameItems.each(function () {
            if ($(this).val().length != 0) {
                arrayColumn.push($(this).val());
            }
        });

        var tblinfo = {
            tableName: tableNameG,
            columns: arrayColumn
        };
        var tmp = JSON.stringify(tblinfo);
        alert(tmp);
        $.post({
            url: 'createTable',
            data: JSON.stringify(tblinfo)
        }).done(function (mes) {
            alert(mes);
        });

        window.location.hash = "#tables";
    });

    $('button[id="addDataBtn"]').click(function (e) {
        e.preventDefault();

        var arrayData = [];
        var arrayColumn = [];
        var columnNameItems = $("#addData :input");
        columnNameItems.each(function () {
            if ($(this).val().length != 0) {
                arrayColumn.push($(this).attr("name"));
                arrayData.push($(this).val());
            }
        });

        var tblinfo = {
            tableName: tableNameG,
            row: arrayData,
            columns: arrayColumn
        };
        $.post({
            url: 'addData',
            data: JSON.stringify(tblinfo)
        }).done(function (mes) {
            alert(mes);
        });

        window.location.hash = "#find/" + tableNameG;
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

});