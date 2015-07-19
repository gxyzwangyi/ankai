
package cn.edu.shu.ankai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.shu.ankai.model.History;

public class HistoryData {

    private HistoryDataBaseHelper dbhelper = null;
    private SQLiteDatabase db = null;


    public HistoryData(Context context) {

        dbhelper = HistoryDataBaseHelper.getDBhelper(context);
        db = HistoryDataBaseHelper.opDatabase();
    }


    public long addNew(History ub) {
        ContentValues values = new ContentValues();
        values.put("imageId", ub.getImageId());
        values.put("name",ub.getName());
        values.put("title", ub.getTitle());
        values.put("show_time", ub.getTime());
        values.put("local", ub.getLocal());
        values.put("ll",ub.getLL());

        return db.insert("qianhistory", null, values);
    }


    public ArrayList<HashMap<String, Object>> getUserList() {
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        Cursor c = db.query("qianhistory", null, null, null, null, null, null,null);
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            int imageId = c.getInt(c.getColumnIndex("imageId"));
            String name = c.getString(c.getColumnIndex("name"));
            String title = c.getString(c.getColumnIndex("title"));
            String show_time = c.getString(c.getColumnIndex("show_time"));
            String local = c.getString(c.getColumnIndex("local"));
            String ll = c.getString(c.getColumnIndex("ll"));


            map = new HashMap<String, Object>();
            map.put("_id", _id + "");
            map.put("imageId", imageId + "");
            map.put("name",name+ "");
            map.put("title", title);
            map.put("show_time", show_time);
            map.put("local", local);
            map.put("ll",ll);
            al.add(map);
        }

        return al;
    }


    public int updateUser(History ub) {
        ContentValues values = new ContentValues();
        values.put("imageId", ub.getImageId() + "");
        values.put("name",ub.getName()+"");
        values.put("title", ub.getTitle());
        values.put("show_time", ub.getTime());
        values.put("local", ub.getLocal());
        values.put("ll",ub.getLL());
        return db.update("qianhistory", values, "_id=?", new String[]{ub.get_id() + ""});
    }

    public boolean delete() {
        boolean b = false;

        int num = db.delete("qianhistory", "_id>?", new String[]{"0"});
        if (num == 1) {
            b = true;
        }
        return b;
    }

}

