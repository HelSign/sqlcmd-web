package ua.com.juja.cmd.controller;

public class Table {
    private String tableName;
    private String[] columns;
    private String[] row;

    public Table() {
        row = new String[]{};
        columns = new String[]{};
    }

    public Table(String tableName, String[] columns, String[] row) {
        this.tableName = tableName;
        this.columns = columns;
        this.row = row;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getRow() {
        return row;
    }

    public void setRow(String[] row) {
        this.row = row;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

}
