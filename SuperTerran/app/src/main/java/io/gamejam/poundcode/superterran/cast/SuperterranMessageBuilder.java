package io.gamejam.poundcode.superterran.cast;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chris_pound on 8/8/15.
 */
public class SuperterranMessageBuilder {

    public static final int MESSAGE_TYPE_SUPERTERRAN_BOOST = 1;
    public static final int MESSAGE_TYPE_SUPERTERRAN_MOVE = 2;
    public static final int MESSAGE_TYPE_SUPERTERRAN_SUPER_GRAVITY = 3;
    private static final String MESSAGE_FIELD_SUPERTERRAN_BOOST = "boost";
    private static final String MESSAGE_FIELD_SUPERTERRAN_MOVE = "move";
    private static final String MESSAGE_FIELD_SUPERTERRAN_SUPER_GRAVITY = "super_gravity";
    private static final String TAG = SuperterranMessageBuilder.class.getSimpleName();

    public static JSONObject createBoostMessage() {
        JSONObject boostMessage = new JSONObject();
        try {
            boostMessage.put(MESSAGE_FIELD_SUPERTERRAN_BOOST, true);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON boost message", e);
        }
        return boostMessage;
    }

    public static JSONObject createMoveMessage(int move) {
        JSONObject moveMessage = new JSONObject();
        try {
            moveMessage.put(MESSAGE_FIELD_SUPERTERRAN_MOVE, move);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON move message", e);
        }
        return moveMessage;
    }

    public static JSONObject createSuperGravityMessage() {
        JSONObject superGravityMessage = new JSONObject();
        try {
            superGravityMessage.put(MESSAGE_FIELD_SUPERTERRAN_SUPER_GRAVITY, true);
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON boost message", e);
        }
        return superGravityMessage;
    }
}
