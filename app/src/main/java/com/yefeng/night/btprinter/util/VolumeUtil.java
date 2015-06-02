package com.yefeng.night.btprinter.util;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by yee on 4/16/14.
 *
 * @author yee
 */
public class VolumeUtil {

    public static int turnOffMusic(Context mContext) {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_VIBRATE);
        return current;
    }

    public static void setMusic(Context mContext, int index) {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, AudioManager.FLAG_VIBRATE);
    }

    public static void setMusicMax(Context mContext) {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, AudioManager.FLAG_VIBRATE);
    }

    public static int getMusicMaxIndex(Context mContext) {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public static void setNotificationSoundMax(Context mContext) {
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        int max = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, max, AudioManager.FLAG_VIBRATE);
    }


}
