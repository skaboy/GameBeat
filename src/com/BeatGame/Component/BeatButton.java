package com.BeatGame.Component;

import android.content.Context;
import android.media.Image;
import android.widget.Button;

public class BeatButton extends Button{
	
	private int id, size;
	Position position;
	Image image;
	Circle circle;

	public BeatButton(Context context, int id, int size, Position position, Circle circle) {

        super(context);
		this.id = id;
		this.size = size;
		this.position = position;
		this.circle = circle;
	}

    public int id(){
        return id;
    }

    public int size(){
        return size;
    }
    
    public Position position(){
    	return position;
    }

    public Circle circle(){
        return circle;
    }

    public void setImage(Image i) {
        image = i;
    }
    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if( o instanceof BeatButton)
            return ((BeatButton) o).id() == this.id;
        return false;
    }
}