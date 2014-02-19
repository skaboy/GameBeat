package com.BeatGame.Manager;

import java.util.ArrayList;

import android.content.Context;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Circle;
import com.BeatGame.Component.Position;

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

	public void clearButtons() {
		buttonList.clear();
		buttonID = 0;
	}

	// create the button and the circle associated, return the id of the button
	public int setButton(BeatButton button) {
		buttonID++;
		buttonList.add(button);
		return buttonID;
	}

	public BeatButton createButton(Position position, int size, int circleSize,
			float circleDuration) {
		Circle circle = new Circle(circleSize, circleDuration);
		BeatButton button = new BeatButton(this.context, this.buttonID + 1,
				size, position, circle);
		buttonList.add(button);
		return button;
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
