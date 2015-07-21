package cn.edu.shu.ankai.test;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import cn.edu.shu.ankai.R;

/**
 * Created by LHY-pc on 2015/7/17.
 */
public class VoiceHelper {
    public static final int VOICE_TIP = 1;
    //����voiceType��������
    public static void play(int voiceType) throws IOException {
        if (Setting.isVoiceEnable()) {
            switch (voiceType) {
                case VOICE_TIP:
                    loadAndStart(R.raw.voice1);
                    break;
                default:
                    break;
            }

        }
    }
    //���������ļ�������
    private static void loadAndStart(int resid) throws IOException {
        Context context = Setting.getContext();
        final MediaPlayer player = MediaPlayer.create(context, resid);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.release();//�ͷ���Դ
            }
        });
        player.start();
    }

}
