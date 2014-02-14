package com.BeatGame.Component;

import android.content.Context;
import android.media.Image;
import android.widget.Button;

public class BeatButton extends Button{
	
	int id, size;
	Position position;
	Image image;
	Circle circle;

	public BeatButton(Context context, int id, int size, Position position, Image image, Circle circle) {
		super(context);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.size = size;
		this.position = position;
		this.image = image;
		this.circle = circle;
	}
	
	
	
}
