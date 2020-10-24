package com.omairtech.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.omairtech.sqlite.Model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammed Alomair on 2020/4/25.
 */
public class SQLiteDB extends SQLiteOpenHelper {

    private static String dbName = "SQLiteDB.db";
    private static int dbVersion = 1;
    private static List<String> createTableQueryList;
    private static ArrayList<Table> tableQueryList;

    public static SQLiteDB init(Context context, String dbName, int dbVersion, ArrayList<Table> tableQueryList) {
        SQLiteDB.dbName = dbName + ".db";
        SQLiteDB.dbVersion = dbVersion;
        SQLiteDB.tableQueryList = tableQueryList;
        return new SQLiteDB(context);
    }

    public static SQLiteDB init(Context context, String dbName, int dbVersion, List<String> createTableQueryList) {
        SQLiteDB.dbName = dbName;
        SQLiteDB.dbVersion = dbVersion;
        SQLiteDB.createTableQueryList = createTableQueryList;
        return new SQLiteDB(context);
    }

    public SQLiteDB(Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (createTableQueryList != null) {
            for (String query : createTableQueryList) {
                db.execSQL(query);
            }
        }
        if (tableQueryList != null) {
            for (Table table : tableQueryList) {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + table.tableName + "(" + table.tableQuery + ")");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (tableQueryList != null) {
            for (Table table : tableQueryList) {
                db.execSQL("Drop TABLE IF EXISTS " + table.tableName);
            }
        }
        onCreate(db);
    }

    /**
     * execute method
     * execute any query to database
     *
     * @param query string query
     */
    public void execute(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    /**
     * select method
     * select all records in query from database
     *
     * @param query string query
     */
    public Cursor select(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }


    /**
     * show method
     * select all records in table from database
     *
     * @param tableName table name
     */
    public Cursor show(String tableName) {
        return select("SELECT * FROM " + tableName);
    }

    /**
     * show method
     * select specific record in table from database
     *
     * @param id value of id
     * @param tableName table name
     */
    public Cursor show(int id, String tableName) {
        return select("SELECT * FROM " + tableName + " WHERE id=" + id);
    }


    /**
     * getMaxId method
     * get mac id in table from database
     *
     * @param tableName table name
     */
    public int getMaxId(String tableName) {
        Cursor cursor = select("SELECT (IFNULL(MAX(id),0)) AS 'id' FROM " + tableName);
        return cursor.getInt(cursor.getColumnIndex("id"));
    }

    /**
     * getMinId method
     * get min id in table from database
     *
     * @param tableName table name
     */
    public int getMinId(String tableName) {
        Cursor cursor = select("SELECT IFNULL(MIN(id),0) AS 'id' FROM " + tableName);
        return cursor.getInt(cursor.getColumnIndex("id"));
    }

    /**
     * getMinIdUnderZero method
     * get min id under zero in table from database
     *
     * @param tableName table name
     */
    public int getMinIdUnderZero(String tableName) {
        Cursor cursor = select("SELECT IFNULL(MIN(id),0) AS 'id' FROM " + tableName + " WHERE id < 0");
        return cursor.getInt(cursor.getColumnIndex("id"));
    }


    /**
     * insert method
     * insert new record to database
     *
     * @param tableName table name
     * @param contentValues content of data
     */
    public boolean insert(String tableName, ContentValues contentValues) {
        if (contentValues != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            long rowId = db.insert(tableName, null, contentValues);
            db.close();
            return rowId != -1;
        }
        return false;
    }

    /**
     * update method
     * update specific record in database
     *
     * @param id value of id
     * @param tableName table name
     * @param contentValues content of data
     */
    public boolean update(int id, String tableName, ContentValues contentValues) {
        if (contentValues != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            int rowAffected = db.update(tableName, contentValues, "id=?", new String[]{Integer.toString(id)});
            db.close();
            return rowAffected > 0;
        }
        return false;
    }

    /**
     * insert or Uupdate method
     * insert new record to database if record is in database or update it
     *
     * @param id value of id
     * @param tableName table name
     * @param contentValues content of data
     */
    public boolean insertOrUpdate(int id, String tableName, ContentValues contentValues) {
        return show(id, tableName).getCount() == 0 ? insert(tableName, contentValues) : update(id, tableName, contentValues);
    }


    /**
     * Delete method
     * delete all records in database
     *
     * @param tableName table name
     */
    public boolean delete(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowAffected = db.delete(tableName, null, null);
        db.close();
        return rowAffected != 0;
    }

    /**
     * Delete method
     * delete specific record in database
     *
     * @param id value of id
     * @param tableName table name
     */
    public boolean delete(int id, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowAffected = db.delete(tableName, "id=?", new String[]{Integer.toString(id)});
        db.close();
        return rowAffected != 0;
    }

    /**
     * Delete method
     * delete records with where close in database
     *
     * @param tableName table name
     * @param where where clause
     */
    private void delete(String tableName, String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName + " " + where);
        db.close();
    }
}