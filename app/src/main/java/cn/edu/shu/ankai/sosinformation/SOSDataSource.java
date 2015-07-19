package cn.edu.shu.ankai.sosinformation;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 3:49 PM
 */
public class SOSDataSource {

    public static final int NO_NAVIGATION = -1;

    private ArrayList<SOSDataItem> mDataSource;
    private SOSDrawableProvider mProvider;

    public SOSDataSource(Context context) {
        mProvider = new SOSDrawableProvider(context);
        mDataSource = new ArrayList<SOSDataItem>();
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_RECT));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_ROUND_RECT));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_ROUND));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_RECT_BORDER));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_ROUND_RECT_BORDER));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_ROUND_BORDER));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_MULTIPLE_LETTERS));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_FONT));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_SIZE));
        mDataSource.add(itemFromType(SOSDrawableProvider.SAMPLE_ANIMATION));

    }

    public int getCount() {
        return mDataSource.size();
    }

    public SOSDataItem getItem(int position) {
        return mDataSource.get(position);
    }

    private SOSDataItem itemFromType(int type) {
        String label = null;
        Drawable drawable = null;
        switch (type) {
            case SOSDrawableProvider.SAMPLE_RECT:
                label = "电气类实验";
                drawable = mProvider.getRect("个");
                break;
            case SOSDrawableProvider.SAMPLE_ROUND_RECT:
                label = "辐射类实验";
                drawable = mProvider.getRoundRect("辐");
                break;
            case SOSDrawableProvider.SAMPLE_ROUND:
                label = "化学类实验";
                drawable = mProvider.getRound("化");
                break;
            case SOSDrawableProvider.SAMPLE_RECT_BORDER:
                label = "机械建筑类实验";
                drawable = mProvider.getRectWithBorder("机");
                break;
            case SOSDrawableProvider.SAMPLE_ROUND_RECT_BORDER:
                label = "通识类实验";
                drawable = mProvider.getRoundRectWithBorder("通");
                break;
            case SOSDrawableProvider.SAMPLE_ROUND_BORDER:
                label = "医学生物类";
                drawable = mProvider.getRoundWithBorder("医");
                break;
            case SOSDrawableProvider.SAMPLE_MULTIPLE_LETTERS:
                label = "应急措施处理";
                drawable = mProvider.getRectWithMultiLetter();
                type = NO_NAVIGATION;
                break;
            case SOSDrawableProvider.SAMPLE_FONT:
                label = "快速拨打电话";
                drawable = mProvider.getRoundWithCustomFont();
                type = NO_NAVIGATION;
                break;
            case SOSDrawableProvider.SAMPLE_SIZE:
                label = "查看安开电脑版";
                drawable = mProvider.getRectWithCustomSize();
                type = NO_NAVIGATION;
                break;
            case SOSDrawableProvider.SAMPLE_ANIMATION:
                label = "辅助计时";
                drawable = mProvider.getRectWithAnimation();
                type = NO_NAVIGATION;
                break;

        }
        return new SOSDataItem(label, drawable, type);
    }
}
