package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.MyPlace;

import java.util.ArrayList;

/**
 * Created by chamod on 3/3/17.
 */

public class MyPlaceDB {
    private static MyPlaceDB myPlaceDB=null;

    public static MyPlaceDB getInstance(Context context){
        if(myPlaceDB==null){
            myPlaceDB=new MyPlaceDB(context);
        }
        return myPlaceDB;
    }

    private MyPlaceDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }

    private DB_Helper db_helper;


    public void addMyPlace(MyPlace myPlace){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(DB_Helper.place_name,myPlace.getName());
        cv.put(DB_Helper.place_address,myPlace.getAddress());
        cv.put(DB_Helper.place_latitude,myPlace.getLatitude());
        cv.put(DB_Helper.place_longitude,myPlace.getLongitude());

        db.insert(DB_Helper.myplaces_table,null,cv);
    }

    public ArrayList<MyPlace> getAllMyPlaces(){
        ArrayList<MyPlace> myPlaces=new ArrayList<>();

        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query=String.format("SELECT * FROM %s",DB_Helper.myplaces_table);
        Cursor cursor=db.rawQuery(query,null);

        while (cursor.moveToNext()){
            MyPlace myPlace=new MyPlace(cursor.getString(cursor.getColumnIndex(DB_Helper.place_name)),
                    cursor.getString(cursor.getColumnIndex(DB_Helper.place_address)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.place_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.place_longitude))
            );
            myPlace.setPlace_id(cursor.getInt(cursor.getColumnIndex(DB_Helper.place_id)));

            myPlaces.add(myPlace);
        }
        return myPlaces;
    }

    public boolean isPlaceNameTaken(String place_name){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query=String.format("SELECT %s FROM %s WHERE %s='%s'",DB_Helper.place_name,
                DB_Helper.myplaces_table,DB_Helper.place_name,place_name);
        Cursor cursor=db.rawQuery(query,null);
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public MyPlace getMyPlace(String place_name){
        MyPlace myPlace=null;
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query=String.format("SELECT * FROM %s WHERE %s='%s'",
                DB_Helper.myplaces_table,DB_Helper.place_name,place_name);
        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToNext()){
            myPlace=new MyPlace(place_name,
                    cursor.getString(cursor.getColumnIndex(DB_Helper.place_address)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.place_latitude)),
                    cursor.getDouble(cursor.getColumnIndex(DB_Helper.place_longitude)));
            myPlace.setPlace_id(cursor.getInt(cursor.getColumnIndex(DB_Helper.place_id)));
        }
        return myPlace;
    }
}
