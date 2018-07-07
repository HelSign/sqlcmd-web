package ua.com.juja.cmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.model.DataSet;
import ua.com.juja.cmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
        try {
            tables = service.tables(dbManager);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    @RequestMapping(value = "find/{table}/content", method = RequestMethod.GET)
    public List<List<String>> getTableContent(@PathVariable(value = "table") String table,
                                              HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        List<List<String>> tableContent = new LinkedList<>();
        if (table == null || dbManager == null)
            return tableContent;
        try {
            tableContent = service.find(dbManager, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableContent;
    }

    @PostMapping(value = "/createTable")
    // @ResponseBody
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
        try {
            service.create(dbManager, table.getTableName(), columns);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try {
            service.delete(dbManager, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        message = "Table was successfully cleared";
        return message;
    }
}
