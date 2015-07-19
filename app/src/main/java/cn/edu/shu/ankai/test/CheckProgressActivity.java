package cn.edu.shu.ankai.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.shu.ankai.MainActivity;
import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.db.HistoryData;
import cn.edu.shu.ankai.model.History;
import cn.edu.shu.ankai.utils.StreamTool;


public class CheckProgressActivity extends Activity
{
    private TextView mobileAddress;
    private static final String TAG = "MainActivity";
    public String json;
    private HistoryData HD;
    private static final int COMPLETED = 0;
    AVUser currentUser = AVUser.getCurrentUser();
    String user;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                StreamTool.save(json, CheckProgressActivity.this, "qiandao.txt");
                mobileAddress.setText("更新成功");
                asynctaskInstance();
                Intent intent = new Intent(CheckProgressActivity.this, MainActivity.class);
                startActivity(intent);
                CheckProgressActivity.this.finish();
            }
        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=CheckProgressActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.test);
        HD = new HistoryData(this);


        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
        findViewById(R.id.progressBarCircularIndetermininate).setBackgroundColor(color);

        mobileAddress = (TextView) this.findViewById(R.id.mobileAddress);

        if(StreamTool.load(CheckProgressActivity.this, "qiandao.txt").equals("error")||!StreamTool.load(CheckProgressActivity.this, "qiandao.txt").substring(2,6).equals("code")) {
            JsonDownloadThread thread = new JsonDownloadThread();
            thread.start();
        }
        else{
            Intent intent = new Intent(CheckProgressActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }




    public class JsonDownloadThread extends Thread {
        public void run() {
            Looper.prepare();
            String mobile = "sdhr66135152";
            InputStream inStream = this.getClass().getResourceAsStream("/assets/qiandao_d.xml");
            try
            {
                user=currentUser.getUsername();
                Toast.makeText(CheckProgressActivity.this, "201", Toast.LENGTH_LONG).show();
                json=getMobileAddress(inStream, mobile,user);
                Message msg = new Message();
                msg.what = COMPLETED;
                handler.sendMessage(msg);
                //mobileAddress.setText(getMobileAddress(inStream, mobile));
                //Log.e("嘿嘿哦",getMobileAddress(inStream, mobile));
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                Toast.makeText(CheckProgressActivity.this, "查询失败", Toast.LENGTH_LONG).show();
            }
        }
    }




    /**
     * 获取电话号码地理位置
     *
     * @param inStream
     * @param mobile
     * @return
     * @throws Exception
     */
    private String getMobileAddress(InputStream inStream, String mobile,String user) throws Exception
    {
        // 替换xml文件中的电话号码
        String soap = readSoapFile(inStream, mobile,user);
        byte[] data = soap.getBytes();
        // 提交Post请求
        URL url = new URL("http://202.121.199.198/AnkaiAPPWebService/WebService_AnKaiDaTi.asmx");
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
        Log.e("嘿嘿",conn.getResponseCode()+"");
        return conn.getResponseCode()+"";
    }

    private String readSoapFile(InputStream inStream, String mobile,String user) throws Exception
    {
        // 从流中获取文件信息
        byte[] data = readInputStream(inStream);
        String soapxml = new String(data);
        // 占位符参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", mobile);
        params.put("user",user);
        // 替换文件中占位符
        return replace(soapxml, params);
    }

    /**
     * 读取流信息
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inputStream) throws Exception
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

    /**
     * 替换文件中占位符
     *
     * @param xml
     * @param params
     * @return
     * @throws Exception
     */
    private String replace(String xml, Map<String, String> params) throws Exception
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

    /**
     * 解析XML文件
     * @param inStream
     * @return
     * @throws Exception
     */
    private String parseResponseXML(InputStream inStream) throws Exception
    {
        XmlPullParser parser = Xml.newPullParser();

        parser.setInput(inStream, "UTF-8");

        int eventType = parser.getEventType();// 产生第一个事件
        Log.e("嘿嘿","2");
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            Log.e("嘿嘿","1");
            // 只要不是文档结束事件
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();// 获取解析器当前指向的元素的名称
                    if ("GetAppSignInInfoResult".equals(name))
                    {
                        Log.e("嘿嘿1",parser.getName());
                        return parser.nextText();
                    }
                    Log.e("嘿嘿2",parser.getName());
                    break;
            }
            eventType = parser.next();
        }
        return null;
    }



    private void asynctaskInstance() {
        AsyncHttpClient client = new AsyncHttpClient();
        HD.delete();
        json = StreamTool.load(CheckProgressActivity.this, "qiandao.txt");
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 1) {
                String content = jsonObject.getString("content");
                JSONArray array = new JSONArray(content);
                for (int i = 0; i < content.length(); i++) {
                    Log.e("请问3","1");
                    JSONObject object = array.getJSONObject(i);
                    String UserAccount = object.getString("UserAccount");
                    String Description  = object.getString("Description");
                    String Location = object.getString("Location");
                    String Longitudes = object.getString("Longitudes");
                    String ClassRoom = object.getString("ClassRoom");
                    String time = object.getString("time");


                    History history = new History();



                    history.setName(UserAccount);
                    history.setTitle(Description);
                    history.setLocal(Location + ClassRoom);
                    history.setLL(Longitudes);
                    history.setTime(time);



                    HD.addNew(history);

                }
            } else {
                Toast.makeText(CheckProgressActivity.this, "数据解析出现异常，请联系管理员", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e("请问", e.getMessage());

        }
    }















}