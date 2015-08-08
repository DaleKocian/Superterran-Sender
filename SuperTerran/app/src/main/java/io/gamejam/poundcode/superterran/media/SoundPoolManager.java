package io.gamejam.poundcode.superterran.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SoundPoolManager {

    private static final String TAG = SoundPoolManager.class.getSimpleName();
    private static SoundPool sp;

    private SoundPoolManager() {
    }

    public static void playAudio(int audiofileId, Context context) {

        if(sp == null) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                sp = new SoundPool(4, AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
            }
            else {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .build();
                sp = new SoundPool.Builder()
                        .setMaxStreams(5)
                        .setAudioAttributes(attributes)
                        .build();
            }
        }
        int soundId = sp.load(context, audiofileId, 1);
        sp.play(soundId, 1, 1, 0, 0, 1);
        MediaPlayer mPlayer = MediaPlayer.create(context, audiofileId);

        mPlayer.start();

    }
}
