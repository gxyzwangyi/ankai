package cn.edu.shu.ankai.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gxyzw_000 on 2015/7/12.
 */
public class LabroomDataBaseHelper extends SQLiteOpenHelper {

    public static LabroomDataBaseHelper dbhelper;
    public static SQLiteDatabase db;

    public LabroomDataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        // TODO Auto-generated constructor stub
    }

    public LabroomDataBaseHelper(Context context, String name) {
        super(context, name, null, 1);
        // TODO Auto-generated constructor stub
    }

    public LabroomDataBaseHelper(Context context) {
        super(context, "labroom_info2", null, 1);
        // TODO Auto-generated constructor stub
    }

    public static LabroomDataBaseHelper getDBhelper(Context c) {
        if (dbhelper == null) {
            dbhelper = new LabroomDataBaseHelper(c);
        }
        return dbhelper;
    }

    public static SQLiteDatabase opDatabase() {

        if (db == null) {
            db = dbhelper.getWritableDatabase();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table labroom (_id Integer primary key autoincrement,id varchar(20),serialno varchar(20),name varchar(20),parent_id varchar(20),image varchar(20),lab_level varchar(20),lab_type varchar(20),open_flag varchar(20),secrect_flag varchar(20),description varchar(20),research_fields varchar(20),director_id varchar(20),contact_id varchar(20)," +
                "contact_phone varchar(20),web_url varchar(20),since_date varchar(20))");

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

