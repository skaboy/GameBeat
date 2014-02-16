package com.BeatGame.Component;

import java.util.ArrayList;

import android.content.Context;
import android.media.Image;

public class ButtonManager {

	private ArrayList<BeatButton> buttonList;
	Context context;
	int buttonID;

	//Construction of the  button Manager
	public ButtonManager(Context context){
		this.context = context;
		buttonList = new ArrayList<BeatButton>();
		buttonID = 0;
	}

	// return the id of the button at the position in parameter
	public int buttonAtPosition(Position position) {
		return 0; // No button
	}


    // create the button and the circle associated, return the id of the button
    public int createButton(Position position, int size, int circleSize, float circleDuration){
        buttonID++; // cannot be index O with this , normal ?
        Circle circle = new Circle(circleSize, circleDuration);
        BeatButton button = new BeatButton(this.context, this.buttonID, size, position, circle);
        buttonList.add(button);
        return buttonID;
    }

	//delete a BeatButton in the buttonList
	public int deleteButton(int id){
		for (BeatButton button : buttonList){
			if (button.id() == id) buttonList.remove(button);
		}
		return 0;
	}

	public ArrayList<BeatButton> buttons() {
		return buttonList;
	}
}
