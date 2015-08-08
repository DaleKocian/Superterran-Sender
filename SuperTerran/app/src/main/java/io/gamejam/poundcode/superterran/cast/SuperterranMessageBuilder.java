package io.gamejam.poundcode.superterran.cast;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperterranMessageBuilder {

    public static final int MESSAGE_TYPE_SUPERTERRAN_FIRE = 1;
    public static final int MESSAGE_TYPE_SUPERTERRAN_MOVE = 2;
    private static final String MESSAGE_FIELD_SUPERTERRAN_FIRE = "fire";
    private static final String MESSAGE_FIELD_SUPERTERRAN_MOVE = "move";
    private static final String TAG = SuperterranMessageBuilder.class.getSimpleName();

    public static JSONObject createFireMessage() {
        JSONObject fireMessage = new JSONObject();
        try {
            fireMessage.put(MESSAGE_FIELD_SUPERTERRAN_FIRE, true);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON fire message", e);
        }
        return fireMessage;
    }

    public static JSONObject createMoveMessage(float move) {
        JSONObject moveMessage = new JSONObject();
        try {
            moveMessage.put(MESSAGE_FIELD_SUPERTERRAN_MOVE, move);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON move message", e);
        }
        return moveMessage;
    }
}
