package ua.com.juja.cmd.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ua.com.juja.cmd.controller.Configuration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Manages data in database
 */
@Component
public class JDBCManager implements DBManager {
    private Connection connection;
    private final static Logger LOG = LogManager.getLogger();
    private JdbcTemplate template;
    private String userName;
    private String dbName;

    /**
     * Opens connection with specified user name and password
     *
     * @param dbName
     * @param user
     * @param password
     */
    @Override
    public void makeConnection(String dbName, String user, String password) {
        if (connection != null) {
            LOG.trace("Connection already exist");
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error(e);
            throw new RuntimeException("PostgreSQL JDBC Driver is not in the library path!", e);
        }
        Configuration configuration = new Configuration();
        String url = configuration.getUrl();
        LOG.debug("Trying to connect to DB with url='{}'", url);
        try {
            connection = DriverManager.getConnection(url, user, password);
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
            this.userName = user;
            this.dbName = dbName;
        } catch (Exception e) {
            connection = null;
            template = null;
            LOG.error("", e);
            throw new RuntimeException(String.format("Connection failed to database: %s as user: %s , url: %s ",
                    dbName, user, url), e);
        }
        LOG.debug("Connection has been created");
    }

    /**
     * Creates table with specified name and columns. All columns are varchar(40)
     *
     * @param name
     * @param columns
     * @return 1 if table was created without any exception
     */
    @Override
    public int createTable(String name, Set<String> columns) {
        LOG.traceEntry();
        // todo check if required  checkIfConnected();
        String columnsList = StringUtils.collectionToDelimitedString(columns, " varchar(40),");
        String query = String.format("CREATE TABLE %s (%s  varchar(40))", name, columnsList);
        LOG.debug(query);
        template.execute(query);
        LOG.traceExit();
        return 1;
    }

    /**
     * Closes database conection if it is open
     *
     * @throws SQLException
     */
    @Override
    public void closeConnection() throws SQLException {
        if (connection != null) {
            if (!connection.getAutoCommit())
                connection.commit();
            connection.close();
            LOG.debug("Connection has been closed");
        }
    }

    /**
     * Inserts data into specified table
     *
     * @param table String table name
     * @param data  DataSet object with data to be inserted (pairs of column name and value)
     * @return int number of inserted rows
     */
    @Override
    public int insertRows(String table, DataSet data) {
        LOG.traceEntry();
        checkIfConnected();
        String columnsList = StringUtils.collectionToCommaDelimitedString(data.getNames());
        String valuesList = StringUtils.collectionToDelimitedString(data.getValues(), "','");
        String query = String.format("INSERT INTO public.%1$s (%2$s) VALUES ('%3$s')", table, columnsList, valuesList);
        LOG.debug(query);
        int numRows = template.update(query);
        LOG.traceExit();
        return numRows;
    }

    /**
     * Updates specified table according to condition
     *
     * @param table
     * @param condition as pair of column name and value for part of UPDATE statement WHERE column=value
     * @param data      for update as pairs of column name and value for part of UPDATE statement SET column=value
     * @return int number of updated rows
     */
    @Override
    public int updateRows(String table, DataSet condition, DataSet data) {
        LOG.traceEntry();
        checkIfConnected();
        int numRows;
        String columnsList = StringUtils.collectionToDelimitedString(data.getNames(), ",", "", "=?");
        String conditionList = StringUtils.collectionToDelimitedString(condition.getNames(), ",", "", "=?");
        String query = String.format("UPDATE public.%s SET %s WHERE %s", table, columnsList, conditionList);
        LOG.debug(query);
        List<Object> objects = new LinkedList<>(data.getValues());
        objects.addAll(condition.getValues());
        numRows = template.update(query, objects.toArray());
        LOG.debug(numRows + " rows were updated");
        LOG.traceExit();
        return numRows;
    }

    /**
     * Deletes rows in specified table
     *
     * @param table
     * @param condition as pair of column name and value for part of DELETE statement WHERE column=value
     * @return number of deleted rows
     */
    @Override
    public int deleteRows(String table, DataSet condition) {
        LOG.traceEntry();
        checkIfConnected();
        String query = "DELETE FROM public." + table + " WHERE ";
        Set<String> columns = condition.getNames();
        for (String colName: columns) {
            query = String.format(query + "%1$s='%2$s'", colName, condition.get(colName));
        }
        LOG.debug(query);
        int numRows = -1;
        numRows = template.update(query);
        LOG.debug(numRows + " rows were deleted");
        LOG.traceExit();
        return numRows;
    }

    /**
     * Clears specified table
     *
     * @param table
     * @return 1 if table was truncated
     */
    @Override
    public int truncateTable(String table) {
        LOG.traceEntry();
        checkIfConnected();
        String query = "TRUNCATE TABLE public." + table;
        LOG.debug(query);
        int numRows = template.update(query);
        LOG.traceExit();
        return numRows;
    }

    /**
     * Drops specified table
     *
     * @param table
     * @return 1 if table was deleted
     */
    @Override
    public int dropTable(String table) {
        LOG.traceEntry();
        checkIfConnected();
        String query = "DROP TABLE public." + table;
        LOG.warn(query);
        int numRows = template.update(query);
        LOG.traceExit();
        return numRows;
    }

    /**
     * Selects data from specified table
     *
     * @param tableName
     * @return rows with data
     */
    @Override
    public List<DataSet> getTableData(String tableName) {
        LOG.traceEntry();
        checkIfConnected();
        String query = "SELECT * FROM " + tableName;
        LOG.debug(query);
        List<DataSet> result = template.query(query, new RowMapper<DataSet>() {//todo lambda
            @Override
            public DataSet mapRow(ResultSet rs, int rowNum) throws SQLException {
                ResultSetMetaData rsmd = rs.getMetaData();
                DataSet dataSet = new DBDataSet();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                return dataSet;
            }
        });
        LOG.traceExit();
        return result;
    }

    /**
     * Gets list of columns of specified table
     *
     * @param tableName
     * @return
     */
    @Override
    public Set<String> getTableColumns(String tableName) {
        LOG.traceEntry();
        checkIfConnected();
        Set<String> result;
        String query = "SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" +
                tableName + "'";
        LOG.debug(query);
        result = new LinkedHashSet<>(template.query(query, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("column_name");
            }
        }));
        LOG.traceExit();
        return result;
    }

    @Override
    public Set<String> getTablesNames() {//todo streams
        LOG.traceEntry();
        checkIfConnected();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                "AND table_type='BASE TABLE'";
        LOG.debug(query);
        Set<String> result = new LinkedHashSet<>(template.query(query,
                (rs, rownum) -> {
                    return rs.getString("table_name");
                }));
        LOG.traceExit();
        return result;
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }

    private void checkIfConnected() {
        if (connection == null) {
            LOG.warn("Connection to DB is not established");
            throw new RuntimeException("Connection to DB is not established. Please connect to you DB");
        }
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getDbName() {
        return dbName;
    }
}
