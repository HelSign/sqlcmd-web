package ua.com.juja.cmd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DBManager {

    void makeConnection(String dbName, String user, String password);

    void closeConnection() throws SQLException;

    int createTable(String name, Set<String> columns) ;

    int insertRows(String table, DataSet data) ;

    int updateRows(String table, DataSet condition, DataSet data) ;

    int deleteRows(String table, DataSet data);

    int truncateTable(String table);

    int dropTable(String table);

    List<DataSet> getTableData(String tableName) ;

    Set<String> getTableColumns(String tableName);

    Set<String> getTablesNames() ;

    boolean isConnected();

    String getUserName();

    String getDbName();
}
