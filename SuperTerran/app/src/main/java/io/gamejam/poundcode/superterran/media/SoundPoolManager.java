package io.gamejam.poundcode.superterran.media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SoundPoolManager {

    private static final String TAG = SoundPoolManager.class.getSimpleName();
    private static SoundPool sp;

    private SoundPoolManager() {
        sp = new SoundPool(4, 0,0);

    }

    public static void playAudio(int audiofileId, Context context) {

        if(sp == null) {
            sp = new SoundPool(4, AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
        }
//      /** soundId for Later handling of sound pool **/
        int soundId = sp.load(context, audiofileId, 1); // in 2nd param u have to pass your desire ringtone

        sp.play(soundId, 1, 1, 0, 0, 1);

        MediaPlayer mPlayer = MediaPlayer.create(context, audiofileId); // in 2nd param u have to pass your desire ringtone
        //mPlayer.prepare();
        mPlayer.start();

    }
}
