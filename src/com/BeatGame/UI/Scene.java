package com.BeatGame.UI;
//import com.BeatGame.UI.SceneInterface;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.content.Context;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Position;
import com.BeatGame.Management.R;

public class Scene implements SceneInterface {

    int width = 0;
    int height = 0;
    Context context;
    HashMap<BeatButton, Position> buttonsMap = new HashMap<BeatButton, Position>() ;

    // @ctor
    public Scene(Context ctx, int h, int w) {
        width = w;
        height = h;
        context = ctx;
        buttonsMap = new HashMap<BeatButton, Position>() ;
        System.out.println("Scene constructed and return !");
    }

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
        System.out.println("About to set a button");
        if ( !isOverlapping(b) ) {
            buttonsMap.put(b, b.position());
            return true;
        }
        return false;
    }

    @Override
    public boolean setButtonList(List<BeatButton> bl) {
        boolean ok = true;
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
            overlap |=  collisionBetweenCircles(b,key);
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
    private boolean collisionBetweenCircles(BeatButton b1, BeatButton b2) {
        // is the distance between two centers bigger than the sum of their air
        int x = b1.position().x();
        int y = b1.position().y();
        int radius1 = b1.size();

        int a = b2.position().x();
        int b = b2.position().y();
        int radius2 = b2.size();

        int d2 = (x-a)*(x-a) + (y-b)*(y-b);
        return (d2 < (radius1 + radius2)*(radius1 + radius2)); // return true if there is collision
    }

    public boolean drawButton(BeatButton button, Context context, RelativeLayout  layout){
    	
    	RelativeLayout.LayoutParams params;
    	
        button.setText("1");
        button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_button));
        params = new RelativeLayout.LayoutParams(button.size(),button.size());
        params.leftMargin = button.position().x();
        params.topMargin = button.position().y();
        
    	Log.e("Draw button","button");
        
        layout.addView(button, params);
                    	
    	return true;
    }
 
    public boolean removeButton(BeatButton button , Context context, RelativeLayout layout){
    	
    	layout.removeView(button);
    	
    	return true;
    }
    
    public int buttonsMapSize() {
        return buttonsMap.size();
    }

}
