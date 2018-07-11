package ua.com.juja.cmd.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.*;

@RestController
public class RestService {

    @Autowired
    private Service service;

    @GetMapping(value = "/menu/content")
    public List<String> getMenuItems() {
        return service.getCommands();
    }

    @GetMapping(value = "/tables/content")
    public Set<String> getTablesList(HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        Set<String> tables = new HashSet<>();
        tables = service.tables(dbManager);
        return tables;
    }

    @GetMapping(value = "find/{table}/content")
    public List<List<String>> getTableContent(@PathVariable(value = "table") String table,
                                              HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        List<List<String>> tableContent = new LinkedList<>();
        if (table == null || dbManager == null)
            return tableContent;
        tableContent = service.find(dbManager, table);
        return tableContent;
    }

    @PostMapping(value = "/createTable")
    public String saveTable(HttpServletRequest request) {
        String message = "";
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null)
            return null;
        Table table = getTableFromJSON(request);
        Set<String> columns = new LinkedHashSet<>();
        String[] addedColumns = table.getColumns();
        Collections.addAll(columns, addedColumns);
        service.create(dbManager, table.getTableName(), columns);
        message = "Table was successfully created";
        return message;
    }

    private Table getTableFromJSON(HttpServletRequest request) {
        Table table;
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            table = gson.fromJson(reader, Table.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return table;
    }

    @PostMapping(value = "/drop")
    public String dropTable(HttpServletRequest request) {
        String table = request.getParameter("table");
        String message = "";
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null)
            return null;
        service.delete(dbManager, table);
        message = "Table was successfully dropped";
        return message;
    }

    @PostMapping(value = "/addData")
    public String insertData(HttpServletRequest request) {
        String message = "";
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null)
            return null;
        Table table = getTableFromJSON(request);
        String tableName = table.getTableName();
        List<String> listData = Arrays.asList(table.getRow());
        List<String> listColumns = Arrays.asList(table.getColumns());
        List<List<String>> data = new LinkedList<>();
        data.add(listColumns);
        data.add(listData);
        service.addData(dbManager, tableName, data);
        message = "Data were inserted";
        return message;
    }
}
