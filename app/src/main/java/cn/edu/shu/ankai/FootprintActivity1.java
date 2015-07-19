package cn.edu.shu.ankai;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.shu.ankai.model.History;
import cn.edu.shu.ankai.db.HistoryData;
import cn.edu.shu.ankai.model.Point;
import cn.edu.shu.ankai.utils.App;


public class FootprintActivity1 extends ActionBarActivity implements OnMenuItemClickListener,
        OnMenuItemLongClickListener {
    public String profilelocal = "定位没成功";
    private String profilelolLL = "定位经纬度没成功";
    public EditText inputString;
    public EditText inputString_edit;
    public TextView BoolLocal;
    public int i;
    private int currentPosition = 0;
    private ListView listView;
    private TimelineAdapter timelineAdapter;
    private List<Map<String, Object>> dataSource;
    private ArrayList<HashMap<String, Object>> historylist = new ArrayList<HashMap<String, Object>>();
    private HistoryData HD;
    ArrayList LLList = new ArrayList();
    private App app;
    private ButtonFloatSmall footview;

    private FragmentManager fragmentManager;
    private DialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //  this.requestWindowFeature(Window.FEATURE_ACTION_BAR);

        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=FootprintActivity1.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.history_list);
        HD = new HistoryData(this);
//HD.delete();

        historylist = HD.getUserList();

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();



        ((App) getApplication()).locationClient.requestLocation();
        app = (App) getApplication();

      //  AVOSCloud.initialize(this, "ov8sxtwd1yhjeo1q3ubsvk9q3y0dmwln2mhlmg9h26owsh4u", "kfu9zoe4ckbv71e3mx6sfhoxhyvzn9lw7xhathiytykr51o8");

        if (!historylist.isEmpty()) {
            for (int q = 0; q < historylist.size(); q++) {
               Log.e("的我",historylist.get(q).toString());
                if (historylist.get(q).toString().length() > 95) {

                    String a = historylist.get(q).toString().substring(historylist.get(q).toString().indexOf("ll=") + 3, historylist.get(q).toString().indexOf("show") - 2);
                    LLList.add(a);
                }
            }
            Log.e("我", LLList.toString());
        }


        listView = (ListView) this.findViewById(R.id.listview);
        listView.setDividerHeight(0);
        //     dataSource = getData();
        timelineAdapter = new TimelineAdapter(this, historylist);
        listView.setAdapter(timelineAdapter);

        BoolLocal = (TextView) this.findViewById(R.id.boollocal);
        BoolLocal.setText("不确定");
Log.e("网络",isConnectionAvailable(this)+"");
        if (app.getCurrentLocation() != null&&!isConnectionAvailable(this)) {
            profilelocal = app.getCurrentLocation().getAddr();
            profilelolLL = app.getCurrentLocation().getLatitude() +","+ app.getCurrentLocation().getLongitude();
            Log.v("我的", profilelocal + profilelolLL);


            if (app.getCurrentLocation().getLatitude()>=31.31694&&app.getCurrentLocation().getLatitude()<=31.326114&&app.getCurrentLocation().getLongitude()>=121.394293&&app.getCurrentLocation().getLongitude()<=121.403878)
            {
                double now_wei = app.currentLocation.getLatitude(); //纬度
                double now_jin =  app.currentLocation.getLongitude();//精度
                //A 121.402346,31.319339
                //B 121.402068,31.319755
                // c 121.401812,31.320191
                //  d 121.401529,31.320626
                // e 121.400909,31.321849
                // f 121.400532,31.322212
                // g 121.400213,31.322659
                //图 121.121.398829,31.322589
                // 行 121.403393,31.319111
                Point A = new Point(121.402346,31.319339);
                Point B = new Point(121.402068,31.319755);
                Point C = new Point(121.401812,31.320191);
                Point D = new Point(121.401529,31.320626);
                Point E = new Point(121.400909,31.321849);
                Point F = new Point(1121.400532,31.322212);
                Point G = new Point(121.400213,31.322659);
                Point T = new Point(121.398829,31.322589);
                Point X = new Point(121.403393,31.319111);
                Point now = new Point(now_jin,now_wei);


               double array[] = {Point.length(now,A),Point.length(now,B),Point.length(now,C),Point.length(now,D),Point.length(now,E),Point.length(now,F),Point.length(now,G),Point.length(now,T),Point.length(now,X)};
                double array1[]=array.clone();


                Arrays.sort(array1);
                double minpoint = array1[array1.length-1];

                if(minpoint>200)
                {
                    BoolLocal.setText("在学校(不知道具体在哪)");

                }
else {
                    if (array[0] == minpoint) {

                        BoolLocal.setText("在学校(A楼附近)");

                    } else if (array[1] == minpoint) {
                        BoolLocal.setText("在学校(B楼附近)");

                    } else if (array[2] == minpoint) {
                        BoolLocal.setText("在学校(C楼附近)");

                    } else if (array[3] == minpoint) {
                        BoolLocal.setText("在学校(D楼附近)");

                    } else if (array[4] == minpoint) {
                        BoolLocal.setText("在学校(E楼附近)");

                    } else if (array[5] == minpoint) {
                        BoolLocal.setText("在学校(F楼附近)");

                    } else if (array[6] == minpoint) {
                        BoolLocal.setText("在学校(G楼附近)");

                    } else if (array[7] == minpoint) {
                        BoolLocal.setText("在学校(图书馆附近)");

                    } else {
                        BoolLocal.setText("在学校(行政楼附近)");

                    }

                }



               // BoolLocal.setText("在学校");



            }
            else{
                BoolLocal.setText("不在学校");
            }

        }









        //ActionBar actionBar = this.getActionBar();
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);



        footview = (ButtonFloatSmall) this.findViewById(R.id.buttonFloatSmall);
        footview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent intent1 = new Intent(FootprintActivity1.this, FootviewActivity.class);
             //   Bundle bundle = new Bundle();
                //       bundle.putSerializable("history",LLList);
              //  intent1.putStringArrayListExtra("history", LLList);
                //  intent1.putExtras(bundle);
           //     startActivity(intent1);
            }
        });


        AVUser currentUser = AVUser.getCurrentUser();


    }


    public static boolean isConnectionAvailable(Context cotext)
    {
        Context c=cotext;
        boolean isConnectionFail = true;
        ConnectivityManager connectivityManager = (ConnectivityManager)cotext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected())
            {
                isConnectionFail = true;
                Toast.makeText(c,"请检查您的网络配置",Toast.LENGTH_LONG).show();

            }
            else
            {
                isConnectionFail = false;
            }
        }
        else
        {

        }
        return isConnectionFail;
    }

    @Override
    public void onResume() {
        super.onResume();
        historylist = HD.getUserList();
        timelineAdapter = new TimelineAdapter(this, historylist);
        listView.setAdapter(timelineAdapter);


        if (app.getCurrentLocation() != null&&!isConnectionAvailable(this)) {
            profilelocal = app.getCurrentLocation().getAddr();
            profilelolLL = app.getCurrentLocation().getLatitude() +","+ app.getCurrentLocation().getLongitude();
            Log.v("我的", profilelocal + profilelolLL);


            if (app.getCurrentLocation().getLatitude()>=31.31694&&app.getCurrentLocation().getLatitude()<=31.326114&&app.getCurrentLocation().getLongitude()>=121.394293&&app.getCurrentLocation().getLongitude()<=121.403878) {

                {
                    double now_wei = app.currentLocation.getLatitude();
                    double now_jin = app.currentLocation.getLongitude();
                    //A 121.402346,31.319339
                    //B 121.402068,31.319755
                    // c 121.401812,31.320191
                    //  d 121.401529,31.320626
                    // e 121.400909,31.321849
                    // f 121.400532,31.322212
                    // g 121.400213,31.322659
                    //图 121.121.398829,31.322589
                    // 行 121.403393,31.319111
                    Point A = new Point(121.402346, 31.319339);
                    Point B = new Point(121.402068, 31.319755);
                    Point C = new Point(121.401812, 31.320191);
                    Point D = new Point(121.401529, 31.320626);
                    Point E = new Point(121.400909, 31.321849);
                    Point F = new Point(121.400532, 31.322212);
                    Point G = new Point(121.400213, 31.322659);
                    Point T = new Point(121.398829, 31.322589);
                    Point X = new Point(121.403393, 31.319111);
                    Point now = new Point(now_jin, now_wei);


                    double array[] = {Point.length(now, A), Point.length(now, B), Point.length(now, C), Point.length(now, D), Point.length(now, E), Point.length(now, F), Point.length(now, G), Point.length(now, T), Point.length(now, X)};
                    double array1[] = array.clone();
                       Log.e("点",array[1]+"+"+array[2]+"+"+array[3]);

                    Arrays.sort(array1);
                    double minpoint = array1[array1.length - 1];
                   Log.e("大点", minpoint + "");

                    if(minpoint>200)
                    {
                        BoolLocal.setText("在学校(不知道具体在哪)");

                    }
                    else {
                        if (array[0] == minpoint) {

                            BoolLocal.setText("在学校(A楼附近)");

                        } else if (array[1] == minpoint) {
                            BoolLocal.setText("在学校(B楼附近)");

                        } else if (array[2] == minpoint) {
                            BoolLocal.setText("在学校(C楼附近)");

                        } else if (array[3] == minpoint) {
                            BoolLocal.setText("在学校(D楼附近)");

                        } else if (array[4] == minpoint) {
                            BoolLocal.setText("在学校(E楼附近)");

                        } else if (array[5] == minpoint) {
                            BoolLocal.setText("在学校(F楼附近)");

                        } else if (array[6] == minpoint) {
                            BoolLocal.setText("在学校(G楼附近)");

                        } else if (array[7] == minpoint) {
                            BoolLocal.setText("在学校(图书馆附近)");

                        } else {
                            BoolLocal.setText("在学校(行政楼附近)");

                        }

                    }
                }
            }
            else{
                BoolLocal.setText("不在学校");
            }
        }

    }

    private EditText passwordInput;
    private View positiveAction;
    //创建actionbar设置一个增加按钮


    AVUser currentUser = AVUser.getCurrentUser();






    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("刷新");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("上课打卡");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFav = new MenuObject("下课打卡");
        addFav.setResource(R.drawable.icn_4);



        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);

        menuObjects.add(addFav);

        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("签到打卡");
    }



    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {

        if(position==0)
        {
            Toast.makeText(this, "没什么" , Toast.LENGTH_SHORT).show();
        }





        if(position==2) {
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("你好" + currentUser.getUsername().toString() + currentUser.getString("name"))
                    .customView(R.layout.dialog_customview, true)
                    .positiveText(R.string.connect)
                    .neutralText(BoolLocal.getText())
                    .negativeText(android.R.string.cancel)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            Toast.makeText(getApplicationContext(), "Password: " + passwordInput.getText().toString(), Toast.LENGTH_LONG);

                            // 获取用户添加足迹的输入内容
                            String str = passwordInput.getText().toString();
                            String str3 = FootprintActivity1.this.profilelocal+"["+BoolLocal.getText()+"]";
                            String str4 = FootprintActivity1.this.profilelolLL;



                            //Log.v("腊肉2",str3);
                            // 验证用户输入
                            if (str == null || str.equals("")) {
                                Toast.makeText(getApplicationContext(),
                                        "添加的内容不能为空", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                String str2 = formatter.format(curDate);

                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("title", str);
                                map.put("show_time", str2);
                                map.put("local", str3);
                                map.put("ll",str4);
                                // SharedPreferences sp =historyActivity.this.getSharedPreferences("history", Context.MODE_WORLD_READABLE);

                                History hi = new History();
                                hi.setLocal(str3);
                                hi.setTime(str2);
                                hi.setTitle(str);
                                hi.setLL(str4);
                                HD.addNew(hi);
                        //        Log.v("加一个", hi.getLocal().toString());
                                // dataSource.add(0, map);

                                timelineAdapter.notifyDataSetChanged();


                                AVObject locationdata = new AVObject("Locationdata");
                                locationdata.put("username", currentUser.getUsername());
                                locationdata.put("name", currentUser.getString("name"));
                                locationdata.put("time", str2);
                                locationdata.put("ll", str4);
                                locationdata.put("addr", str3);
                                locationdata.put("info", str);
                                locationdata.put("inschool",BoolLocal.getText().toString());
                                locationdata.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            Log.i("LeanCloud", "Save successfully.");
                                        } else {
                                            Log.e("LeanCloud", e.getMessage());


                                        }
                                    }
                                });


                                Toast.makeText(
                                        FootprintActivity1.this,
                                        "上班签到成功:" + str + "",
                                        Toast.LENGTH_SHORT).show();
  //                              Intent intent = new Intent(FootprintActivity1.this,ProgressActivity1.class) ;
//                                startActivity(intent) ;
                            }
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                        }
                        @Override
                        public void onNeutral(MaterialDialog dialog) {
               //            Intent intent1 = new Intent(FootprintActivity1.this, FootviewActivity.class);
               //             Bundle bundle = new Bundle();
                //            intent1.putStringArrayListExtra("history", LLList);
               //             startActivity(intent1);
                        }
                    }).build();

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
        }


if(position==3) {
    MaterialDialog dialog_2 = new MaterialDialog.Builder(this)
            .title("你好" + currentUser.getUsername().toString() + currentUser.getString("name"))
            .customView(R.layout.dialog_customview_leave, true)
            .positiveText(R.string.connect)
            .neutralText(BoolLocal.getText())
            .negativeText(android.R.string.cancel)
            .callback(new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    Toast.makeText(getApplicationContext(), "Password: " + passwordInput.getText().toString(), Toast.LENGTH_LONG);

                    // 获取用户添加足迹的输入内容
                    String str = passwordInput.getText().toString();
                    String str3 = FootprintActivity1.this.profilelocal+"["+BoolLocal.getText()+"]";
                    String str4 = FootprintActivity1.this.profilelolLL;

                    //Log.v("腊肉2",str3);
                    // 验证用户输入
                    if (str == null || str.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "添加的内容不能为空", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str2 = formatter.format(curDate);

                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("title", str);
                        map.put("show_time", str2);
                        map.put("local", str3);
                        map.put("ll",str4);

                        // SharedPreferences sp =historyActivity.this.getSharedPreferences("history", Context.MODE_WORLD_READABLE);

                        History hi = new History();
                        hi.setLocal(str3);
                        hi.setTime(str2);
                        hi.setTitle(str);
                        hi.setLL(str4);
                        HD.addNew(hi);
                        Log.v("加一个", hi.getLocal().toString());
                        // dataSource.add(0, map);
                        timelineAdapter.notifyDataSetChanged();


                        AVObject locationdata = new AVObject("Locationdata");
                        locationdata.put("username", currentUser.getUsername());
                        locationdata.put("name", currentUser.getString("name"));
                        locationdata.put("time", str2);
                        locationdata.put("ll", str4);
                        locationdata.put("addr", str3);
                        locationdata.put("info", str);
                        locationdata.put("inschool",BoolLocal.getText().toString());

                        locationdata.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Log.i("LeanCloud", "Save successfully.");
                                } else {
                                    Log.e("LeanCloud", "Save failed.");
                                }
                            }
                        });

                        Toast.makeText(
                                FootprintActivity1.this,
                                "下班签到成功:" + str + "",
                                Toast.LENGTH_SHORT).show();
               //         Intent intent = new Intent(FootprintActivity1.this,ProgressActivity1.class) ;
              //          startActivity(intent) ;
                    }
                }

                @Override
                public void onNegative(MaterialDialog dialog) {
                }
                @Override
                public void onNeutral(MaterialDialog dialog) {
                //   Intent intent1 = new Intent(FootprintActivity1.this, FootviewActivity.class);
                //    Bundle bundle = new Bundle();
                //    intent1.putStringArrayListExtra("history", LLList);
                //    startActivity(intent1);
                }
            }).build();

    positiveAction = dialog_2.getActionButton(DialogAction.POSITIVE);
    //noinspection ConstantConditions
    passwordInput = (EditText) dialog_2.getCustomView().findViewById(R.id.password);
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
    int widgetColor_2 = ThemeSingleton.get().widgetColor;

    MDTintHelper.setTint(passwordInput,
            widgetColor_2 == 0 ? getResources().getColor(R.color.material_teal_500) : widgetColor_2);
    dialog_2.show();
    positiveAction.setEnabled(false); // disabled by default

}






    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

}