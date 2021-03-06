// Copyright 2015 Google Inc. All Rights Reserved.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package io.gamejam.poundcode.superterran.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import io.gamejam.poundcode.superterran.SuperTerranApplication;
import io.gamejam.poundcode.superterran.cast.SuperterranMessageBuilder;

/**
 * Custom class for a view that will register touch/gestures from the user.
 */
public class TouchControllerView extends View {

    public TouchControllerView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                float screenHeight = getHeight();
                float scaled = y / screenHeight;
                SuperTerranApplication.getInstance().getSendMessageHandler().enqueueMessage(
                        SuperterranMessageBuilder.MESSAGE_TYPE_SUPERTERRAN_MOVE,
                        SuperterranMessageBuilder.createMoveMessage((int) scaled));
                break;
            default:
                return false;
        }

        return true;
    }

}
