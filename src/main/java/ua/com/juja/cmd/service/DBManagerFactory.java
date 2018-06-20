package ua.com.juja.cmd.service;

import org.springframework.stereotype.Component;
import ua.com.juja.cmd.model.DBManager;

@Component
public class DBManagerFactory {
    private String className;

    public DBManager createDBManager() {
        try {
            return (DBManager) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
