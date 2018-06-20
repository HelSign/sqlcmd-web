package ua.com.juja.cmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.cmd.model.*;
import ua.com.juja.cmd.model.entity.UserOperation;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public abstract class ServiceImpl implements Service {

    @Autowired
    UserOperationRepository userOperationRepository;

    abstract DBManager getDBManager();

    @Override
    public List<String> getCommands() {
        return Arrays.asList("help", "menu", "exit", "connect", "create", "tables");
    }

    @Override
    public DBManager connect(String dbName, String userName, String password) {
        DBManager dbManager = getDBManager();
        dbManager.makeConnection(dbName, userName, password);
        userOperationRepository.createOperation(dbManager, "connect");
        return dbManager;
    }


    @Override
    public List<List<String>> find(DBManager dbManager, String table) throws SQLException {
        List<DataSet> dataSets = dbManager.getTableData(table);
        List<List<String>> result = new LinkedList<>();
        for (DataSet dataSet : dataSets) {
            List<String> row = new LinkedList<>();
            result.add(row);
            for (Object value : dataSet.getValues()) {
                row.add((String) value);
            }
        }
        userOperationRepository.createOperation(dbManager, "find");
        return result;
    }

    @Override
    public Set<String> tables(DBManager dbManager) throws SQLException {
        Set<String> result = dbManager.getTablesNames();
        userOperationRepository.createOperation(dbManager, "tables");
        return result;
    }

    @Override
    public void create(DBManager dbManager, String tableName, Set<String> columns) throws SQLException {
        dbManager.createTable(tableName, columns);
        userOperationRepository.createOperation(dbManager, "create");
    }

    @Override
    public void delete(DBManager dbManager, String tableName) throws SQLException {
        dbManager.dropTable(tableName);
        userOperationRepository.createOperation(dbManager, "delete");
    }

    @Override
    public List<UserOperation> userOperations(String name) {
        return userOperationRepository.findByUserName(name);
    }
}
