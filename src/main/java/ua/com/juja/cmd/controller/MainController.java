package ua.com.juja.cmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/")
    public String homepage() {
        return "main";
    }

    @GetMapping(value = "/main")
    public String mainPage(HttpServletRequest request) {
        DBManager dbManager = (DBManager) request.getSession().getAttribute("dbManager");
        if (dbManager == null) {
            return "redirect:connect";
        } else
            return "main";
    }

    @GetMapping(value = "/help")
    public String printHelp() {
        return "help";
    }

    @GetMapping(value = "/connect")
    public String connectToDbGet(ModelMap model) {
        model.addAttribute("connection", new Connection());
        return "connect";
    }

    @PostMapping(value = "/connect")
    public String connectToDbPost(@ModelAttribute("connection") Connection connection, HttpServletRequest request) {
        DBManager dbManager = service.connect(connection.getDbName(), connection.getUserName(), connection.getPassword());
        request.getSession().setAttribute("dbManager", dbManager);
        return "redirect:main";
    }

    @GetMapping(value = "/operations/{name}")
    public String showAllOperations(ModelMap model, @PathVariable(value = "name") String userName) {
        model.addAttribute("operations", service.userOperations(userName));
        return "operations";

    }
}
