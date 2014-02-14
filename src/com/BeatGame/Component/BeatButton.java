package com.BeatGame.Component;

import android.content.Context;
import android.media.Image;
import android.widget.Button;

public class BeatButton extends Button{
	
	private int id, size;
	Position position;
	Image image;
	Circle circle;

	public BeatButton(Context context, int id, int size, Position position, Image image, Circle circle) {

        // TODO : size need to be calculate according to the given picture
        // or we have to agree that all the used picture have the same size and define constant when this
        // constructor is called.
        super(context);
		this.id = id;
		this.size = size;
		this.position = position;
		this.image = image;
		this.circle = circle;
	}

    public int id(){
        return id;
    }

    public int size(){
        return size;
    }
    
    public Position getPosition(){
    	return this.position;
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