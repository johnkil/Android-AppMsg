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

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.devspark.appmsg.AppMsg;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.text.TextUtils.isEmpty;
import static android.view.Gravity.BOTTOM;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.devspark.appmsg.AppMsg.LENGTH_SHORT;
import static com.devspark.appmsg.AppMsg.LENGTH_STICKY;

/**
 * Sample of AppMsg library.
 *
 * @author Evgeny Shishkin
 */
public class MainActivity extends ActionBarActivity {
    private static final int NORMAL_POSITION = 1;
    private static final int INFO_POSITION = 2;

    private int mMsgCount;
    private Spinner mStyle;
    private Spinner mPriority;
    private EditText mProvidedMsg;
    private CheckBox mBottom;
    private CheckBox mParent;
    private ViewGroup mAltParent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProvidedMsg = (EditText) findViewById(R.id.provided_txt);
        mStyle = (Spinner) findViewById(R.id.style_spnr);
        mStyle.setSelection(INFO_POSITION);
        mPriority = (Spinner) findViewById(R.id.priority_spnr);
        mPriority.setSelection(NORMAL_POSITION);
        mBottom = (CheckBox) findViewById(R.id.bottom);
        mParent = (CheckBox) findViewById(R.id.parent_chk);
        mAltParent = (ViewGroup) findViewById(R.id.alt_parent);

        mParent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAltParent.setVisibility(isChecked ? VISIBLE : GONE);
                mBottom.setVisibility(isChecked ? GONE : VISIBLE);
            }
        });

        if (SDK_INT >= JELLY_BEAN) {
            enableChangingTransition();
        }
    }

    @TargetApi(JELLY_BEAN)
    private void enableChangingTransition() {
        ViewGroup animatedRoot = (ViewGroup) findViewById(R.id.animated_root);
        animatedRoot.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
    }

    /**
     * Button onClick listener.
     *
     * @param v
     */
    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                showAppMsg();
                break;
            case R.id.cancel_all:
                AppMsg.cancelAll(this);
                break;
            default:
                return;
        }
    }

    private void showAppMsg() {
        mMsgCount++;
        final int styleSelected = mStyle.getSelectedItemPosition();
        final int priority = positionToPriority(mPriority.getSelectedItemPosition());
        final CharSequence providedMsg = mProvidedMsg.getText();
        final CharSequence msg = isEmpty(providedMsg)
                ? new StringBuilder().append(mStyle.getSelectedItem())
                .append(" ").append(mPriority.getSelectedItem())
                .append(" msg#").append(mMsgCount).toString()
                : providedMsg;
        final AppMsg.Style style;
        boolean customAnimations = false;
        AppMsg provided = null;
        switch (styleSelected) {
            case 0:
                style = AppMsg.STYLE_ALERT;
                break;
            case 1:
                style = AppMsg.STYLE_CONFIRM;
                break;
            case 3:
                style = new AppMsg.Style(LENGTH_SHORT, R.color.custom);
                customAnimations = true;
                break;
            case 4:
                style = new AppMsg.Style(LENGTH_STICKY, R.color.sticky);
                provided = AppMsg.makeText(this, msg, style, R.layout.sticky);
                provided.getView()
                        .findViewById(R.id.remove_btn)
                        .setOnClickListener(new CancelAppMsg(provided));
                break;
            default:
                style = AppMsg.STYLE_INFO;
                break;
        }
        // create {@link AppMsg} with specify type
        AppMsg appMsg = provided != null ? provided : AppMsg.makeText(this, msg, style);
        appMsg.setPriority(priority);
        if (mParent.isChecked()) {
            appMsg.setParent(mAltParent);
        } else {
            if (mBottom.isChecked()) {
                appMsg.setLayoutGravity(BOTTOM);
            }
        }

        if (customAnimations) {
            appMsg.setAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        appMsg.show();

    }

    private static int positionToPriority(int selectedItemPosition) {
        switch (selectedItemPosition) {
            case 0:
                return AppMsg.PRIORITY_HIGH;
            case 2:
                return AppMsg.PRIORITY_LOW;
            default:
                return AppMsg.PRIORITY_NORMAL;
        }
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
