package ua.com.juja.cmd.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.controller.Configuration;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DBManagerTest {
    private DBManager dbManager;
    private Set<String> columns;

    @BeforeEach
    public void setup() {
        dbManager = getDBManager();
        try {
            Configuration configuration = new Configuration();
            String dbName = configuration.getDbName();
            String user = configuration.getUser();
            String password = configuration.getPassword();
            dbManager.makeConnection(dbName, user, password);
            columns = new LinkedHashSet<String>();
            columns.add("col1");
            columns.add("col2");
            columns.add("col3");
            columns.add("col4");
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void release() throws SQLException {
        dropTable();
        dbManager.closeConnection();
    }

    abstract DBManager getDBManager();

    @Test
    public void testCheckIfConnected() {
        assertTrue(dbManager.isConnected());
    }

    @Test
    public void testGetTableNames() throws SQLException {
        Set<String> tableNames = dbManager.getTablesNames();
        assertTrue(tableNames.contains("u_test"));
    }

    @Test
    public void testInsertSelectRows() throws SQLException {
        DataSet dataSet = new DBDataSet();
        dataSet.put("col1", "value1");
        dataSet.put("col2", "value2");
        dataSet.put("col3", "value3");
        dataSet.put("col4", "value4");
        dbManager.insertRows("u_test", dataSet);
        List<DataSet> result = dbManager.getTableData("u_test");
        assertEquals(dataSet, result.get(0));
    }

    @Test
    public void testInsertUpdateRows() throws SQLException {
        //given
        DataSet dataSet = new DBDataSet();
        dataSet.put("col1", "value1");
        dataSet.put("col2", "value2");
        dataSet.put("col3", "value3");
        dataSet.put("col4", "value4");
        dbManager.insertRows("u_test", dataSet);
        DataSet condition = new DBDataSet();
        condition.put("col1", "value1");
        DataSet data = new DBDataSet();
        data.put("col2", "new value");
        //when
        dbManager.updateRows("u_test", condition, data);
        //then
        dataSet.put("col2", "new value");
        List<DataSet> result = dbManager.getTableData("u_test");
        assertEquals(dataSet.hashCode(), result.get(0).hashCode());
    }

    @Test
    public void testInsertDeleteRows() throws SQLException {
        //given
        DataSet dataSet = new DBDataSet();
        dataSet.put("col1", "value1");
        dataSet.put("col2", "value2");
        dataSet.put("col3", "value3");
        dataSet.put("col4", "value4");
        dbManager.insertRows("u_test", dataSet);
        dataSet = new DBDataSet();
        dataSet.put("col1", "value11");
        dataSet.put("col2", "value21");
        dataSet.put("col3", "value31");
        dataSet.put("col4", "value41");
        dbManager.insertRows("u_test", dataSet);
        DataSet condition = new DBDataSet();
        condition.put("col1", "value1");
        //when
        dbManager.deleteRows("u_test", condition);
        //then
        List<DataSet> result = dbManager.getTableData("u_test");
        assertEquals(dataSet.hashCode(), result.get(0).hashCode());
    }

    @Test
    public void testTruncateTable() throws SQLException {
        //given
        DataSet dataSet = new DBDataSet();
        dataSet.put("col1", "value1");
        dataSet.put("col2", "value2");
        dataSet.put("col3", "value3");
        dataSet.put("col4", "value4");
        dbManager.insertRows("u_test", dataSet);
        //when
        dbManager.truncateTable("u_test");
        //then
        assert (dbManager.getTableData("u_test").size() == 0);
    }

    @Test
    public void testGetTableColumns() throws SQLException {
        //given

        //when
        Set<String> result = dbManager.getTableColumns("u_test");
        //then
        assertEquals (columns,result);
    }

    private void createTable() throws SQLException {
        dbManager.createTable("u_test", columns);
    }

    private void dropTable() throws SQLException {
        dbManager.dropTable("u_test");
    }

}
