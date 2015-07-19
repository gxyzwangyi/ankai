package cn.edu.shu.ankai;

/**
 * Created by gxyzw_000 on 2015/4/16.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.shu.ankai.model.Device;
import cn.edu.shu.ankai.utils.StreamTool;


public class ProgressActivity1 extends Activity {


    public String StringE;
    public String json;
    private static final int COMPLETED = 0;
    public ProgressDialog dialog;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_activity_progress);
        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
        findViewById(R.id.progressBarCircularIndetermininate).setBackgroundColor(color);
        Intent intent=getIntent();
        StringE=intent.getStringExtra("lab_id");


        JsonDownloadThread thread = new JsonDownloadThread();
        thread.start();

    }





    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                StreamTool.save(json, ProgressActivity1.this, "device.txt");
                //mobileAddress.setText("更新成功");
                //    dialog.dismiss();


                Intent intent=new Intent();
                intent.putExtra("lab_id",StringE);
                intent.putExtra("device", getData().toArray());
                intent.setClass(ProgressActivity1.this, SlidingUpRecyclerViewActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };



    public class JsonDownloadThread extends Thread {
        public void run() {
            Looper.prepare();
            String mobile = "004VCg2w8LzebX5j";
            InputStream inStream = this.getClass().getResourceAsStream("/assets/device.xml");
            try
            {
                Log.e("建松", "1");
                Toast.makeText(ProgressActivity1.this, "201", Toast.LENGTH_LONG).show();
                json=getMobileAddress(inStream, mobile,StringE);
                Log.e("建松", json);
                Message msg = new Message();
                msg.what = COMPLETED;
                handler.sendMessage(msg);
                //mobileAddress.setText(getMobileAddress(inStream, mobile));

            } catch (Exception e)
            {
                Log.e("123", e.toString());
            }

        }
    }

    public String getMobileAddress(InputStream inStream, String mobile,String lab) throws Exception
    {

        String soap = readSoapFile(inStream, mobile,lab);
        byte[] data = soap.getBytes();
        Log.e("建松", "2");
        URL url = new URL("http://202.121.199.183:8001/WebServiceForRealTimeMonitor.asmx");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(10 * 1000);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200)
        {
            Toast.makeText(this, "200", Toast.LENGTH_LONG).show();
            InputStream Stream = conn.getInputStream();

            return parseResponseXML(Stream);
        }
        Toast.makeText(this, "202", Toast.LENGTH_LONG).show();
        return conn.getResponseCode()+"";
    }

    private String readSoapFile(InputStream inStream, String mobile,String lab) throws Exception
    {
        byte[] data = readInputStream(inStream);
        String soapxml = new String(data);

        Map<String, String> params = new HashMap<String, String>();
        params.put("access", mobile);
        params.put("lab", lab);

        return replace(soapxml, params);
    }




    public static byte[] readInputStream(InputStream inputStream) throws Exception
    {
        byte[] buffer = new byte[1048576];
        int len = -1;
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1)
        {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inputStream.close();
        return outSteam.toByteArray();
    }

    public String replace(String xml, Map<String, String> params) throws Exception
    {
        String result = xml;
        if (params != null && !params.isEmpty())
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String name = "\\$" + entry.getKey();
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(result);
                if (matcher.find())
                {
                    result = matcher.replaceAll(entry.getValue());
                }
            }
        }
        return result;
    }

    public String parseResponseXML(InputStream inStream) throws Exception
    {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {

            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    Log.e("建松", name);
                    if ("LabroomGetDeviceResult".equals(name))
                    {
                        return parser.nextText();
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;
    }


    private List<Device> getData() {
        List<Device> list = new ArrayList();

        json = StreamTool.load(ProgressActivity1.this, "device.txt");
        Log.e("请问",json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                String content = jsonObject.getString("content");
                JSONArray array = new JSONArray(content);
                Log.e("请问1",content.length()+"");
                Log.e("请问2",array.length()+"");
                for (int i = 0; i < array.length(); i++) {
                    Log.e("请问3",i+"");
                    JSONObject object = array.getJSONObject(i);
                    String id = object.getString("id");
                    String serialno = object.getString("serialno");
                    String name  = object.getString("name");
                    String eng_name = object.getString("eng_name");
                    String location_id = object.getString("location_id");
                    String device_model = object.getString("device_model");
                    String device_metric = object.getString("device_metric");
                    String application_code = object.getString("application_code");
                    String use_department_id = object.getString("use_department_id");
                    String director_id = object.getString("director_id");
                    String contact_id = object.getString("contact_id");

                    Device device = new Device();
                    device.setid(id);
                    device.setserialno(serialno);
                    device.setname(name);
                    device.seteng_name(eng_name);
                    device.setlocation_id(location_id);
                    device.setdevice_model(device_model);
                    device.setdevice_metric(device_metric);
                    device.setapplication_code(application_code);
                    device.setuse_department_id(use_department_id);
                    device.setdirector_id(director_id);
                    device.setcontact_id(contact_id);

                    list.add(device);
                }
            } else {
                Toast.makeText(ProgressActivity1.this, "数据解析出现异常，请联系管理员", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("请问4",e.getMessage());
        }
        return list;
    }




}
