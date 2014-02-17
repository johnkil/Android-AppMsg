/*
 * Copyright 2012 Evgeny Shishkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devspark.appmsg.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.actionbarsherlock.app.SherlockActivity;
import com.devspark.appmsg.AppMsg;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static android.view.Gravity.BOTTOM;
import static com.devspark.appmsg.AppMsg.LENGTH_SHORT;
import static com.devspark.appmsg.AppMsg.LENGTH_STICKY;

/**
 * Sample of AppMsg library.
 * 
 * @author Evgeny Shishkin
 * 
 */
public class MainActivity extends SherlockActivity {
    private int mStickyCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Button onClick listener.
     * 
     * @param v
     */
    public void showAppMsg(View v) {
        final CharSequence msg = ((Button) v).getText();
        final AppMsg.Style style;
        boolean customAnimations = false;
        AppMsg provided = null;
        switch (v.getId()) {
            case R.id.alert:
                style = AppMsg.STYLE_ALERT;
                break;
            case R.id.confirm:
                style = AppMsg.STYLE_CONFIRM;
                break;
            case R.id.info:
                style = AppMsg.STYLE_INFO;
                break;
            case R.id.custom:
                style = new AppMsg.Style(LENGTH_SHORT, R.color.custom);
                customAnimations = true;
                break;
            case R.id.sticky:
                style = new AppMsg.Style(LENGTH_STICKY, R.color.sticky);
                provided = AppMsg.makeText(this, msg + " #" + ++mStickyCount, style, R.layout.sticky);
                provided.getView()
                        .findViewById(R.id.remove_btn)
                        .setOnClickListener(new CancelAppMsg(provided));
                break;
            case R.id.cancel_all:
                AppMsg.cancelAll(this);
                return;
            default:
                return;
        }
        // create {@link AppMsg} with specify type
        AppMsg appMsg = provided != null ? provided : AppMsg.makeText(this, msg, style);
        if (((CheckBox) (findViewById(R.id.bottom))).isChecked()) {
            appMsg.setLayoutGravity(BOTTOM);
        }

        if (customAnimations) {
            appMsg.setAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        appMsg.show();
    }

  @Override
  protected void onPause() {
    super.onPause();
    // This is optional for 14+,
    // also you may want to call it at your later convenience, e.g. onDestroy
    if (SDK_INT < ICE_CREAM_SANDWICH) {
        AppMsg.cancelAll(this);
    }
  }

    static class CancelAppMsg implements View.OnClickListener {
        private final AppMsg mAppMsg;

        CancelAppMsg(AppMsg appMsg) {
            mAppMsg = appMsg;
        }

        @Override
        public void onClick(View v) {
            mAppMsg.cancel();
        }
    }
}
