package cn.edu.shu.ankai.sosinformation;

import android.graphics.drawable.Drawable;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 3:50 PM
 */
public class SOSDataItem {

    private String label;

    private Drawable drawable;

    private int navigationInfo;

    public SOSDataItem(String label, Drawable drawable, int navigationInfo) {
        this.label = label;
        this.drawable = drawable;
        this.navigationInfo = navigationInfo;
    }

    public String getLabel() {
        return label;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public int getNavigationInfo() {
        return navigationInfo;
    }
}
