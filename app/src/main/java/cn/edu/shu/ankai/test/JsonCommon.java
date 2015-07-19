package cn.edu.shu.ankai.test;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gxyzw_000 on 2015/7/15.
 */
public class JsonCommon {



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

    public static String replace(String xml, Map<String, String> params) throws Exception
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

    public static String parseResponseXML(InputStream inStream,String getname) throws Exception
    {Log.e("步骤","4");
        XmlPullParser parser = Xml.newPullParser();
        Log.e("步骤","5" +
                "");
        parser.setInput(inStream, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {

                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    Log.e("奈姆", name);
                    if (getname.equals(name))
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
