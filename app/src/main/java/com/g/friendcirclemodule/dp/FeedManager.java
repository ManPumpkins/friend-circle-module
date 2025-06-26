package com.g.friendcirclemodule.dp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FeedManager {
    private static SQLiteDatabase db;
    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }
    // int friendName, int friendHead, String decStr, Integer[] friendImageId, String time, Integer friendVideoId
    public static List<DMEntryBase>getTypeList(){
        List<DMEntryBase> list = new ArrayList<>();
        String sql = "SELECT * FROM accounttb ORDER BY id DESC";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            int friendName = cursor.getInt(cursor.getColumnIndexOrThrow("friendName"));
            int friendHead = cursor.getInt(cursor.getColumnIndexOrThrow("friendHead"));
            String decStr = cursor.getString(cursor.getColumnIndexOrThrow("decStr"));
            String friendImageId = cursor.getString(cursor.getColumnIndexOrThrow("friendImageId"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String friendVideoId = cursor.getString(cursor.getColumnIndexOrThrow("friendVideoId"));
            DMEntryBase typeBean = new DMEntryBase(id, friendName, friendHead, decStr, friendImageId, time, friendVideoId);
            list.add(typeBean);
        }
        return list;
    }
    /*
    表插入
     */
    public static void insertItemToAccounttb(DMEntryBase bean){
        ContentValues values = new ContentValues();
        values.put("id",bean.getId());
        values.put("friendName",bean.getFriendName());
        values.put("friendHead",bean.getFriendHead());
        values.put("decStr",bean.getDecStr());
        values.put("friendImageId",bean.getFriendImageId());
        values.put("time",bean.getTime());
        values.put("friendVideoId",bean.getFriendVideoId());
        db.insert("accounttb", null,values);
        Log.i("dataLog", "插入：" + values);
    }
}
