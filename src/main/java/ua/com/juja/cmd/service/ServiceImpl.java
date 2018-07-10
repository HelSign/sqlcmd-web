package ua.com.juja.cmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.juja.cmd.model.*;
import ua.com.juja.cmd.model.entity.UserOperation;

import java.util.*;

@Component
public abstract class ServiceImpl implements Service {

    @Autowired
    UserOperationRepository userOperationRepository;

    abstract DBManager getDBManager();

    @Override
    public List<String> getCommands() {
        return Arrays.asList("help", "menu", "connect", "create", "tables");
    }

    @Override
    public DBManager connect(String dbName, String userName, String password) {
        DBManager dbManager = getDBManager();
        dbManager.makeConnection(dbName, userName, password);
        userOperationRepository.createOperation(dbManager, "connect");
        return dbManager;
    }

    @Override
    public List<List<String>> find(DBManager dbManager, String table) {
        List<List<String>> result = new LinkedList<>();
        Set<String> columnsSet = dbManager.getTableColumns(table);
        List<DataSet> dataSets = dbManager.getTableData(table);
        List<String> columnsList = new LinkedList<>(columnsSet);
        result.add(columnsList);
        for (DataSet dataSet: dataSets) {
            List<String> row = new LinkedList<>();
            result.add(row);
            for (Object value: dataSet.getValues()) {
                row.add((String) value);
            }
        }
        userOperationRepository.createOperation(dbManager, "find");
        return result;
    }

    @Override
    public Set<String> tables(DBManager dbManager) {
        Set<String> result = dbManager.getTablesNames();
        userOperationRepository.createOperation(dbManager, "tables");
        return result;
    }

    @Override
    public void create(DBManager dbManager, String tableName, Set<String> columns) {
        dbManager.createTable(tableName, columns);
        userOperationRepository.createOperation(dbManager, "create");
    }

    @Override
    public void delete(DBManager dbManager, String tableName) {
        dbManager.dropTable(tableName);
        userOperationRepository.createOperation(dbManager, "delete");
    }

    @Override
    public void addData(DBManager dbManager, String tableName, List<List<String>> data) {
        for (List<String> row: data) {
            System.out.println("row1: ");
            for (String element: row)
                System.out.print(element + ", ");
        }
       /*
        DataSet dataDS = new DBDataSet();
        Set<String> columns = data.keySet();
        for (String column: columns) {
            dataDS.put(column, data.get(column));
        }
        dbManager.insertRows(tableName, dataDS);
        userOperationRepository.createOperation(dbManager, "insert");
  */
    }

    @Override
    public void deleteData(DBManager dbManager, String tableName, Map<String, String> data) {
        DataSet condition = new DBDataSet();
        Set<String> columns = data.keySet();
        for (String column: columns) {
            condition.put(column, data.get(column));
        }
        dbManager.deleteRows(tableName, condition);
        userOperationRepository.createOperation(dbManager, "delete");
    }

    @Override
    public List<UserOperation> userOperations(String name) {
        return userOperationRepository.findByUserName(name);
    }
}
