package ua.com.juja.cmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.model.DataSet;
import ua.com.juja.cmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "/find/content", method = RequestMethod.GET)
    public List<List<String>> getTableContent(HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        String table = request.getParameter("table");
        List<List<String>> tableContent = new LinkedList<>();
        if (table == null)
            table = "book";
        try {
            tableContent = service.find(dbManager, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableContent;
    }

}
