package ua.com.juja.cmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class RestService {

    @Autowired
    private Service service;

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<String> getMenuItems() {
        return service.getCommands();
    }

    @RequestMapping(value = "/tables/content", method = RequestMethod.GET)
    public Set<String> getTablesList(HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        Set<String> tables = new HashSet<>();
        tables = service.tables(dbManager);
        return tables;
    }

    @RequestMapping(value = "find/{table}/content", method = RequestMethod.GET)
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
    public String saveTable(@ModelAttribute("table") Table table,
                            HttpServletRequest request) {
        String message = "";
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null)
            return null;

        Set<String> columns = new LinkedHashSet<>();
        columns.add(table.getColumn1());
        columns.add(table.getColumn2());
        columns.add(table.getColumn3());
        service.create(dbManager, table.getTableName(), columns);
        message = "Table was successfully created";
        return message;
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
}
