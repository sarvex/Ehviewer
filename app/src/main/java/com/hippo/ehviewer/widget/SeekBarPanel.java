/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.hippo.ehviewer.R;
import com.hippo.yorozuya.ViewUtils;

public class SeekBarPanel extends LinearLayout implements View.OnApplyWindowInsetsListener {

    private SeekBar mSeekBar;
    private final int[] mLocation = new int[2];

    public SeekBarPanel(Context context) {
        super(context);
    }

    public SeekBarPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekBarPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSeekBar = (SeekBar) ViewUtils.$$(this, R.id.seek_bar);
        setOnApplyWindowInsetsListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mSeekBar == null) {
            return super.onTouchEvent(event);
        } else {
            ViewUtils.getLocationInAncestor(mSeekBar, mLocation, this);
            final float offsetX = -mLocation[0];
            final float offsetY = -mLocation[1];
            event.offsetLocation(offsetX, offsetY);
            mSeekBar.onTouchEvent(event);
            event.offsetLocation(-offsetX, -offsetY);
            return true;
        }
    }

    @Override
    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        setPadding(insets.getSystemWindowInsetLeft(), 0, insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
        return insets.consumeSystemWindowInsets();
    }
}
