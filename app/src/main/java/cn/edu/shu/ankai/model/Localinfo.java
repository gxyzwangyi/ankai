package cn.edu.shu.ankai.model;

/**
 * Created by gxyzw_000 on 2015/3/29.
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.ankai.R;

public class Localinfo implements Serializable
{
    private static final long serialVersionUID = -1010711775392052966L;
    private double latitude;
    private double longitude;
    private int imgId;
    private String name;


    public static List<Localinfo> infos = new ArrayList<Localinfo>();


    static
    {
        infos.add(new Localinfo(31.231722, 121.411831, R.drawable.img_10,"海绵宝宝"
        ));
        infos.add(new Localinfo(31.314294, 121.394181,R.drawable.img_06, "章鱼哥"
        )       );
        infos.add(new Localinfo(31.314725, 121.395914,R.drawable.img_02, "派大星"
        ));
        infos.add(new Localinfo(31.314247, 121.395090,R.drawable.img_05,"小蜗"
                ));
    }

    public Localinfo(double latitude, double longitude, int imgId, String name
              )
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;

    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public int getImgId()
    {
        return imgId;
    }

    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }



}