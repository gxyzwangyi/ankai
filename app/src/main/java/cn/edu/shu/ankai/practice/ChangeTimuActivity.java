package cn.edu.shu.ankai.practice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

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

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.utils.PreferenceUtils;
import cn.edu.shu.ankai.utils.StreamTool;

/**
 * @author coolszy
 * @date 2012-3-8
 * @blog http://blog.92coding.com
 */
public class ChangeTimuActivity extends Activity
{
    private TextView mobileAddress;
    private static final String TAG = "MainActivity";
    public String json;
    String path;
    MaterialEditText shueidt;

    private static final int COMPLETED = 0;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                StreamTool.save(json, ChangeTimuActivity.this, "timu.txt");
               int length =  StreamTool.load(ChangeTimuActivity.this, "timu.txt").length();
                mobileAddress.setText("更新成功");
            }
        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timu_change);
        mobileAddress = (TextView) this.findViewById(R.id.mobileAddress);
         shueidt = (MaterialEditText) this.findViewById(R.id.shu);
         Button btnSearch = (Button) this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //		String mobile = mobileNum.getText().toString();
                JsonDownloadThread thread = new JsonDownloadThread();
                thread.start();

            }

        });

    }



    public class JsonDownloadThread extends Thread {
        public void run() {
            Looper.prepare();
            String mobile = "sdhr66135152";
            String dcode  =    PreferenceUtils.getPrefString(ChangeTimuActivity.this, "dcode", null);
            AVUser currentUser = AVUser.getCurrentUser();


            InputStream inStream = this.getClass().getResourceAsStream("/assets/timu.xml");
            try
            {
                Toast.makeText(ChangeTimuActivity.this, "201", Toast.LENGTH_LONG).show();
                json=getMobileAddress(inStream,mobile,currentUser.getClassName(),dcode,shueidt.getText().toString());

                Message msg = new Message();
                msg.what = COMPLETED;
                handler.sendMessage(msg);
                //mobileAddress.setText(getMobileAddress(inStream, mobile));

            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                Toast.makeText(ChangeTimuActivity.this, "", Toast.LENGTH_LONG).show();
            }

        }
    }

    private String getMobileAddress(InputStream inStream,String mobile,String user,String dcode, String shu) throws Exception
    {

        String soap = readSoapFile(inStream, mobile,user,dcode,shu);
        byte[] data = soap.getBytes();

        URL url = new URL("http://202.121.199.198/AnKaiAPPWebService/WebService_AnKaiDaTi.asmx");
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

    private String readSoapFile(InputStream inStream,String mobile,String user,String dcode, String shu) throws Exception
    {
        byte[] data = readInputStream(inStream);
        String soapxml = new String(data);

        Map<String, String> params = new HashMap<String, String>();
        params.put("access", mobile);
        params.put("user", user);
        params.put("dcode", dcode);
        params.put("shu", shu);

        return replace(soapxml, params);
    }




    private static byte[] readInputStream(InputStream inputStream) throws Exception
    {
        byte[] buffer = new byte[10485760];
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

    private String parseResponseXML(InputStream inStream) throws Exception
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
                    if ("GetQuestionInfosByDepartmentIdResult".equals(name))
                    {
                        return parser.nextText();
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;
    }
}