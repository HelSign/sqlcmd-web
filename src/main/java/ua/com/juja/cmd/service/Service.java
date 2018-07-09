package ua.com.juja.cmd.service;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.model.DataSet;
import ua.com.juja.cmd.model.entity.UserOperation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Service {
    List<String> getCommands();

    DBManager connect(String dbName, String userName, String password);

    List<List<String>> find(DBManager dbManager, String table);

    Set<String> tables(DBManager dbManager);

    void create(DBManager dbManager, String tableName, Set<String> columns);

    void delete(DBManager dbManager, String tableName);

    void addData(DBManager dbManager, String tableName, Map<String, String> data);

    void deleteData(DBManager dbManager, String tableName, Map<String, String> data);

    List<UserOperation> userOperations(String name);
}
