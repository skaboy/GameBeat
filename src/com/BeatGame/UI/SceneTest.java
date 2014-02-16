package com.BeatGame.UI;
import android.content.Context;
import android.media.Image;
import android.util.Log;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Circle;
import com.BeatGame.Component.Position;
import com.BeatGame.UI.Scene;

import junit.framework.TestCase;
import java.util.prefs.Preferences;

public class SceneTest extends TestCase {

    Scene myScene;
    Context context;

    public SceneTest(Context ctx){
        myScene = new Scene(context, 100,100);
        context = ctx;
        initTest();
    }

    public void initTest() {

        System.out.println("Starting the Scene Test");
        // we hope it's empty at first !
        assert (myScene.buttonsMapSize() == 0);
        System.out.println("Empty Map : ok ");

        // add one button
        //BeatButton(Context context, int id, int size, Position position, Circle circle) {
        BeatButton button1 = new BeatButton(context, 1, 10, new Position(0,0), new Circle(1000, 20));
        Log.e("<TEST> Successfully add one button into the scene", (myScene.setButton(button1))? "Ok": "Fail" ) ;

        // add the exact same button, shouldn't be possible
        BeatButton button1Bis = new BeatButton(context, 2, 10, new Position(0,0),new Circle(1000, 20));
        Log.e("<TEST> Successfully failed to add the same one button into the scene", (!myScene.setButton(button1Bis)&& myScene.buttonsMapSize()== 1)? "Ok": "Fail" ) ;

        // add an other button, should work !
        BeatButton button2 = new BeatButton(context, 3, 10, new Position(15,15), new Circle(1000, 20));
        Log.e("<TEST> Successfully add a second button into the scene", (myScene.setButton(button2)&& myScene.buttonsMapSize()== 1)? "Ok": "Fail" ) ;

        // check the list
    }

}
