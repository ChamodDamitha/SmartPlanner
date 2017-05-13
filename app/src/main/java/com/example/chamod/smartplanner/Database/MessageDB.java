package com.example.chamod.smartplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chamod.smartplanner.Models.Contact;
import com.example.chamod.smartplanner.Models.Location;
import com.example.chamod.smartplanner.Models.Message;

/**
 * Created by chamod on 3/26/17.
 */

public class MessageDB {
    private static MessageDB messageDB =null;

    private DB_Helper db_helper;

    private MessageDB(Context context){
        db_helper=DB_Helper.getInstance(context);
    }

    public static MessageDB getInstance(Context context){
        if(messageDB ==null){
            messageDB =new MessageDB(context);
        }
        return messageDB;
    }


    //    ...........Add a Message record..............
    public boolean addMessageRecord(Message message){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.msg_content,message.getContent());
        cv.put(DB_Helper.receiver_name,message.getReceiver().getName());
        cv.put(DB_Helper.receiver_no,message.getReceiver().getPhone_no());

        db.insert(DB_Helper.messages_table,null,cv);

        db=db_helper.getReadableDatabase();
        String query = String.format("SELECT MAX(%s) as max_id FROM %s ;",DB_Helper.msg_id,DB_Helper.messages_table);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            message.setMsg_id(cursor.getInt(cursor.getColumnIndex("max_id")));
            return true;
        }
        return false;
    }

    //    ...........Update a Message record..............
    public boolean updateMessageRecord(Message message){
        SQLiteDatabase db=db_helper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(DB_Helper.msg_content,message.getContent());
        cv.put(DB_Helper.receiver_name,message.getReceiver().getName());
        cv.put(DB_Helper.receiver_no,message.getReceiver().getPhone_no());
        db.update(DB_Helper.messages_table,cv,DB_Helper.msg_id+"="+message.getMsg_id(),null);

        return true;
    }

//    ...............Get a message record.................................
    public Message getMessageRecord(int msg_id){
        SQLiteDatabase db=db_helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s=%s;",DB_Helper.messages_table,DB_Helper.msg_id,msg_id);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext())
        {
            Message message= new Message(cursor.getString(cursor.getColumnIndex(DB_Helper.msg_content)),
                    new Contact(
                            cursor.getString(cursor.getColumnIndex(DB_Helper.receiver_name))
                            ,cursor.getString(cursor.getColumnIndex(DB_Helper.receiver_no))
                    )
                    );
            message.setMsg_id(msg_id);
            return message;
        }
        return null;
    }


    //    ...........Delete a Message record..............................
    public boolean deleteMessageRecord(int msg_id){
        SQLiteDatabase db=db_helper.getWritableDatabase();
        db.delete(DB_Helper.messages_table,DB_Helper.msg_id+"="+msg_id,null);
        return true;
    }
}
