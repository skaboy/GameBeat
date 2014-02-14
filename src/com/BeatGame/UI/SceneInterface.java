package com.BeatGame.UI;

import android.widget.Button;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Position;
import java.util.List;

public interface SceneInterface {

    public int sceneWidth();

    public int sceneHeight();

    // Resize the scene
    public void setSceneDimension(int h, int w);

     //check the size of the button and place it in the layout
     public boolean setButtonAt(BeatButton b, Position p);

    //same as setButtonAt but accept list
    public boolean setButtonListAt( List<BeatButton> bl, List<Position> pl);

    // return ID button
    public int isButtonAt(Position pos);
}
