package cn.edu.shu.ankai;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import cn.edu.shu.ankai.utils.App;
import cn.edu.shu.ankai.utils.PreferenceUtils;

/**
 * Created by gxyzw_000 on 2015/7/13.
 */
public class FindLabActivity extends Activity{
    private  Spinner spinner1;
    private Spinner spinner2;
   private Spinner spinner3;
  private   String str1="1";
    private   String str2="2";
    private   String str3="3";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_lab);

        spinner1 =(Spinner)this.findViewById(R.id.spinner_lab1);
        spinner2 =(Spinner)this.findViewById(R.id.spinner_lab2);
        spinner3 =(Spinner)this.findViewById(R.id.spinner_lab3);

        spinner1.setSelection(1);
        spinner2.setSelection(1);
        spinner3.setSelection(1);

        ;




        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                TextView tx_spinner1 = (TextView) spinner1.getSelectedView();
                tx_spinner1.setTextColor(Color.BLUE);
                str1 = (String) tx_spinner1.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                TextView tx_spinner2 = (TextView) spinner2.getSelectedView();
                tx_spinner2.setTextColor(Color.BLUE);
                str2 = (String) tx_spinner2.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                TextView tx_spinner3 = (TextView) spinner3.getSelectedView();
                tx_spinner3.setTextColor(Color.BLUE);
                str3 = (String) tx_spinner3.getText();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        Button btnxuan = (Button) this.findViewById(R.id.btnxuan);
        btnxuan.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                PreferenceUtils.setPrefString(FindLabActivity.this,"find_parent",str1.substring(0,3));
                PreferenceUtils.setPrefString(FindLabActivity.this,"find_level",str2.substring(0,6));
                PreferenceUtils.setPrefString(FindLabActivity.this,"find_type",str3.substring(0,6));

                ((App) FindLabActivity.this.getApplication()).code=3;
                Log.e("空的2","1");
                Intent intent = new Intent(FindLabActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });






    }


}
