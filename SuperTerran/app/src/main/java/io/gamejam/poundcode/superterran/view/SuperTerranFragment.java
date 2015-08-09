package io.gamejam.poundcode.superterran.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.cast.games.PlayerInfo;
import com.zerokol.views.JoyStickControllerView;

import org.json.JSONException;
import org.json.JSONObject;

import io.gamejam.poundcode.superterran.R;
import io.gamejam.poundcode.superterran.SuperTerranApplication;
import io.gamejam.poundcode.superterran.cast.SuperterranMessageBuilder;
import io.gamejam.poundcode.superterran.media.SoundPoolManager;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperTerranFragment extends Fragment implements JoystickMovedListener {
    private static final String TAG = SuperTerranFragment.class.getSimpleName();
    public static final int REPEAT_INTERVAL = 100;

    private Button mBOOOOOOOOST;
    private Button mSuperGravity;
    private ProgressBar score;
    private JoyStickControllerView mController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_superterran, container, false);

        mController = (JoyStickControllerView) view.findViewById(R.id.touch_controller);
        mController.setOnJoystickMoveListener(this, REPEAT_INTERVAL);
        mBOOOOOOOOST = (Button) view.findViewById(R.id.btnBoost);
        mSuperGravity = (Button) view.findViewById(R.id.btnSuperGravity);
        score = (ProgressBar) view.findViewById(R.id.score);
        mBOOOOOOOOST.setOnClickListener(getBoostOnClickListener());
        mSuperGravity.setOnClickListener(getSuperGravityOnClickListener());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SuperTerranApplication.getInstance().getCastConnectionManager().getGameManagerClient().setListener(new GameManagerClient.Listener() {
            @Override
            public void onStateChanged(GameManagerState currentState, GameManagerState previousState) {
                PlayerInfo playerInfo = currentState.getConnectedControllablePlayers().get(0);
//                Log.d(TAG, playerInfo.getPlayerData().toString());
                try {
                    if(playerInfo != null && playerInfo.getPlayerData() != null && playerInfo.getPlayerData().get("mass") != null)
                    {
                        Integer currentScore = (Integer) playerInfo.getPlayerData().get("mass");
                        SuperTerranFragment.this.updateScore(currentScore);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onGameMessageReceived(String s, JSONObject jsonObject) {
            }
        });
    }

    public void updateScore(Integer currentScore) {
        score.setProgress(currentScore);
    }

    private View.OnClickListener getSuperGravityOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperTerranApplication.getInstance().getSendMessageHandler().enqueueMessage(
                        SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_SUPER_GRAVITY,
                        SuperterranMessageBuilder.createSuperGravityMessage());
                SoundPoolManager.playAudio(R.raw.gravity_bomb, getActivity());
                mSuperGravity.setClickable(false);
                mSuperGravity.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
            }
        };
    }

    private View.OnClickListener getBoostOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperTerranApplication.getInstance().getSendMessageHandler().enqueueMessage(
                        SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_BOOST,
                        SuperterranMessageBuilder.createBoostMessage());
                SoundPoolManager.playAudio(R.raw.boost, getActivity());
                mBOOOOOOOOST.setOnClickListener(null);
                mBOOOOOOOOST.setClickable(false);
                mBOOOOOOOOST.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
            }
        };
    }

    @Override
    public void onValueChanged(int angle) {
        if(angle < 0) {
            angle += 360;
        }
        SuperTerranApplication.getInstance()
                .getSendMessageHandler()
                .enqueueMessage(SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_MOVE,
                        SuperterranMessageBuilder.createMoveMessage(angle));
    }
}
