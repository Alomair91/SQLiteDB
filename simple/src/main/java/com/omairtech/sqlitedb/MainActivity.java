package com.omairtech.sqlitedb;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.omairtech.sqlite.SQLiteDB;
import com.omairtech.sqlitedb.Model.User;
import com.omairtech.sqlitedb.Utils.DBUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DBUtils.intiDataBase1(this);

        addRecord();
        updateRecord();
        addOrUpdateRecord();
        getAllRecord();
    }


    private void addRecord() {
        User user = new User(1, "Mohammed", 1);
        db.insert(DBUtils.table_users, user.toContentValues());
    }

    private void updateRecord() {
        User user = new User(1, "Mohammed Alomair", 0);
        db.update(user.id, DBUtils.table_users, user.toContentValues());
    }


    private void addOrUpdateRecord() {
        User user = new User(2, "Mohammed Alomair", 1);
        db.insertOrUpdate(user.id, DBUtils.table_users, user.toContentValues());
    }

    private void deleteRecord() {
        db.delete(1, DBUtils.table_users);
    }

    private void deleteAllRecord() {
        db.delete(DBUtils.table_users);
    }


    // get all records
    private void getAllRecord() {
        Cursor cursor = db.show(DBUtils.table_users);
        ArrayList<User> userList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            userList.add(User.fromCursor(cursor));
            cursor.moveToNext();
        }

        for (User user : userList) {
            Log.d("user" + user.id, user.name);
        }
    }

    // get specific record
    private void getOneRecord() {
        Cursor cursor = db.show(1, DBUtils.table_users);
        if (cursor.getCount() > 0) {
            User user = User.fromCursor(cursor);
            Log.d("user" + user.id, user.name);
        }
    }

    // get specific record
    private void getRecordWithId(int id) {
        Cursor cursor =db.select("SELECT * FROM " + DBUtils.table_users + " WHERE id =" + id);
        if (cursor.getCount() > 0) {
            User user = User.fromCursor(cursor);
            Log.d("user" + user.id, user.name);
        }
    }

    // execute any query
    private void updateRecord(int id) {
        db.execute("UPDATE " + DBUtils.table_users + " SET name='M' WHERE id =" + id);
    }
}