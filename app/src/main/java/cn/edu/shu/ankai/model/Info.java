package cn.edu.shu.ankai.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.shu.ankai.db.HistoryData;

public class Info implements Serializable
{
	private static final long serialVersionUID = -1010711775392052966L;
	private double latitude;
	private double longitude;
	private String name;
    private ArrayList<HashMap<String, Object>> historylist = new ArrayList<HashMap<String, Object>>();
    private HistoryData HD;

	public static List<Info> infos = new ArrayList<Info>();







	public Info(double latitude, double longitude,String name
			)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;

	}

	public double getLatitude()	{return latitude;	}

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


}
