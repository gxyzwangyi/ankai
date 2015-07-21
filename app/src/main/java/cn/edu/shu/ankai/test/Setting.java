package cn.edu.shu.ankai.test;

import android.content.Context;

/**
 * Created by LHY-pc on 2015/7/17.
 */
public class Setting {
    /*public static final String VIBRATE = "vibrate";
    public static final String VOICE = "voice";
    public static final String SETTING_FILE = "setting";*/

    private static Context context;
    private static boolean voiceEnable;
    private static boolean vibrateEnable;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Setting.context = context;
    }

    public static boolean isVoiceEnable() {
        return voiceEnable;
    }

    public static void setVoiceEnable(boolean voiceEnable) {
        Setting.voiceEnable = voiceEnable;
    }

    public static boolean isVibrateEnable() {
        return vibrateEnable;
    }

    public static void setVibrateEnable(boolean vibrateEnable) {
        Setting.vibrateEnable = vibrateEnable;
    }


}
