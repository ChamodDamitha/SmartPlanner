package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.User;

/**
 * Created by chamod on 4/20/17.
 */

public class UserDB {
    private static UserDB userDB=null;

    private DB_Helper db_helper;

    public static UserDB getInstance(Context context){
        if(userDB==null){
            userDB=new UserDB(context);
        }
        return userDB;
    }


    private UserDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }

    public boolean addUser(User user){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_Helper.user_email,user.getEmail());
        contentValues.put(DB_Helper.user_name,user.getName());
        contentValues.put(DB_Helper.user_phone,user.getPhone());

        db.insert(DB_Helper.users_table,null,contentValues);
        return true;
    }

    public User getUser(){
        SQLiteDatabase db=db_helper.getReadableDatabase();

        String query=String.format("SELECT * FROM %s",DB_Helper.users_table);
        Cursor cursor=db.rawQuery(query,null);

        while (cursor.moveToNext()){
            return new User(cursor.getString(cursor.getColumnIndex(DB_Helper.user_email)),
                    cursor.getString(cursor.getColumnIndex(DB_Helper.user_name)),
                    cursor.getString(cursor.getColumnIndex(DB_Helper.user_phone)));
        }
        return null;
    }
}
