package com.xingyun.excontrol;

import com.xingyun.activity.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class RoundedRectListView extends ListView {

	public RoundedRectListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		Drawable dr = this.getResources().getDrawable(
				R.drawable.rounded_corner_border);
		setBackgroundDrawable(dr);
		setCacheColorHint(0);
		setVerticalFadingEdgeEnabled(false);
	}

}