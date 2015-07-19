package cn.edu.shu.ankai.model;

import com.baidu.location.BDLocation;

/**
 * Created by yy on 3/24/15.
 */
public class Location {
    private float latitude; // 纬度
    private float longitude; // 经度
    private float radius; // 定位精度半径，单位：米
    private String Addr;
    public Location(){

    }

    public Location(BDLocation location){
        this.latitude = (float)location.getLatitude();
        this.longitude = (float)location.getLongitude();
        this.radius = location.getRadius();
        this.Addr=location.getAddrStr();
    }




    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String Addr) {
        this.Addr = Addr;
    }





}
