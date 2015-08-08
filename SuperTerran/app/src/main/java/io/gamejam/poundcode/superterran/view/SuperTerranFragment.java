package io.gamejam.poundcode.superterran.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.gamejam.poundcode.superterran.R;
import io.gamejam.poundcode.superterran.SuperTerranApplication;
import io.gamejam.poundcode.superterran.cast.SuperterranMessageBuilder;
import io.gamejam.poundcode.superterran.media.SoundPoolManager;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperTerranFragment extends Fragment {


    private static final String TAG = SuperTerranFragment.class.getSimpleName();

    private TouchControllerView mTouchControllerView;
    private Button mBOOOOOOOOST;
    private EditText rotation;
    private Button send;

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
                        SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_FIRE,
                        SuperterranMessageBuilder.createFireMessage());
                SoundPoolManager.playAudio(R.raw.boost, getActivity());
            }

        });
        return view;
    }


}
