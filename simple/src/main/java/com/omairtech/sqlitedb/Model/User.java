package com.omairtech.sqlitedb.Model;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public class User implements Serializable {
    public int id;
    public String name;
    public int status;


    public User(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static User fromCursor(Cursor cursor) {
        return new User(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getInt(cursor.getColumnIndex("status"))
        );
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("status", status);
        return contentValues;
    }
}

