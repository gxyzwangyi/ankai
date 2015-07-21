package cn.edu.shu.ankai;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.avos.avoscloud.AVUser;
import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.ankai.fragments.LabFragment;
import cn.edu.shu.ankai.fragments.RecyclerViewFragment;
import cn.edu.shu.ankai.fragments.ScrollFragment;
import cn.edu.shu.ankai.model.NavigationDrawerItem;
import cn.edu.shu.ankai.practice.PracticeProgressActivity;
import cn.edu.shu.ankai.service.AVService;
import cn.edu.shu.ankai.test.Setting;
import cn.edu.shu.ankai.ui.navigationdrawer.NavigationDrawerView;
import cn.edu.shu.ankai.utils.PreferenceConstants;
import cn.edu.shu.ankai.utils.PreferenceUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import timber.log.Timber;

public class MainActivity extends ActionBarActivity {

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;


    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private int currentSelectedPosition = 0;

    NavigationDrawerView mNavigationDrawerListViewWrapper;

    LinearLayout mLinearDrawerLayout;

    DrawerLayout mDrawerLayout;

    public ListView leftDrawerListView;

    RelativeLayout personLayout;

    private CharSequence mTitle;

    private CharSequence mDrawerTitle;

    private List<NavigationDrawerItem> navigationItems;

    private TextView UserView;
    private TextView EmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = MainActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_main);
        setTitle("实验室安全教育手机版-上海大学");


        initSetting();
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        //  mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        //       mTitle = mDrawerTitle = getTitle();
        // getSupportActionBar().setIcon(R.drawable.ic_action_ab_transparent);

        Timber.tag("LifeCycles");
        Timber.d("Activity Created");

        //     if (savedInstanceState == null) {
        //        getSupportFragmentManager().beginTransaction().add(R.id.contentFrame,
        //                Fragment.instantiate(MainActivity.this, Fragments.ONE.getFragment())).commit();
        //   } else {
        //       currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        //  }

        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_one), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_two), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_three), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_know), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_about),
                R.drawable.ic_action_about, false));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_share),
                R.drawable.ic_action_about, false));


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawerListView = (ListView) findViewById(R.id.leftDrawerListView);
        mLinearDrawerLayout = (LinearLayout) findViewById(R.id.linearDrawer);

        personLayout = (RelativeLayout) findViewById(R.id.userDrawerHeader);


        //侧边栏登录
        personLayout = (RelativeLayout) findViewById(R.id.userDrawerHeader);
        AVUser currentUser = AVUser.getCurrentUser();
        UserView = (TextView) findViewById(R.id.drawerUserName);
        EmailView = (TextView) findViewById(R.id.drawerUserEmail);
        if (currentUser == null) {
            personLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent Intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(Intent);

                }
            });
        } else {
            UserView.setText(currentUser.getUsername());
            EmailView.setText(PreferenceUtils.getPrefString(MainActivity.this, "school", null));
            personLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    new AlertDialogWrapper.Builder(MainActivity.this)
                            .setTitle(R.string.useGoogleLocationServices)
                            .setMessage(R.string.useGoogleLocationServicesPrompt)
                            .setNegativeButton(R.string.disagree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Neutral", Toast.LENGTH_LONG);
                                    AVService.logout();
                                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(loginIntent);
                                }
                            })
                            .show();


                }
            });
        }


        mNavigationDrawerListViewWrapper = (NavigationDrawerView) findViewById(R.id.navigationDrawerListViewWrapper);
        mNavigationDrawerListViewWrapper.replaceWith(navigationItems);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //     getSupportActionBar().setHomeButtonEnabled(true);

        selectItem(currentSelectedPosition);


        leftDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if (mDrawerLayout.isDrawerOpen(mLinearDrawerLayout)) {
                    mDrawerLayout.closeDrawer(mLinearDrawerLayout);
                    onNavigationDrawerItemSelected(position);
                    selectItem(position);
                }


            }
        });


        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }


        // mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        //     mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            int oldPosition = -1;

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return LabFragment.newInstance();
                    case 1:
                        return RecyclerViewFragment.newInstance();
                    default:
                        return ScrollFragment.newInstance();
                }
            }


            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                //only if position changed
                if (position == oldPosition)
                    return;
                oldPosition = position;

                int color = 0;
                int imageUrl = R.drawable.bg_0;
                switch (position) {
                    case 0:
                        imageUrl = R.drawable.bg_0;
                        color = getResources().getColor(R.color.blue);
                        break;
                    case 1:
                        imageUrl = R.drawable.bg_1;
                        color = getResources().getColor(R.color.green);
                        break;
                    case 2:
                        imageUrl = R.drawable.bg_2;
                        color = getResources().getColor(R.color.cyan);
                        break;
                }

                Drawable currentDrawable = getResources().getDrawable(imageUrl);

                final int fadeDuration = 1000;
                mViewPager.setColor(color, fadeDuration);
                mViewPager.setImageDrawable(currentDrawable, fadeDuration);


            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "查看实验室情况";
                    case 1:
                        return "实验室签到记录";
                }
                return "";
            }
        });
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void initSetting() {
        Setting.setContext(this);
        Setting.setVibrateEnable(PreferenceUtils.getPrefBoolean(
                this, PreferenceConstants.VIBRATIONNOTIFY, true));
        Setting.setVoiceEnable(PreferenceUtils.getPrefBoolean(
                this, PreferenceConstants.SCLIENTNOTIFY, true));
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//没用
/*
    @OnItemClick(R.id.leftDrawerListView)
    public void OnItemClick(int position, long id) {
        Log.e("点到了",position+"");
        if (mDrawerLayout.isDrawerOpen(mLinearDrawerLayout)) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
            onNavigationDrawerItemSelected(position);
            selectItem(position);
        }
    }
*/

    private void selectItem(int position) {

        if (leftDrawerListView != null) {
            leftDrawerListView.setItemChecked(position, true);

            navigationItems.get(currentSelectedPosition).setSelected(false);
            navigationItems.get(position).setSelected(true);

            currentSelectedPosition = position;
            //getSupportActionBar().setTitle(navigationItems.get(currentSelectedPosition).getItemName());
        }

        if (mLinearDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
        }
    }


    private void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                Intent intent1 = new Intent(this, SettingActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(this, PracticeProgressActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, WebActivity.class);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent(this, AboutActivity.class);
                startActivity(intent4);
                break;

            case 5:
                showShare(MainActivity.this, MainActivity.this.getResources().getString(R.string.share_app_string));


        }

    }


    protected void showShare(Context context, String text) {
        ShareSDK.initSDK(this);

        String appHomePage = getString(R.string.app_home_page);
        String shareText = text != "" ? text : "\n分享自实验室安全平台-上海大学版本：" + appHomePage;

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字

        //            oks.setNotification(R.drawable.profile, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(appHomePage);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareText);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(getPackageResourcePath() + "/drawable/ic_suesnews.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(appHomePage);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(appHomePage);

        // 启动分享GUI
        oks.show(this);
    }


}
