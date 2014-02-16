package com.BeatGame.Component;

import java.util.ArrayList;

import android.content.Context;
import android.media.Image;

public class ButtonManager {

	private ArrayList<BeatButton> buttonList;
	Context context;
	int buttonID;

	// Construction of the button Manager
	public ButtonManager(Context context) {
		this.context = context;
		buttonList = new ArrayList<BeatButton>();
		buttonID = 0;
	}

	// return the id of the button at the position in parameter
	public int buttonAtPosition(Position position) {
		return 0; // No button
	}

	// Create a BeatButton in the buttonList
	// return the ID of the buttonS
	public int createButton(int size, Position position, Image image,
			Circle circle) {
		buttonID++;
		buttonList.add(new BeatButton(this.context, this.buttonID, size,
				position, image, circle));
		return buttonID;

	}

	// delete a BeatButton in the buttonList
	public int deleteButton(int id) {
		for (BeatButton button : buttonList) {
			if (button.id() == id)
				buttonList.remove(button);
		}
		return 0;
	}

	public ArrayList<BeatButton> buttons() {
		return buttonList;
	}
	
	

}
