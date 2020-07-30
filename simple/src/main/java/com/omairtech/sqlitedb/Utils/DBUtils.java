package com.omairtech.sqlitedb.Utils;

import android.content.Context;

import com.omairtech.sqlite.Model.Table;
import com.omairtech.sqlite.SQLiteDB;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static String dbName = "SQLiteDB";
    private static int dbVersion = 1;

    // TABLES NAME
    public static final String table_users = "users";

    // INIT METHOD
    public static SQLiteDB intiDataBase1(Context context){
        ArrayList<Table> tableArrayList = new ArrayList<>();
        tableArrayList.add(new Table(table_users,"id INTEGER,name TEXT,status INTEGER"));
        return SQLiteDB.init(context,dbName,dbVersion,tableArrayList);
    }

    public static SQLiteDB intiDataBase2(Context context){
        List<String> createTableQueryList = new ArrayList<>();

        createTableQueryList.add("create table IF NOT EXISTS " + table_users +
                "(id INTEGER," +
                "name TEXT," +
                "status INTEGER)");

        return SQLiteDB.init(context,dbName,dbVersion,createTableQueryList);
    }
}
