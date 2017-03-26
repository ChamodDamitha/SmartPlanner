package com.example.chamod.smartplanner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chamod on 2/25/17.
 */

public class DB_Helper extends SQLiteOpenHelper {
    private static DB_Helper db_helper = null;
    private static final int db_version = 14;
    protected static final String db_name = "smart_planner_db";

    //singleton for DB_helper class
    public static DB_Helper getInstance(Context context) {
        if (db_helper == null)
            db_helper = new DB_Helper(context);
        return db_helper;
    }

    public DB_Helper(Context context) {
        super(context, db_name, null, db_version);
    }


//    users table
    public static final String users_table ="users";
    public static final String user_email="user_email";
    public static final String user_name="name";
    public static final String user_phone="user_phone";

//    tasks table
    public static final String tasks_table ="tasks";
    public static final String task_id="task_id";
    public static final String task_type="task_type";
    public static final String task_description="task_description";
    public static final String task_date="task_date";
    public static final String task_alerted="task_alerted";
    public static final String task_completed="task_completed";
    public static final String task_repeat="task_repeat";

//  times table
    public static final String timeset_table ="time_tasks";
    public static final String timeset_id ="timeset_id";
    public static final String date_alert="date_alert";
    public static final String time_alert="time_alert";
    public static final String task_time="task_time";

//  locations table
    public static final String locations_table="locations";
    public static final String loc_id="loc_id";
    public static final String task_location_name="task_location_name";
    public static final String task_location_latitude="task_location_latitude";
    public static final String task_location_longitude="task_location_longitude";
    public static final String task_location_range="task_location_range";


//  messages table
    public static final String messages_table="messages";
    public static final String msg_id="msg_id";
    public static final String msg_content ="msg_content";
    public static final String receiver_no="receiver_no";



//      MyPlaces table
    public static final String myplaces_table="myplaces";
    public static final String place_id="place_id";
    public static final String place_name="place_name";
    public static final String place_address="place_address";
    public static final String place_latitude="place_latitude";
    public static final String place_longitude="place_longitude";




    @Override
    public void onCreate(SQLiteDatabase db) {

        String users_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s VARCHAR(100) PRIMARY KEY," +
                "%s VARCHAR(150),"+
                "%s VARCHAR(10)"+
                ");",users_table,user_email, user_name,user_phone);


        String task_table_query=String.format("" +
                "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY," +
                    "%s VARCHAR(10)," +
                    "%s VARCHAR(150)," +
                    "%s DATE," +
                    "%s INT," +
                    "%s INT," +
                    "%s INT," +
                    "%s INT," +
                    "%s INT," +
                    "%s INT," +
                    "FOREIGN KEY(%s) REFERENCES %s(%s)," +
                    "FOREIGN KEY(%s) REFERENCES %s(%s)," +
                    "FOREIGN KEY(%s) REFERENCES %s(%s)" +
                ");", tasks_table,task_id,task_type,task_description,task_date,
                task_alerted,task_completed,task_repeat,loc_id,msg_id, timeset_id,
                    loc_id,locations_table,loc_id,
                    msg_id,messages_table,msg_id,
                timeset_id, timeset_table, timeset_id);

        String times_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY," +
                "%s DATE,"+
                "%s TIME,"+
                "%s TIME"+
                ");", timeset_table, timeset_id,date_alert,time_alert,task_time);

        String locations_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY," +
                "%s VARCHAR(100),"+
                "%s DOUBLE,"+
                "%s DOUBLE,"+
                "%s FLOAT"+
                ");",locations_table,loc_id,task_location_name,task_location_latitude,task_location_longitude,task_location_range);

        String messages_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY," +
                "%s VARCHAR(300),"+
                "%s VARCHAR(10)"+
                ");",messages_table,msg_id, msg_content,receiver_no);




        String myplace_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY," +
                "%s VARCHAR(20) UNIQUE," +
                "%s VARCHAR(100)," +
                "%s DOUBLE," +
                "%s DOUBLE" +
                ");",
                myplaces_table,place_id,place_name,place_address,place_latitude,place_longitude);

        db.execSQL(users_table_query);
        db.execSQL(times_table_query);
        db.execSQL(messages_table_query);
        db.execSQL(locations_table_query);
        db.execSQL(task_table_query);
        db.execSQL(myplace_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + users_table);
        db.execSQL("DROP TABLE IF EXISTS " + tasks_table);
        db.execSQL("DROP TABLE IF EXISTS " + timeset_table);
        db.execSQL("DROP TABLE IF EXISTS " + locations_table);
        db.execSQL("DROP TABLE IF EXISTS " + messages_table);
        db.execSQL("DROP TABLE IF EXISTS " + myplaces_table);
        onCreate(db);
    }
}
