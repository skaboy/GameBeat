package com.BeatGame.UI;
//import com.BeatGame.UI.SceneInterface;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Position;

public class Scene implements SceneInterface {

    int width = 0;
    int height = 0;
    HashMap<BeatButton, Position> buttonsMap = new HashMap<BeatButton, Position>() ;

    @Override
    public int sceneWidth() {
        return width;
    }

    @Override
    public int sceneHeight() {
        return height;
    }

    @Override
    public void setSceneDimension(int h, int w) {
        width = w ;
        height = h;
    }

    @Override
    public boolean setButton(BeatButton b) {
        if ( !isOverlapping(b) ) {
            buttonsMap.put(b, b.position());
            return true;
        }
        return false;
    }

    @Override
    public boolean setButtonList(List<BeatButton> bl) {
        boolean ok = false;
        for (int i=0 ; i < bl.size() ; i++){
            ok &= setButton( bl.get(i) );
        }
        return ok;
    }

    @Override
    public int isButtonAt(Position pos) {
        // return the ID of the button is there is already one there
        Position buttonPos;
        for (BeatButton key : buttonsMap.keySet()) {
            buttonPos = buttonsMap.get(key);
            if ( isPointInCircle(pos.x(), pos.y(), buttonPos.x(), buttonPos.y(), key.size()) ) {
                return key.id();
            }
        }
        return -1; // mean there is not button
    }

    private boolean isOverlapping(BeatButton b){
        // calculus : check two centers

        boolean overlap = false ;
        Position p = b.position();
        Position pos; int radius;
        for (BeatButton key : buttonsMap.keySet()) {
            radius = key.size();
            pos = buttonsMap.get(key);
            overlap |=  collisionBetweenCircles(pos.x(), pos.y(), radius, p.x(), p.y(), b.size());
        }
        return overlap;
    }

    // calculate if the point (x,y) is in the circle (a,b,radius)
    private boolean isPointInCircle(int x,int y, int a, int b, int radius)
    {
        int d2 = (x-a)*(x-a) + (y-b)*(y-b);
        return (d2 > radius*radius); // return true when the point is in the circle
    }

    // calculate if there is collision between C1 and C2 where C1 is x, y and its air.
    private boolean collisionBetweenCircles(int x, int y, int radius1, int a, int b, int radius2) {
        // is the distance between two centers bigger than the sum of their air
        int d2 = (x-a)*(x-a) + (y-b)*(y-b);
        return (d2 < (radius1 + radius2)*(radius1 + radius2)); // return true if there is collision
    }
    
    public boolean drawButton(BeatButton button, Context context, RelativeLayout  layout){
    	

    	RelativeLayout.LayoutParams params;
    	
        button.setText("Button");
        params = new RelativeLayout.LayoutParams(button.size(),button.size());
        params.leftMargin = button.position().x();
        params.topMargin = button.position().y();
        
    	Log.e("Draw button","button");

        
        layout.addView(button, params);
        
            	
    	return true;
    }
    
    
   
}
