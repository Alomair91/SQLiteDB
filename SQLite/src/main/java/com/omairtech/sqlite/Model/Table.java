package com.omairtech.sqlite.Model;

import java.io.Serializable;

public class Table implements Serializable {

    public String tableName;
    public String tableQuery;

    public Table(String tableName, String tableQuery) {
        this.tableName = tableName;
        this.tableQuery = tableQuery;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableQuery(String tableQuery) {
        this.tableQuery = tableQuery;
    }
    public String getTableQuery() {
        return tableQuery;
    }

}

