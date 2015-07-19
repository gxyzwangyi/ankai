package cn.edu.shu.ankai.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.qc.stat.common.User;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.shu.ankai.model.Location;

/**
 * Created by gxyzw_000 on 2015/6/26.
 */
public class App extends Application {


    public static String TAG;
    public static final String LIKES = "likes";
    public static final String STATUS_DETAIL = "StatusDetail";
    public static final String DETAIL_ID = "detailId";
    public static final String CREATED_AT = "createdAt";
    public static final String FOLLOWER = "follower";
    public static final String FOLLOWEE = "followee";
    private static App app = null;

    public int code=0;


    public static App getInstance(){
        return app;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.useAVCloudCN();
        SDKInitializer.initialize(this);
        AVOSCloud.initialize(this, "ov8sxtwd1yhjeo1q3ubsvk9q3y0dmwln2mhlmg9h26owsh4u", "kfu9zoe4ckbv71e3mx6sfhoxhyvzn9lw7xhathiytykr51o8");

        TAG = this.getClass().getSimpleName();
        app = this;
        initImageLoader();
        initImageLoader(this);



        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) return;
                if (location.getAddrStr() != null) {
                    Log.d("yy", "App位置" + location.getAddrStr());
                }
                Log.d("yy", "App经度" + location.getLongitude());
                Log.d("yy", "App纬度" + location.getLatitude());
                Log.d("yy", "位置" + location.getDirection());

                currentLocation = new Location(location);
            }
        });

        LocationClientOption option = new LocationClientOption();

        // Hight_Accuracy Battery_Saving Device_Sensors(GPS)
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // GPS
        option.setOpenGps(true);


        option.setCoorType("bd09ll");

        option.setScanSpan(2000);

        option.setAddrType("all");

        option.setNeedDeviceDirect(true);
        locationClient.setLocOption(option);

        locationClient.start();




    }





    /**
     */
    public void initImageLoader(){
        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(5*1024*1024))
                .memoryCacheSize(10*1024*1024)
              //  .discCache(new UnlimitedDiscCache(cacheDir))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);
    }

    public DisplayImageOptions getOptions(int drawableId){
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(drawableId)
                .showImageForEmptyUri(drawableId)
                .showImageOnFail(drawableId)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "");
        ImageLoaderConfiguration config = StatusUtils.getImageLoaderConfig(context, cacheDir);
        ImageLoader.getInstance().init(config);
    }

    public static Map<String, AVUser> userCache = new HashMap<>();

    public static void regiserUser(AVUser user) {
        userCache.put(user.getObjectId(), user);
    }

    public static void registerBatchUser(List<AVUser> users) {
        for (AVUser user : users) {
            regiserUser(user);
        }
    }

    public static AVUser lookupUser(String userId) {
        return userCache.get(userId);
    }



    public LocationClient locationClient = null;
    public Location currentLocation; //非实时
    private boolean login;
    private User currentUser;


    public boolean isLogin() {
        return login;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        if (currentUser == null)
            login = false;
        else
            login = true;
    }


    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (locationClient.isStarted()) locationClient.stop();
    }









}
