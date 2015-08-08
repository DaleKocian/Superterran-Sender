package io.gamejam.poundcode.superterran;

import android.app.Application;

import io.gamejam.poundcode.superterran.cast.CastConnectionManager;
import io.gamejam.poundcode.superterran.cast.CastConstants;
import io.gamejam.poundcode.superterran.cast.SendMessageHandler;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperTerranApplication extends Application  implements CastConnectionManager.CastAppIdProvider {

    private static SuperTerranApplication instance;
    private CastConnectionManager castConnectionManager;
    private SendMessageHandler mSendMessageHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        castConnectionManager = new CastConnectionManager(this, this);
        mSendMessageHandler = new SendMessageHandler(castConnectionManager);
    }

    public static SuperTerranApplication getInstance() {
        return instance;
    }

    public CastConnectionManager getCastConnectionManager() {
        return castConnectionManager;
    }

    @Override
    public String getCastAppId() {
        return CastConstants.CAST_APP_ID;
    }

    public SendMessageHandler getSendMessageHandler() {
        return mSendMessageHandler;
    }

}
