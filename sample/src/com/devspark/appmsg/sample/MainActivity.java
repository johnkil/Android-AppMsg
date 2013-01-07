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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.actionbarsherlock.app.SherlockActivity;
import com.devspark.appmsg.AppMsg;

/**
 * Sample of AppMsg library.
 * 
 * @author Evgeny Shishkin
 * 
 */
public class MainActivity extends SherlockActivity {

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
                style = new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.custom);
                break;

            default:
                return;
        }

        // create {@link AppMsg} with specify type
        AppMsg appMsg = AppMsg.makeText(this, msg, style);
        if (((CheckBox) (findViewById(R.id.bottom))).isChecked()) {
            appMsg.setLayoutGravity(Gravity.BOTTOM);
        }
        appMsg.show();
    }

}
