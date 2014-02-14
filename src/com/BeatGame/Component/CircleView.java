package com.BeatGame.Component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

	private ShapeDrawable mDrawable;
	
	int x = 10;
	int y = 10;

	int width = 100;
	int height = 150;

	public CircleView(Context context) {

		super(context);

		mDrawable = new ShapeDrawable(new OvalShape());

		mDrawable.getPaint().setColor(0xff74AC23);

	}

	public CircleView(Context context, AttributeSet attr) {

		super(context, attr);

		mDrawable = new ShapeDrawable(new OvalShape()); // ici on affiche un
														// oval...
		mDrawable.getPaint().setColor(0xff74AC23);

	}

	protected void onDraw(Canvas canvas) {

		mDrawable.setBounds(x, y, x + width, y + height);
		mDrawable.draw(canvas);
	}
}