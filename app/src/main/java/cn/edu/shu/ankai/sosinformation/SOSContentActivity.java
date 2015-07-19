package cn.edu.shu.ankai.sosinformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.JustifiedSpan;
import com.bluejamesbond.text.style.LeftSpan;

import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.ui.textviewhelp.ArticleBuilder;
import cn.edu.shu.ankai.ui.textviewhelp.TestActivity;


/**
 * Created by gxyzw_000 on 2015/3/27.
 */





public class SOSContentActivity extends TestActivity {

    private String sos_help;
    private String sos_help_name;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);



            SharedPreferences sp = getSharedPreferences("soshelp", Context.MODE_WORLD_READABLE);

            sos_help = sp.getString("help","未加载");
            sos_help_name= sp.getString("help_name","不知道");

            TextView titleBar = ((TextView) findViewById(R.id.titlebar));

            titleBar.setText(sos_help_name);


            ArticleBuilder amb = new ArticleBuilder();
            amb.append(sos_help_name, false, new RelativeSizeSpan(2f), new StyleSpan(Typeface.BOLD),
                    new LeftSpan());
            amb.append("<font color=0xFFC801>@ankai-wangyi</font><font color=0x33B5E5> 7.18.2015</font>",
                    true, new RelativeSizeSpan(0.8f), new StyleSpan(Typeface.BOLD));


            String[] strs  =  sos_help.split(" ");


            for(int i = 0;i<strs.length;i++) {
                amb.append(
                        strs[i],
                        true, new RelativeSizeSpan(1.3f), new JustifiedSpan());
            }




            addDocumentView(amb, DocumentView.FORMATTED_TEXT);
        }


}
