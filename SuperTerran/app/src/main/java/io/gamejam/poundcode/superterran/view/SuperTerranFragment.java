package io.gamejam.poundcode.superterran.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zerokol.views.JoyStickControllerView;

import io.gamejam.poundcode.superterran.R;
import io.gamejam.poundcode.superterran.SuperTerranApplication;
import io.gamejam.poundcode.superterran.cast.SuperterranMessageBuilder;
import io.gamejam.poundcode.superterran.media.SoundPoolManager;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperTerranFragment extends Fragment implements JoystickMovedListener {


    private static final String TAG = SuperTerranFragment.class.getSimpleName();

    private Button mBOOOOOOOOST;
    private JoyStickControllerView mController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_superterran, container, false);

        mController = (JoyStickControllerView) view.findViewById(R.id.touch_controller);
        mController.setOnJoystickMoveListener(this, 100);
        mBOOOOOOOOST = (Button) view.findViewById(R.id.button_boost);
        mBOOOOOOOOST.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SuperTerranApplication.getInstance().getSendMessageHandler().enqueueMessage(
                        SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_FIRE,
                        SuperterranMessageBuilder.createFireMessage());
                SoundPoolManager.playAudio(R.raw.boost, getActivity());
            }

        });
        return view;
    }

    @Override
    public void onValueChanged(int angle) {
        if(angle < 0) {
            angle = angle + 360;
        }
        SuperTerranApplication.getInstance()
                .getSendMessageHandler()
                .enqueueMessage(SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_MOVE,
                        SuperterranMessageBuilder.createMoveMessage(angle));
        Log.d(TAG, "ANGLE: " + angle);
    }
}
