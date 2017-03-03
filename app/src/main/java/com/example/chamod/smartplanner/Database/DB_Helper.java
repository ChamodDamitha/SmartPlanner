package com.example.chamod.smartplanner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chamod on 2/25/17.
 */

public class DB_Helper extends SQLiteOpenHelper {
    private static DB_Helper db_helper = null;
    private static final int db_version = 3;
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

//    static variables
//    table names
    public static final String task_table="tasks";
    public static final String time_task_table="time_tasks";
    public static final String location_task_table="location_tasks";
    public static final String full_task_table="full_tasks";
//    column names
    public static final String task_id="task_id";
    public static final String task_description="task_description";
    public static final String task_year="task_year";
    public static final String task_month="task_month";
    public static final String task_day="task_day";
    public static final String task_day_of_week="task_day_of_week";
    public static final String task_alerted="task_alerted";
    public static final String task_completed="task_completed";
    public static final String task_type="task_type";


    public static final String task_hour="task_hour";
    public static final String task_minute="task_minute";
    public static final String task_alert_hour="task_alert_hour";
    public static final String task_alert_minute="task_alert_minute";

    public static final String task_location_name="task_location_name";
    public static final String task_location_latitude="task_location_latitude";
    public static final String task_location_longitude="task_location_longitude";
    public static final String task_location_range="task_location_range";


//      MyPlaces table
    public static final String myplaces_table="myplaces";
    public static final String place_id="place_id";
    public static final String place_name="place_name";
    public static final String place_address="place_address";
    public static final String place_latitude="place_latitude";
    public static final String place_longitude="place_longitude";




    @Override
    public void onCreate(SQLiteDatabase db) {
        String task_table_query=String.format("" +
                "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY," +
                    "%s VARCHAR(150)," +
                    "%s INT," +
                    "%s INT," +
                    "%s INT," +
                    "%s VARCHAR(3)," +
                    "%s INT," +
                    "%s INT," +
                    "%s VARCHAR(10)" +
                ");",task_table,task_id,task_description,task_year,task_month,task_day,task_day_of_week,task_alerted,task_completed,task_type);

        String full_task_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY," +
                "%s INT,"+
                "%s INT,"+
                "%s INT,"+
                "%s INT,"+
                "%s VARCHAR(100),"+
                "%s DOUBLE,"+
                "%s DOUBLE,"+
                "%s DOUBLE"+
                ");",full_task_table,task_id,task_hour,task_minute,task_alert_hour,task_alert_minute,
                task_location_name,task_location_latitude,task_location_longitude,task_location_range);


        String myplace_table_query=String.format("" +
                "CREATE TABLE %s(" +
                "%s INTEGER PRIMARY KEY," +
                "%s VARCHAR(20) UNIQUE," +
                "%s VARCHAR(100)," +
                "%S DOUBLE," +
                "%S DOUBLE" +
                ");",
                myplaces_table,place_id,place_name,place_address,place_latitude,place_longitude);

        db.execSQL(task_table_query);
        db.execSQL(full_task_table_query);
        db.execSQL(myplace_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + task_table);
        db.execSQL("DROP TABLE IF EXISTS " + full_task_table);
        db.execSQL("DROP TABLE IF EXISTS " + myplaces_table);
        onCreate(db);
    }
}
