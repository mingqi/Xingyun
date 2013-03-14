package com.xingyun.excontrol;

import com.xingyun.activity.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TableLayout;

public class RoundedRectEditTextLayout extends EditText {

	public RoundedRectEditTextLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		Drawable dr = this.getResources().getDrawable(
				R.drawable.rounded_corner_border);
		setBackgroundDrawable(dr);
		setVerticalFadingEdgeEnabled(false);
	}
}
