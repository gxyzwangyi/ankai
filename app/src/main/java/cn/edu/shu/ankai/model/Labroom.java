package cn.edu.shu.ankai.model;

import java.io.Serializable;

/**
 * Created by gxyzw_000 on 2015/7/11.
 */
public class Labroom implements Serializable {
    private static final long serialVersionUID = 1L;
    private int _id;
    private String id ;
    private  String serialno ;
    private  String name;
    private  String parent_id ;
    private String image ;
    private String lab_level;
    private String lab_type ;
    private String open_flag ;
    private  String secrect_flag ;
    private  String description ;
    private String research_fields ;
    private String director_id;
    private  String contact_id ;
    private  String contact_phone ;
    private String web_url ;
    private String since_date ;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public  String getid() {
        return id;
    }
    public void setid(String id){
        this.id = id;
    }

    public  String getserialno() {
        return serialno;
    }
    public void setserialno(String serialno){
        this.serialno = serialno;
    }


    public  String getname() {
        return name;
    }
    public void setname(String name){
        this.name = name;
    }

    public  String getparent_id() {
        return parent_id;
    }
    public void setparent_id(String parent_id){
        this.parent_id = parent_id;
    }



    public  String getlab_level() {
        return lab_level;
    }
    public void setlab_level(String lab_level){
        this.lab_level = lab_level;
    }

    public  String getlab_type() {
        return lab_type;
    }
    public void setlab_type(String lab_type){
        this.lab_type = lab_type;
    }



    public  String getimage() {
        return image;
    }
    public void setimage(String image){
        this.image = image;
    }

    public  String getopen_flag() {
        return open_flag;
    }
    public void setopen_flag(String open_flag){
        this.open_flag = open_flag;
    }



    public  String getsecrect_flag() {
        return secrect_flag;
    }
    public void setsecrect_flag(String secrect_flag){
        this.secrect_flag = secrect_flag;
    }



    public  String getdescription() {
        return description;
    }
    public void setdescription(String description){
        this.description = description;
    }



    public  String getresearch_fields() {
        return research_fields;
    }
    public void setresearch_fields(String research_fields){
        this.research_fields = research_fields;
    }




    public  String getdirector_id() {
        return director_id;
    }
    public void setdirector_id(String director_id){
        this.director_id = director_id;
    }


    public  String getcontact_id() {
        return contact_id;
    }
    public void setcontact_id(String contact_id){
        this.contact_id = contact_id;
    }




    public  String getcontact_phone() {
        return contact_phone;
    }
    public void setcontact_phone(String contact_phone){
        this.contact_phone = contact_phone;
    }




    public  String getweb_url() {
        return web_url;
    }
    public void setweb_url(String web_url){
        this.web_url = web_url;
    }

    public  String getsince_date() {
        return since_date;
    }
    public void setsince_date(String since_date){
        this.since_date = since_date;
    }







}