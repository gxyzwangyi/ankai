package cn.edu.shu.ankai.model;

import java.io.Serializable;

/**
 * Created by gxyzw_000 on 2015/3/25.
 */
public class History implements Serializable {
    private static final long serialVersionUID = 1L;
    private int _id;
    private int imageId;
    private String name;
    private String title;
    private String show_time;
    private String local;
    private String ll;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName(){ return name; }
    public void setName(String name){this.name = name;}


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return show_time;
    }

    public void setTime(String show_time) {
        this.show_time = show_time;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLL() {
        return ll;
    }

    public void setLL(String ll) {
        this.ll = ll;
    }



}
