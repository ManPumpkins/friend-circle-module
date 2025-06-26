package com.g.friendcirclemodule.dp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "feed.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表 int friendName, int friendHead, String decStr, Integer[] friendImageId, int time, Integer friendVideoId
        String sql = "create table accounttb(id integer primary key autoincrement,friendName integer,friendHead integer,decStr varchar(80),friendImageId varchar(80)," +
                "time varchar(60), friendVideoId varchar(80))";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
