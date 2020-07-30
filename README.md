Android SQLiteDB
-------

- Helper library written in Java language to dealing with SQLite database in Android Studio

- This class provides developers with a simple way to ship their Android app with an existing SQLite database (which may be pre-populated with data) and to manage its initial creation and any upgrades required with subsequent version releases.


To get a Git project into your build:
-------

#### gradle
- Step 1. Add the JitPack repository to your build file
  (Add it in your root build.gradle at the end of repositories):

	    allprojects {
            repositories {
        	    ...
        	    maven { url 'https://jitpack.io' }
            }
        }
    
- Step 2. Add the dependency

        dependencies {
            implementation 'com.github.Alomair91:SQLiteDB:1.0'
        }


#### maven
- Step 1.

        <repositories>
            <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
            </repository>
        </repositories>
        
- Step 2. Add the dependency

        <dependency>
            <groupId>com.github.Alomair91</groupId>
            <artifactId>SQLiteDB</artifactId>
            <version>1.0</version>
        </dependency>
	

How to use it?
-------

- Step 1. Create your own utils class to handle all tables like this:

        public class DBUtils {
            private static String dbName = "SQLiteDB";
            private static int dbVersion = 1;
            
            // TABLES NAME
            public static final String table_users = "users";
        
            // INIT METHOD
            public static SQLiteDB intiDataBase(Context context){
                ArrayList<Table> tableArrayList = new ArrayList<>();
                
                tableArrayList.add(new Table(table_users,"id INTEGER,name TEXT,status INTEGER"));
                
                return SQLiteDB.init(context,dbName,dbVersion,tableArrayList);
            }
        }
        
- Step 2. Declare object of SQLiteDB class in your main activity:

        SQLiteDB db;
        

- Step 3. Configure the object only once:

        db = DBUtils.intiDataBase(this);

- Step 4. Now you can use all helper methods: 



Example: (Please follow code in simple project)
-------

- Suppose we have user data to work with.
- Create user model class:

        public class User {
            public int id;
            public String name;
            public int status;
        
            public User(int id, String name, int status) {
                this.id = id;
                this.name = name;
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

- To add new user:

        User user = new User(1, "Mohammed", 1);
        db.insert(DBUtils.table_users, user.toContentValues());
       
- To update user data:

        user = new User(1, "Mohammed Alomair", 0);
        db.update(user.id, DBUtils.table_users, user.toContentValues());
       
- To add or update user data:

        User user = new User(2, "Mohammed Alomair", 1);
        db.insertOrUpdate(user.id, DBUtils.table_users, user.toContentValues());
       
- To delete specific user:

        db.delete(1, DBUtils.table_users);
        
- To delete all users:

        db.delete(DBUtils.table_users);
        
- To get all records from table:

        Cursor cursor = db.show(DBUtils.table_users);
        ArrayList<User> userList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            userList.add(User.fromCursor(cursor));
            cursor.moveToNext();
        }
        
        for (User user : userList) {
            Log.d("user" + user.id, user.name);
        }
        
- To get specific record from table:

        Cursor cursor = db.show(1, DBUtils.table_users);
        if(cursor.getCount() > 0) {
            User user = User.fromCursor(cursor);
            Log.d("user" + user.id, user.name);
        }
        
- To query any data from table:

        Cursor cursor =db.select("SELECT * FROM " + DBUtils.table_users + " WHERE id =" + id);
        if(cursor.getCount() > 0) {
            User user = User.fromCursor(cursor);
            Log.d("user" + user.id, user.name);
        }
        
- To execute any query:

        db.execute("UPDATE "+  DBUtils.table_users + " SET name='M' WHERE id =1");
    


Contributors:
-------
  * [Eng: Mohammed Alomair](https://github.com/Alomair91)

License
-------

    Copyright (C) 2011 readyState Software Ltd
    Copyright (C) 2007 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[![](https://jitpack.io/v/Alomair91/SQLiteDB.svg)](https://jitpack.io/#Alomair91/SQLiteDB)
