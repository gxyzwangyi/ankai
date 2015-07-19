package cn.edu.shu.ankai.model;

import java.io.Serializable;

/**
 * Created by gxyzw_000 on 2015/7/15.
 */
public class Device implements Serializable {
    private static final long serialVersionUID = -1010711775392052966L;
    String id;
    String serialno ;
    String name  ;
    String eng_name ;
    String location_id;
    String device_model ;
    String device_metric ;
    String application_code ;
    String use_department_id ;
    String director_id ;
    String contact_id ;

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }


    public String getserialno() {
        return serialno;
    }

    public void setserialno(String serialno) {
        this.serialno = serialno;
    }



    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }



    public String geteng_name() {
        return eng_name;
    }

    public void seteng_name(String eng_name) {
        this.eng_name = eng_name;
    }



    public String getlocation_id() {
        return location_id;
    }

    public void setlocation_id(String location_id) {
        this.location_id = location_id;
    }



    public String getdevice_model() {
        return device_model;
    }

    public void setdevice_model(String device_model) {
        this.device_model = device_model;
    }



    public String getdevice_metric() {
        return device_metric;
    }

    public void setdevice_metric(String device_metric) {
        this.device_metric = device_metric;
    }




    public String getapplication_code() {
        return application_code;
    }

    public void setapplication_code(String application_code) {
        this.application_code = application_code;
    }



    public String getuse_department_id() {
        return use_department_id;
    }

    public void setuse_department_id(String use_department_id) {
        this.use_department_id = use_department_id;
    }



    public String getdirector_id() {
        return director_id;
    }

    public void setdirector_id(String director_id) {
        this.director_id = director_id;
    }



    public String getcontact_id() {
        return contact_id;
    }

    public void setcontact_id(String contact_id) {
        this.contact_id = contact_id;
    }














}
