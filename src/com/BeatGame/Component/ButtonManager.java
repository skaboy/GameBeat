package com.BeatGame.Component;

import java.util.ArrayList;

import android.content.Context;
import android.media.Image;

public class ButtonManager {

	ArrayList<BeatButton> buttonList;
	Context context;
	int buttonID;

	public ButtonManager(Context context){
		this.context = context;
		buttonList = new ArrayList<BeatButton>();
		buttonID = 0;
	}

	public int buttonAtPosition(Position position){
		return 0; // No button
	}

	public int createButton(int size, Position position, Image image, Circle circle){
		buttonID++;
		buttonList.add( new BeatButton(this.context, this.buttonID, size, position, image, circle));
		return buttonID;
	}

	public int deleteButton(int id){
		for (BeatButton button : buttonList){
			if (button.id == id) buttonList.remove(button); 
		}
		return 0;
	}
}
