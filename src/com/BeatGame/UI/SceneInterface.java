package com.BeatGame.UI;

import java.util.List;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Position;

public interface SceneInterface {

    public int sceneWidth();

    public int sceneHeight();

    // Resize the scene
    public void setSceneDimension(int h, int w);

     //check the size of the button and place it in the layout
     public boolean setButton(BeatButton b);

    //same as setButtonAt but accept list
     public boolean setButtonList(List<BeatButton> bl);

    // return ID button
    public int isButtonAt(Position pos);
}
