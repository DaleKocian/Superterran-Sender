package io.gamejam.poundcode.superterran.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import io.gamejam.poundcode.superterran.R;
import io.gamejam.poundcode.superterran.SuperTerranApplication;
import io.gamejam.poundcode.superterran.media.SoundPoolManager;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperTerranFragment extends Fragment {

    public static final int MESSAGE_TYPE_STARCAST_FIRE = 1;
    public static final int MESSAGE_TYPE_STARCAST_MOVE = 2;
    private static final String MESSAGE_FIELD_STARCAST_FIRE = "fire";
    private static final String MESSAGE_FIELD_STARCAST_MOVE = "move";
    private static final String TAG = SuperTerranFragment.class.getSimpleName();

    private TouchControllerView mTouchControllerView;
    private Button mBOOOOOOOOST;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_superterran, container, false);

        mTouchControllerView = (TouchControllerView) view.findViewById(R.id.touch_controller);

        mBOOOOOOOOST = (Button) view.findViewById(R.id.button_boost);
        mBOOOOOOOOST.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SuperTerranApplication.getInstance().getSendMessageHandler().enqueueMessage(
                        MESSAGE_TYPE_STARCAST_FIRE, createFireMessage());
                SoundPoolManager.playAudio(R.raw.boost, getActivity());
            }

        });
        return view;
    }

    public static JSONObject createFireMessage() {
        JSONObject fireMessage = new JSONObject();
        try {
            fireMessage.put(MESSAGE_FIELD_STARCAST_FIRE, true);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON fire message", e);
        }
        return fireMessage;
    }

    public static JSONObject createMoveMessage(float move) {
        JSONObject moveMessage = new JSONObject();
        try {
            moveMessage.put(MESSAGE_FIELD_STARCAST_MOVE, move);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON move message", e);
        }
        return moveMessage;
    }
}
