package com.example.mpesademo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mpesademo.model.Message;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mpesa.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_MESSAGES = "messages";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MESSAGES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public void insertMessage(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("text", text);
        db.insert(TABLE_MESSAGES, null, cv);
    }

    public List<Message> getAllMessages() {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Message(c.getInt(0), c.getString(1)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}

