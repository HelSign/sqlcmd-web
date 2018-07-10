package ua.com.juja.cmd.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Table {
    private String tableName;
    // private Set<String> columns;
    private String column1;
    private String column2;
    private String column3;
    private List<List<String>> row;

    public Table() {
        row = new LinkedList<List<String>>();
    }

    public Table(String tableName, List<List<String>> row) {
        this.tableName = tableName;
        this.row = row;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public List<List<String>> getRow() {
        return row;
    }

    public void setRow(List<List<String>> row) {
        this.row = row;
    }

   /* public Set<String> getColumns() {
        return columns;
    }

    public void setColumns(Set<String> columns) {
        this.columns = columns;
    }*/

}
