package com.jdhd.qynovels.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUtils extends SQLiteOpenHelper {
    public DbUtils(Context context) {
        super(context, "qy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * 用户书架
         */
        sqLiteDatabase.execSQL("create table if not exists usercase(_id integer primary key autoincrement,user text,name text,image text,author text,readContent text,readStatus integer,bookStatus integer,bookid integer,backlistPercent integer,lastTime integer,backlistId integer)");
        /**
         * 书架顶部
         */
        sqLiteDatabase.execSQL("create table if not exists hot(_id integer primary key autoincrement,bookid integer,name text,image text,intro text,author text)");
        /**
         * 阅读历史数据库
         */
        sqLiteDatabase.execSQL("create table if not exists readhistory(_id integer primary key autoincrement,user text,name text,image text,author text,readContent text,readStatus integer,bookStatus integer,bookid integer,backlistPercent integer,lastTime text,backlistId integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
