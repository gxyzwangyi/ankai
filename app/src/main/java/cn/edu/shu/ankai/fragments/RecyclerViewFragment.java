package cn.edu.shu.ankai.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.avos.avoscloud.AVUser;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.melnykov.fab.FloatingActionButton;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.shu.ankai.AddActivity1;
import cn.edu.shu.ankai.ComplainActivity;
import cn.edu.shu.ankai.MainActivity;
import cn.edu.shu.ankai.R;
import cn.edu.shu.ankai.db.HistoryData;
import cn.edu.shu.ankai.dialog.CityDialog;
import cn.edu.shu.ankai.model.History;
import cn.edu.shu.ankai.test.JsonCommon;
import cn.edu.shu.ankai.ui.adapter.TestRecyclerViewAdapter;
import cn.edu.shu.ankai.utils.App;
import cn.edu.shu.ankai.utils.StreamTool;

/**
     * Created by florentchampigny on 24/04/15.
     */
    public class RecyclerViewFragment extends Fragment {
        private List<FloatingActionMenu> menus = new ArrayList<>();
    public String profilelocal = "定位没成功";
    private String profilelolLL = "定位经纬度没成功";
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        FloatingActionButton floatingActionButton;
        private com.github.clans.fab.FloatingActionButton fab1;
        private com.github.clans.fab.FloatingActionButton fab2;
        private com.github.clans.fab.FloatingActionButton fab3;
        private HistoryData HD;
    AVUser currentUser = AVUser.getCurrentUser();
    private EditText passwordInput;
    private Handler mUiHandler = new Handler();
    private App app;
    private ArrayList<HashMap<String, Object>> historylist = new ArrayList<HashMap<String, Object>>();
    private View positiveAction;
    private  TextView  stv;
    private CityDialog dialog1;

    private static final int COMPLETED = 0;
    public String json;
    private String sp1_str;
    private String sp2_str;
    String str;
    String str1;
    String str2;
    String str3;
    String str4;

        public static RecyclerViewFragment newInstance() {
            return new RecyclerViewFragment();
        }
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_recyclerview, container, false);
        }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //本地数据库
        HD = new HistoryData(getActivity());
        historylist = HD.getUserList();

        ((App) getActivity().getApplication()).locationClient.requestLocation();
        app = (App) getActivity().getApplication();


        FloatingActionMenu menuLabelsRight = (FloatingActionMenu)view.findViewById(R.id.menu_labels_right);
        menus.add(menuLabelsRight);
        menuLabelsRight.hideMenuButton(false);


//浮动button
        fab1 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab2);
        fab3 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab3);
        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialog = inflater.inflate(R.layout.dialog_customview, null);







        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }


        if (app.getCurrentLocation() != null&&!AddActivity1.isConnectionAvailable(getActivity())) {
            profilelocal = app.getCurrentLocation().getAddr();
            profilelolLL = app.getCurrentLocation().getLatitude() + "," + app.getCurrentLocation().getLongitude();
            Log.v("我的", profilelocal + profilelolLL);
        }








        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(getData1()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }



    private List<History> getData1() {
        List<History> list = new ArrayList();

        History history=new History();
        history.setName("用户名");
        history.setTitle("课程");
        history.setTime("时间");
        history.setLocal("地点");
        history.setLL("经纬度");
        list.add(history);

if (historylist.size()>0) {
    for (int i = historylist.size()-1; i >=0 ; i--) {
        history = new History();
        history.setName(historylist.get(i).get("name").toString());
        history.setTitle(historylist.get(i).get("title").toString());
        history.setTime(historylist.get(i).get("show_time").toString());
        history.setLocal(historylist.get(i).get("local").toString());
        history.setLL(historylist.get(i).get("ll").toString());
        list.add(history);
    }
}
        return list;
    }






        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";

                switch (v.getId()) {
                    case R.id.fab1:
                        text = fab1.getLabelText();
                        Intent intent = new Intent(getActivity(),AddActivity1.class);
                        startActivity(intent);
                        break;
                    case R.id.fab2:
                        text = fab2.getLabelText();
                        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                                .title("你好" + currentUser.getUsername())
                                .customView(R.layout.dialog_customview, true)
                                .positiveText(R.string.connect)
                                .negativeText(android.R.string.cancel)
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                       // Toast.makeText(getApplicationContext(), "Password: " + passwordInput.getText().toString(), Toast.LENGTH_LONG);

                                        // 获取用户添加足迹的输入内容
                                         str = passwordInput.getText().toString();
                                         str3 = profilelocal;
                                         str4 = profilelolLL;


                                        sp1_str=stv.getText().toString();
                                        Log.e("埃斯皮",sp1_str);
                                      String  sp1_str1= StreamTool.utf8(sp1_str);
                                        Log.e("埃斯皮1",sp1_str1);

                                      try {
                                        String sp1_str2= StreamTool.getUTF8StringFromGBKString(sp1_str);
                                          Log.e("埃斯皮2",sp1_str2);

                                      }
                                        catch (Exception e){}

                                        //Log.v("腊肉2",str3);
                                        // 验证用户输入
                                        if (str == null || str.equals("")) {
                                            Toast.makeText(getActivity(),
                                                    "添加的内容不能为空", Toast.LENGTH_SHORT)
                                                    .show();
                                        } else {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                            str2 = formatter.format(curDate);

                                            HashMap<String, Object> map = new HashMap<String, Object>();
                                            map.put("name",currentUser.getUsername());
                                            map.put("title", str);
                                            map.put("show_time", str2);
                                            map.put("local", str3+sp1_str);
                                            map.put("ll",str4);
                                            // SharedPreferences sp =historyActivity.this.getSharedPreferences("history", Context.MODE_WORLD_READABLE);


                                            History hi = new History();
                                            hi.setName(currentUser.getUsername());
                                            hi.setLocal(str3+sp1_str);

                                            hi.setTime(str2);
                                            hi.setTitle(str);
                                            hi.setLL(str4);
                                            HD.addNew(hi);


                                        //    timelineAdapter.notifyDataSetChanged();






                                            JsonDownloadThread thread = new JsonDownloadThread();
                                            thread.start();
                                            Toast.makeText(
                                                    getActivity(),
                                                    "上课签到成功:" + str + "",
                                                    Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getActivity(),MainActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();

                                        }
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                    }

                                }).build();

                        stv = (TextView) dialog.findViewById(R.id.stv);
                        stv.setTextColor(Color.parseColor("#FFF9C4"));
                        stv.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                CityDialog.InputListener listener = new CityDialog.InputListener() {

                                    @Override
                                    public void getText(String str) {
                                        // TODO Auto-generated method stub
                                        stv.setText(str);
                                        stv.setTextColor(Color.parseColor("#FFEB3B"));
                                    }
                                };
                                dialog1 = new CityDialog(getActivity(), listener);
                                dialog1.setTitle("选择具体地点");
                                dialog1.show();
                            }
                        });




                        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
                        //noinspection ConstantConditions
                        passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
                        passwordInput.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                positiveAction.setEnabled(s.toString().trim().length() > 0);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });
                        int widgetColor = ThemeSingleton.get().widgetColor;

                        MDTintHelper.setTint(passwordInput,
                                widgetColor == 0 ? getResources().getColor(R.color.material_teal_500) : widgetColor);
                        dialog.show();
                        positiveAction.setEnabled(false); // disabled by default

                        break;
                    case R.id.fab3:
                        text = fab1.getLabelText();
                        Intent intent1 = new Intent(getActivity(),ComplainActivity.class);
                        startActivity(intent1);
                        break;

                }

                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                if (json=="true");{
                    Toast.makeText(getActivity(), "成功签到", Toast.LENGTH_LONG);
                    Log.e("确定",json);
                }
            }
        }
    };




    public class JsonDownloadThread extends Thread {
        public void run() {
            Looper.prepare();
            String key = "sdhr66135152";
            InputStream inStream = this.getClass().getResourceAsStream("/assets/qiandao_up.xml");
                try {
                    json = getMobileAddress(inStream, key, currentUser.getUsername(), str, str3, str4, sp1_str,str2);

                    Message msg = new Message();
                    msg.what = COMPLETED;
                    handler.sendMessage(msg);


                } catch (Exception e) {
                }


        }
    }

    private String getMobileAddress(InputStream inStream, String key,String user,String des,String loca,String ll,String classroom,String time) throws Exception
    {

        String soap = readSoapFile(inStream, key, user, des, loca, ll, classroom,time);
        Log.e("步骤",soap);
        byte[] data = soap.getBytes();
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
            Log.e("步骤","3");
            InputStream Stream = conn.getInputStream();
            return JsonCommon.parseResponseXML(Stream,"AddAppSignInInfoResult");
        }
        Log.e("步骤1",conn.getResponseCode()+"");
        return conn.getResponseCode()+"";
    }

    private String readSoapFile(InputStream inStream, String key,String user,String des,String loca,String ll,String classroom,String time) throws Exception
    {
        byte[] data = JsonCommon.readInputStream(inStream);
        String soapxml = new String(data);
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", key);
        params.put("user", user);
        params.put("des", des);
        params.put("loca", loca);
        params.put("ll", ll);
        params.put("classroom", classroom);
        params.put("time", time);
        return JsonCommon.replace(soapxml, params);
    }






}
