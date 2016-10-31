package com.example.dacas.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by qingf on 2016/9/28.
 */

public class PersonSQLiteHelper extends SQLiteOpenHelper{
    public static final String CREATE_PERSON ="create table person ("
            + "id integer primary key autoincrement, "
            + "username text, "
            + "password text, "
            + "ticket text)";
    private Context pContent;
    public PersonSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        pContent = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PERSON);
        Toast.makeText(pContent,"Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
