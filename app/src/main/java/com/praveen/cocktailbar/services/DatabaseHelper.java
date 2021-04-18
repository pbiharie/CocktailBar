package com.praveen.cocktailbar.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.praveen.cocktailbar.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cocktailBar_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTableQuery = "CREATE TABLE IF NOT EXISTS user (" +
                "\"ID\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "\"Firstname\" TEXT NOT NULL, " +
                "\"Lastname\" TEXT NOT NULL, " +
                "\"Username\" TEXT NOT NULL, " +
                "\"Password\" TEXT NOT NULL " +
                ");";
        db.execSQL(userTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
    }

    //------------------------------------------------------------------------------------------------UserActivity
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Firstname", user.getFirstName());
        contentValues.put("Lastname", user.getLastName());
        contentValues.put("Username", user.getUserName().toLowerCase());
        contentValues.put("Password", user.getPassword());
        db.insert("user", null, contentValues);
        db.close();
    }

    public User getUser(String userName){
        User user = null;
        SQLiteDatabase db = null;
        
        try {
            db = this.getReadableDatabase();
            Cursor cursor = db.query("user", new String[]{"ID", "Firstname", "Lastname", "Username", "Password"}, "Username = ?", new String[]{String.valueOf(userName).toLowerCase()}, null, null, null);
            cursor.moveToFirst();
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        } catch (Exception e){
            user = null;
        } finally {
            db.close();
        }
        return user;
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        SQLiteDatabase db = null;

        try{
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                    userList.add(user);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e){
            userList = null;
        } finally {
            db.close();
        }

        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Firstname", user.getFirstName());
        contentValues.put("Lastname", user.getLastName());
        contentValues.put("Username", user.getUserName());
        contentValues.put("Password", user.getPassword());

        db.update("user", contentValues, "ID = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", "ID = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

}
