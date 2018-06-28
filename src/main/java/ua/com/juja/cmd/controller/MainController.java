package ua.com.juja.cmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private Service service;

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String printHelp() {
        return "help";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connectToDbGet(ModelMap model) {
        model.addAttribute("connection", new Connection());
        return "connect";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connectToDbPost(@ModelAttribute("connection") Connection connection,
                                  BindingResult result, ModelMap model, HttpServletRequest request) {
        DBManager dbManager = service.connect(connection.getDbName(), connection.getUserName(), connection.getPassword());
        request.getSession().setAttribute("dbManager", dbManager);
        return "redirect:menu";
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String find(HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");

        if (dbManager == null) {
            return "redirect:connect";
        } else
            return "find";
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null) {
            return "redirect:connect";
        } else
            return "tables";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("table") Table table, HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null) {
            return "redirect:connect";
        } else {
            try {
                Set<String> columns = new LinkedHashSet<>();
                columns.add(table.getColumn1());
                columns.add(table.getColumn2());
                columns.add(table.getColumn3());
                service.create(dbManager, table.getTableName(), columns);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "redirect:tables";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createForm(HttpServletRequest request, ModelMap model) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null) {
            return "redirect:connect";
        } else {
            model.addAttribute("table", new Table());
            return "create";
        }
    }

    @RequestMapping(value = "/drop", method = RequestMethod.GET)
    public String drop(HttpServletRequest request, ModelMap model) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null) {
            return "redirect:connect";
        } else {
            String table = request.getParameter("table");
            if (table != null) {
                try {
                    service.delete(dbManager, table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return "redirect:tables";
        }
    }

    @RequestMapping(value = "/operations/{name}", method = RequestMethod.GET)
    public String showAllOperations(ModelMap model, @PathVariable(value="name") String userName) {
        model.addAttribute("operations", service.userOperations(userName));
        return "operations";

    }
}
