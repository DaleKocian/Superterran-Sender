package io.gamejam.poundcode.superterran.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.cast.games.PlayerInfo;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import io.gamejam.poundcode.superterran.R;
import io.gamejam.poundcode.superterran.SuperTerranApplication;
import io.gamejam.poundcode.superterran.cast.CastConnectionManager;
import io.gamejam.poundcode.superterran.cast.SuperterranMessageBuilder;

/**
 * Created by chris_pound on 8/8/15.
 */
public class MainActivity extends AppCompatActivity implements Observer {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CastConnectionFragment mCastConnectionFragment;
    private SuperTerranFragment mSuperTerranFragment;
    private String userName = "Player";

    @Override
    protected void onPause() {
        super.onPause();
        CastConnectionManager manager =
                SuperTerranApplication.getInstance().getCastConnectionManager();
        manager.stopScan();
        manager.deleteObserver(this);
        SuperTerranApplication.getInstance().getSendMessageHandler().flushMessages();
        updateFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CastConnectionManager manager =
                SuperTerranApplication.getInstance().getCastConnectionManager();
        manager.startScan();
        manager.addObserver(this);
        SuperTerranApplication.getInstance().getSendMessageHandler().resumeSendingMessages();
        updateFragments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCastConnectionFragment = new CastConnectionFragment();
        mSuperTerranFragment = new SuperTerranFragment();
        updateFragments();
        final EditText etUserName = new EditText(this);
        new AlertDialog.Builder(this).setCancelable(false).setMessage("Please enter your name.")
                .setView(etUserName).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (etUserName != null) {
                    userName = etUserName.getText().toString();
                    dialog.dismiss();
                }
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cast, menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
        mediaRouteActionProvider.setRouteSelector(SuperTerranApplication.getInstance()
                .getCastConnectionManager()
                .getMediaRouteSelector());
        return true;
    }

    @Override
    public void update(Observable observable, Object data) {
        CastConnectionManager manager =
                SuperTerranApplication.getInstance().getCastConnectionManager();
        if (manager.isConnectedToReceiver() && !hasPlayerConnected()) {
            final GameManagerClient gameManagerClient = manager.getGameManagerClient();
            PendingResult<GameManagerClient.GameManagerResult> result =
                    gameManagerClient.sendPlayerAvailableRequest(null);
            result.setResultCallback(new ResultCallback<GameManagerClient.GameManagerResult>() {
                @Override
                public void onResult(final GameManagerClient.GameManagerResult gameManagerResult) {
                    if (!gameManagerResult.getStatus().isSuccess()) {
                        SuperTerranApplication.getInstance().getCastConnectionManager().
                                disconnectFromReceiver(false);
                        Log.e(TAG, gameManagerResult.getStatus().getStatusMessage());
                    }
                    updateFragments();
                }
            });
        }
        updateFragments();
    }

    private void updateFragments() {
        Log.d(TAG, "Updating fragments");
        if (isChangingConfigurations() || isFinishing() || isDestroyed()) {
            return;
        }
        Fragment fragment;
        if (hasPlayerConnected()) {
            SuperTerranApplication.getInstance().getSendMessageHandler().enqueueMessage(
                    SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_USER_NAME,
                    SuperterranMessageBuilder.createUserNameMessage(userName));
            fragment = mSuperTerranFragment;
        } else {
            fragment = mCastConnectionFragment;
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }

    private boolean hasPlayerConnected() {
        CastConnectionManager manager =
                SuperTerranApplication.getInstance().getCastConnectionManager();
        GameManagerClient gameManagerClient = manager.getGameManagerClient();
        if (manager.isConnectedToReceiver()) {
            GameManagerState state = gameManagerClient.getCurrentState();
            return state.getConnectedControllablePlayers().size() > 0;
        }
        return false;
    }
}
