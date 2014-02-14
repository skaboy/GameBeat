package com.BeatGame.Component;

import android.graphics.Color;

public class Circle {

	Position initialPosition;
	int speed, thickness;
	float currentSize; // r of the circle
	Color color;

	
	public Circle(Position initiaPosition, int speed, int thickness, float size, Color color){
		
		this.initialPosition = initiaPosition;
		this.speed = speed;
		this.thickness = thickness;
		this.currentSize = size;
		this.color = color;
		
	}
	
}
