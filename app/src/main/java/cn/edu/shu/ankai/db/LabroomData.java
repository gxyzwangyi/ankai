package cn.edu.shu.ankai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cn.edu.shu.ankai.model.Labroom;

/**
 * Created by gxyzw_000 on 2015/7/12.
 */
public class LabroomData {

    private LabroomDataBaseHelper dbhelper = null;
    private SQLiteDatabase db = null;

    public LabroomData(Context context) {
        dbhelper = LabroomDataBaseHelper.getDBhelper(context);
        db = LabroomDataBaseHelper.opDatabase();
    }


    public long addNew(Labroom ub) {
        ContentValues values = new ContentValues();
        values.put("id", ub.getid());
        values.put("serialno",ub.getserialno());
        values.put("name",ub.getname());
        values.put("parent_id", ub.getparent_id());
        values.put("image", ub.getimage());
        values.put("lab_level", ub.getlab_level());
        values.put("lab_type",ub.getlab_type());
        values.put("open_flag",ub.getopen_flag());
        values.put("secrect_flag",ub.getsecrect_flag());
        values.put("description",ub.getdescription());
        values.put("research_fields",ub.getresearch_fields());
        values.put("director_id",ub.getdirector_id());
        values.put("contact_id",ub.getcontact_id());
        values.put("contact_phone",ub.getcontact_phone());
        values.put("web_url",ub.getweb_url());
        values.put("since_date",ub.getsince_date());

        return db.insert("labroom", null, values);
    }


    public ArrayList<HashMap<String, Object>> getUserList() {
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        Cursor c = db.query("labroom", null, null, null, "id",null , null);
        while (c.moveToNext()) {

            int _id = c.getInt(c.getColumnIndex("_id"));

            String id = c.getString(c.getColumnIndex("id"));
            String serialno = c.getString(c.getColumnIndex("serialno"));
            String name = c.getString(c.getColumnIndex("name"));
            String parent_id = c.getString(c.getColumnIndex("parent_id"));
            String image = c.getString(c.getColumnIndex("image"));
            String lab_level = c.getString(c.getColumnIndex("lab_level"));
            String lab_type = c.getString(c.getColumnIndex("lab_type"));
            String open_flag = c.getString(c.getColumnIndex("open_flag"));
            String secrect_flag = c.getString(c.getColumnIndex("secrect_flag"));
            String description = c.getString(c.getColumnIndex("description"));
            String research_fields = c.getString(c.getColumnIndex("research_fields"));
            String director_id = c.getString(c.getColumnIndex("director_id"));
            String contact_id = c.getString(c.getColumnIndex("contact_id"));
            String contact_phone = c.getString(c.getColumnIndex("contact_phone"));
            String web_url = c.getString(c.getColumnIndex("web_url"));
            String since_date = c.getString(c.getColumnIndex("since_date"));

            map = new HashMap<String, Object>();
            map.put("_id", _id + "");
            map.put("id", id + "");
            map.put("serialno",serialno+ "");
            map.put("name",name);
            map.put("parent_id",parent_id+ "");
            map.put("image",image+ "");
            map.put("lab_level", lab_level);
            map.put("lab_type", lab_type);
            map.put("open_flag", open_flag);
            map.put("secrect_flag",secrect_flag);
            map.put("description",description);
            map.put("research_fields",research_fields);
            map.put("director_id",director_id);
            map.put("contact_id",contact_id);
            map.put("contact_phone",contact_phone);
            map.put("web_url",web_url);
            map.put("since_date", since_date);
            al.add(map);

        }
        return al;
    }

    public ArrayList<HashMap<String, Object>> getSpecialrList(String Factor) {
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
    //    Cursor c = db.query("labroom", null, "id=? or serialno=? or  name=? or lab_level=?", new String[]{Factor,Factor,Factor,Factor}, null, null, null);

        Cursor c = db.query("labroom", null, "id LIKE ?  or serialno LIKE ?  or name LIKE ?  or director_id LIKE ? ", new String[]{"%"+Factor+"%","%"+Factor+"%","%"+Factor+"%","%"+Factor+"%"}, "id", null, "id");

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String id = c.getString(c.getColumnIndex("id"));
            String serialno = c.getString(c.getColumnIndex("serialno"));
            String name = c.getString(c.getColumnIndex("name"));
            String parent_id = c.getString(c.getColumnIndex("parent_id"));
            String image = c.getString(c.getColumnIndex("image"));
            String lab_level = c.getString(c.getColumnIndex("lab_level"));
            String lab_type = c.getString(c.getColumnIndex("lab_type"));
            String open_flag = c.getString(c.getColumnIndex("open_flag"));
            String secrect_flag = c.getString(c.getColumnIndex("secrect_flag"));
            String description = c.getString(c.getColumnIndex("description"));
            String research_fields = c.getString(c.getColumnIndex("research_fields"));
            String director_id = c.getString(c.getColumnIndex("director_id"));
            String contact_id = c.getString(c.getColumnIndex("contact_id"));
            String contact_phone = c.getString(c.getColumnIndex("contact_phone"));
            String web_url = c.getString(c.getColumnIndex("web_url"));
            String since_date = c.getString(c.getColumnIndex("since_date"));

            map = new HashMap<String, Object>();
            map.put("_id", _id + "");
            map.put("id", id + "");
            map.put("serialno",serialno+ "");
            map.put("name",name);
            map.put("parent_id",parent_id+ "");
            map.put("image",image+ "");
            map.put("lab_level", lab_level);
            map.put("lab_type", lab_type);
            map.put("open_flag", open_flag);
            map.put("secrect_flag",secrect_flag);
            map.put("description",description);
            map.put("research_fields",research_fields);
            map.put("director_id",director_id);
            map.put("contact_id",contact_id);
            map.put("contact_phone",contact_phone);
            map.put("web_url",web_url);
            map.put("since_date", since_date);
            al.add(map);


        }
        return al;
    }


    public ArrayList<HashMap<String, Object>> getRandomrList( ) {
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        //    Cursor c = db.query("labroom", null, "id=? or serialno=? or  name=? or lab_level=?", new String[]{Factor,Factor,Factor,Factor}, null, null, null);
        Random r = new Random();
        int n = r.nextInt(399);
        int n1 = r.nextInt(399);
        int n2 = r.nextInt(399);
       // Log.e("雷登",n+"");
        Cursor c = db.query("labroom", null, "_id=?",new String[]{n+""}, "id", null, "id");
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String id = c.getString(c.getColumnIndex("id"));
            String serialno = c.getString(c.getColumnIndex("serialno"));
            String name = c.getString(c.getColumnIndex("name"));
            String parent_id = c.getString(c.getColumnIndex("parent_id"));
            String image = c.getString(c.getColumnIndex("image"));
            String lab_level = c.getString(c.getColumnIndex("lab_level"));
            String lab_type = c.getString(c.getColumnIndex("lab_type"));
            String open_flag = c.getString(c.getColumnIndex("open_flag"));
            String secrect_flag = c.getString(c.getColumnIndex("secrect_flag"));
            String description = c.getString(c.getColumnIndex("description"));
            String research_fields = c.getString(c.getColumnIndex("research_fields"));
            String director_id = c.getString(c.getColumnIndex("director_id"));
            String contact_id = c.getString(c.getColumnIndex("contact_id"));
            String contact_phone = c.getString(c.getColumnIndex("contact_phone"));
            String web_url = c.getString(c.getColumnIndex("web_url"));
            String since_date = c.getString(c.getColumnIndex("since_date"));

            map = new HashMap<String, Object>();
            map.put("_id", _id + "");
            map.put("id", id + "");
            map.put("serialno",serialno+ "");
            map.put("name",name);
            map.put("parent_id",parent_id+ "");
            map.put("image",image+ "");
            map.put("lab_level", lab_level);
            map.put("lab_type", lab_type);
            map.put("open_flag", open_flag);
            map.put("secrect_flag",secrect_flag);
            map.put("description",description);
            map.put("research_fields",research_fields);
            map.put("director_id",director_id);
            map.put("contact_id",contact_id);
            map.put("contact_phone",contact_phone);
            map.put("web_url",web_url);
            map.put("since_date", since_date);
            al.add(map);
        }
        return al;
    }

    public ArrayList<HashMap<String, Object>> getshaiList(String Factor,String Factor1,String Factor2) {
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        //    Cursor c = db.query("labroom", null, "id=? or serialno=? or  name=? or lab_level=?", new String[]{Factor,Factor,Factor,Factor}, null, null, null);

        Cursor c = db.query("labroom", null, "parent_id LIKE ?  and lab_level LIKE ?  and lab_type LIKE ? ", new String[]{"%"+Factor+"%","%"+Factor1+"%","%"+Factor2+"%"}, "id", null, "id");

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String id = c.getString(c.getColumnIndex("id"));
            String serialno = c.getString(c.getColumnIndex("serialno"));
            String name = c.getString(c.getColumnIndex("name"));
            String parent_id = c.getString(c.getColumnIndex("parent_id"));
            String image = c.getString(c.getColumnIndex("image"));
            String lab_level = c.getString(c.getColumnIndex("lab_level"));
            String lab_type = c.getString(c.getColumnIndex("lab_type"));
            String open_flag = c.getString(c.getColumnIndex("open_flag"));
            String secrect_flag = c.getString(c.getColumnIndex("secrect_flag"));
            String description = c.getString(c.getColumnIndex("description"));
            String research_fields = c.getString(c.getColumnIndex("research_fields"));
            String director_id = c.getString(c.getColumnIndex("director_id"));
            String contact_id = c.getString(c.getColumnIndex("contact_id"));
            String contact_phone = c.getString(c.getColumnIndex("contact_phone"));
            String web_url = c.getString(c.getColumnIndex("web_url"));
            String since_date = c.getString(c.getColumnIndex("since_date"));

            map = new HashMap<String, Object>();
            map.put("_id", _id + "");
            map.put("id", id + "");
            map.put("serialno",serialno+ "");
            map.put("name",name);
            map.put("parent_id",parent_id+ "");
            map.put("image",image+ "");
            map.put("lab_level", lab_level);
            map.put("lab_type", lab_type);
            map.put("open_flag", open_flag);
            map.put("secrect_flag",secrect_flag);
            map.put("description",description);
            map.put("research_fields",research_fields);
            map.put("director_id",director_id);
            map.put("contact_id",contact_id);
            map.put("contact_phone",contact_phone);
            map.put("web_url",web_url);
            map.put("since_date", since_date);
            al.add(map);

            Log.e("数据库", "1");
        }
        return al;
    }




    public ArrayList<HashMap<String, Object>> getIDList(String Factor) {
        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;
        //    Cursor c = db.query("labroom", null, "id=? or serialno=? or  name=? or lab_level=?", new String[]{Factor,Factor,Factor,Factor}, null, null, null);

        Cursor c = db.query("labroom", null, "id=?", new String[]{Factor}, "id", null, "id");

        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String id = c.getString(c.getColumnIndex("id"));
            String serialno = c.getString(c.getColumnIndex("serialno"));
            String name = c.getString(c.getColumnIndex("name"));
            String parent_id = c.getString(c.getColumnIndex("parent_id"));
            String image = c.getString(c.getColumnIndex("image"));
            String lab_level = c.getString(c.getColumnIndex("lab_level"));
            String lab_type = c.getString(c.getColumnIndex("lab_type"));
            String open_flag = c.getString(c.getColumnIndex("open_flag"));
            String secrect_flag = c.getString(c.getColumnIndex("secrect_flag"));
            String description = c.getString(c.getColumnIndex("description"));
            String research_fields = c.getString(c.getColumnIndex("research_fields"));
            String director_id = c.getString(c.getColumnIndex("director_id"));
            String contact_id = c.getString(c.getColumnIndex("contact_id"));
            String contact_phone = c.getString(c.getColumnIndex("contact_phone"));
            String web_url = c.getString(c.getColumnIndex("web_url"));
            String since_date = c.getString(c.getColumnIndex("since_date"));

            map = new HashMap<String, Object>();
            map.put("_id", _id + "");
            map.put("id", id + "");
            map.put("serialno",serialno+ "");
            map.put("name",name);
            map.put("parent_id",parent_id+ "");
            map.put("image",image+ "");
            map.put("lab_level", lab_level);
            map.put("lab_type", lab_type);
            map.put("open_flag", open_flag);
            map.put("secrect_flag",secrect_flag);
            map.put("description",description);
            map.put("research_fields",research_fields);
            map.put("director_id",director_id);
            map.put("contact_id",contact_id);
            map.put("contact_phone",contact_phone);
            map.put("web_url",web_url);
            map.put("since_date", since_date);
            al.add(map);
            Log.e("艾迪1","1");
        }
        return al;
    }











    public boolean delete() {
        boolean b = false;

        int num = db.delete("labroom", "_id>?", new String[]{"0"});
        if (num == 1) {
            b = true;
        }
        return b;
    }
}
