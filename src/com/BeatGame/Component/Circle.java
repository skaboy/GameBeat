package com.BeatGame.Component;


import android.graphics.Color;
import static java.lang.System.*;

// Circle that is shrinking around the button, not aware of the button tho.
public class Circle {

	int duration; // millisecond before the circle reach the button
    int thickness ;
	float initialSize; // distance from where the circle starts shrinking
	Color color;
	long startTime;

    // Create a circle with the millisecond before touching the button and the original size,
    // the x and W can be obtained through the button it belongs to.
	public Circle(int d, float is){
		duration = d;
		initialSize = is;
		startTime = currentTimeMillis();
        // setup a default thickness
        // setup a default color later
	}

    public int duration() {
        return duration;
    }

    public int thickness() {
        return thickness;
    }

    public float initialSize() {
        return initialSize;
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setThickness(int t) {
        thickness = t;
    }
    
    public long startTime() {
        return startTime;
    }
}
