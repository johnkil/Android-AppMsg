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

package com.devspark.appmsg;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * In-layout notifications. Based on {@link android.widget.Toast} notifications 
 * and article by Cyril Mottier (http://android.cyrilmottier.com/?p=773).
 * 
 * @author e.shishkin
 *
 */
public class AppMsg {
	
	/**
     * Show the view or text notification for a short period of time. This time
     * could be user-definable. This is the default.
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = 3000;

    /**
     * Show the view or text notification for a long period of time. This time
     * could be user-definable.
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 5000;
	
	/**
	 * Show the text notification for a long period of time with a negative style.
	 */
	public static final Style STYLE_ALERT = new Style(LENGTH_LONG, R.color.alert);
	
	/**
	 * Show the text notification for a short period of time with a positive style.
	 */
	public static final Style STYLE_CONFIRM = new Style(LENGTH_SHORT, R.color.confirm);
	
	/**
	 * Show the text notification for a short period of time with a neutral style.
	 */
	public static final Style STYLE_INFO = new Style(LENGTH_SHORT, R.color.info);
    
    private final Activity mContext;
    private int mDuration = LENGTH_SHORT;
    private View mView;
    private LayoutParams mLayoutParams;
    protected Animation mInAnimation, mOutAnimation;

	/**
	 * Construct an empty AppMsg object. You must call {@link #setView} before
	 * you can call {@link #show}.
	 * 
	 * @param context
	 *            The context to use. Usually your
	 *            {@link android.app.Activity} object.
	 */
	public AppMsg(Activity context) {
		mContext = context;
	}
    
	/**
	 * Make a {@link AppMsg} that just contains a text view.
	 * 
	 * @param context
	 *            The context to use. Usually your
	 *            {@link android.app.Activity} object.
	 * @param text
	 *            The text to show. Can be formatted text.
	 * @param duration
	 *            How long to display the message. Either {@link #LENGTH_SHORT}
	 *            or {@link #LENGTH_LONG}
	 * 
	 */
    public static AppMsg makeText(Activity context, CharSequence text, Style style) {
    	AppMsg result = new AppMsg(context);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.app_msg, null);
        v.setBackgroundResource(style.background);
        
        TextView tv = (TextView) v.findViewById(android.R.id.message);
        tv.setText(text);
        
        result.mView = v;
        result.mDuration = style.duration;

        return result;
    }

	/**
	 * Make a {@link AppMsg} that just contains a text view with the text from a
	 * resource.
	 * 
	 * @param context
	 *            The context to use. Usually your
	 *            {@link android.app.Activity} object.
	 * @param resId
	 *            The resource id of the string resource to use. Can be
	 *            formatted text.
	 * @param duration
	 *            How long to display the message. Either {@link #LENGTH_SHORT}
	 *            or {@link #LENGTH_LONG}
	 * 
	 * @throws Resources.NotFoundException
	 *             if the resource can't be found.
	 */
	public static AppMsg makeText(Activity context, int resId, Style style)
			throws Resources.NotFoundException {
		return makeText(context, context.getResources().getText(resId), style);
	}
    
    /**
     * Show the view for the specified duration.
     */
    public void show() {
    	MsgManager manager = MsgManager.getInstance();
        manager.add(this);
    }
    
    /**
     * @return <code>true</code> if the {@link AppMsg} is being displayed, else <code>false</code>.
     */
    boolean isShowing() {
      return mView != null && mView.getParent() != null;
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    public void cancel() {
    	MsgManager.getInstance().clearMsg(this);
    }
    
    /**
	 * Cancels all queued {@link AppMsg}s. If there is a {@link AppMsg}
	 * displayed currently, it will be the last one displayed.
	 */
	public static void cancelAll() {
		MsgManager.getInstance().clearAllMsg();
	}
    
    /**
     * Return the activity.
     */
    public Activity getActivity() {
      return mContext;
    }
    
    /**
     * Set the view to show.
     * @see #getView
     */
    public void setView(View view) {
        mView = view;
    }

    /**
     * Return the view.
     * @see #setView
     */
    public View getView() {
        return mView;
    }
    
    /**
     * Set how long to show the view for.
     * @see #LENGTH_SHORT
     * @see #LENGTH_LONG
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * Return the duration.
     * @see #setDuration
     */
    public int getDuration() {
        return mDuration;
    }

    /**
     * Update the text in a AppMsg that was previously created using one of the makeText() methods.
     * @param resId The new text for the AppMsg.
     */
    public void setText(int resId) {
        setText(mContext.getText(resId));
    }
    
    /**
     * Update the text in a AppMsg that was previously created using one of the makeText() methods.
     * @param s The new text for the AppMsg.
     */
    public void setText(CharSequence s) {
        if (mView == null) {
            throw new RuntimeException("This AppMsg was not created with AppMsg.makeText()");
        }
        TextView tv = (TextView) mView.findViewById(android.R.id.message);
        if (tv == null) {
            throw new RuntimeException("This AppMsg was not created with AppMsg.makeText()");
        }
        tv.setText(s);
    }

    /**
     * Gets the crouton's layout parameters, constructing a default if necessary.
     * @return the layout parameters
     */
    public LayoutParams getLayoutParams() {
        if (mLayoutParams == null) {
            mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return mLayoutParams;
    }

    /**
     * Sets the layout parameters which will be used to display the crouton.
     * @param layoutParams The layout parameters to use.
     * @return <code>this</code>, for chaining.
     */
    public AppMsg setLayoutParams(LayoutParams layoutParams) {
        mLayoutParams = layoutParams;
        return this;
    }

    /**
     * Constructs and sets the layout parameters to have some gravity.
     * @param gravity the gravity of the Crouton
     * @return <code>this</code>, for chaining.
     * @see android.view.Gravity
     */
    public AppMsg setLayoutGravity(int gravity) {
        mLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, gravity);
        return this;
    }

    /**
     * Sets the Animations to be used when displaying/removing the Crouton.
     * @param inAnimation the Animation resource ID to be used when displaying.
     * @param outAnimation the Animation resource ID to be used when removing.
     */
    public AppMsg setAnimation(int inAnimation, int outAnimation) {
        return setAnimation(AnimationUtils.loadAnimation(mContext, inAnimation),
                AnimationUtils.loadAnimation(mContext, outAnimation));
    }

    /**
     * Sets the Animations to be used when displaying/removing the Crouton.
     * @param inAnimation the Animation to be used when displaying.
     * @param outAnimation the Animation to be used when removing.
     */
    public AppMsg setAnimation(Animation inAnimation, Animation outAnimation) {
        mInAnimation = inAnimation;
        mOutAnimation = outAnimation;
        return this;
    }

	/**
	 * The style for a {@link AppMsg}.
	 * @author e.shishkin
	 */
    public static class Style {
		
		private final int duration;
		private final int background;

		/**
		 * Construct an {@link AppMsg.Style} object.
		 * 
		 * @param duration
		 *            How long to display the message. Either
		 *            {@link #LENGTH_SHORT} or {@link #LENGTH_LONG}
		 * @param resId
		 *            resource for AppMsg background
		 */
		public Style(int duration, int resId) {
			this.duration = duration;
			this.background = resId;
		}

		/**
		 * Return the duration in milliseconds.
		 */
		public int getDuration() {
			return duration;
		}

		/**
		 * Return the resource id of background.
		 */
		public int getBackground() {
			return background;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof AppMsg.Style)) {
				return false;
			}
			Style style = (Style) o;
			return style.duration == duration 
					&& style.background == background;
		}
		
	}

}
