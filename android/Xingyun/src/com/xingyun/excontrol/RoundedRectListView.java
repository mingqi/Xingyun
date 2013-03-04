package com.xingyun.excontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class RoundedRectListView extends ListView {
	private static final float RADIUS = 16;
	private Path mClip;

	public RoundedRectListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		GradientDrawable gd = new GradientDrawable();
		gd.setCornerRadius(RADIUS);
		gd.setColor(0xff208020);
		setBackgroundDrawable(gd);
		setCacheColorHint(0);
		setVerticalFadingEdgeEnabled(false);
		StateListDrawable sld = new StateListDrawable();
		sld.addState(PRESSED_ENABLED_STATE_SET, new GradientDrawable(
				Orientation.LEFT_RIGHT, new int[] { 0xffa58cf5, 0xffa13f99 }));
		sld.addState(EMPTY_STATE_SET, new GradientDrawable(
				Orientation.LEFT_RIGHT, new int[] { 0xff058cf5, 0xff013f99 }));
		setSelector(sld);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mClip = new Path();
		RectF rect = new RectF(0, 0, w, h);
		mClip.addRoundRect(rect, RADIUS, RADIUS, Direction.CW);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		canvas.save();
		canvas.clipPath(mClip);
		super.dispatchDraw(canvas);
		canvas.restore();
	}
}