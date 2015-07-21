package cn.edu.shu.ankai.test;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by LHY-pc on 2015/7/17.
 */
public class VibratorHelper {
    public static final int VIBRATE_TIP = 1;

    //根据震动类型震动
    public static void vibrate(int vibrateType) {
        if (Setting.isVibrateEnable()) {
            Vibrator vibrator = (Vibrator)Setting.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            switch (vibrateType) {
                case VIBRATE_TIP:
                    vibrator.vibrate(new long[]{100,200,100,200},-1);
                    break;
                default:
                    break;
            }
        }
    }
}

