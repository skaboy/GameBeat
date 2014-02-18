package com.BeatGame.Animation;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.View;

import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.Position;
import com.BeatGame.Manager.GameManager;
import com.BeatGame.UI.Scene;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;

public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

        private static final float BALL_SIZE = 100f;
        private static final int DURATION = 4500;

        public ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        ArrayList<Animator> listAnimator;
        AnimatorSet animation = null;
        Animator bounceAnim = null;
        ShapeHolder ball = null;
        private Scene scene;
        private ValueAnimator valueAnimator = null;
                
        public MyAnimationView(Context context, Scene sc) {
            super(context);
            scene=sc;
            
        }
        
        public static int getDuration(){
        	return DURATION;
        }

        private void createAnimation() {

            	listAnimator = new ArrayList<Animator>();
                for(ShapeHolder ball:balls){
	                PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width", ball.getWidth(),
	                        ball.getWidth() / 2);
	                PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height", ball.getHeight(),
	                        ball.getHeight() / 2);
	                PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", ball.getX(),
	                        ball.getX()+25);
	                PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", ball.getY(),
	                        ball.getY()+25);
	                ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhW, pvhH,
	                        pvTX, pvTY).setDuration(DURATION);
	                listAnimator.add(whxyBouncer);
                }

                ((ObjectAnimator)listAnimator.get(0)).addUpdateListener(this);
                bounceAnim = new AnimatorSet();
                ((AnimatorSet)bounceAnim).playTogether( listAnimator);

        }

        public void startAnimation(ArrayList<Position> positions) {
        		balls.clear();
        		for(Position pos : positions){
        			addBall(pos.x()-25, pos.y()-25);
        		}    	
        		for(ShapeHolder ball: balls){
	        		ball.setAlpha(50);
        		}
        
            createAnimation();
            bounceAnim.start();
            
        }
        
        public void configureAddBall(int x, int y){
        	
        	addBall(x, y);
        }

        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            int red = (int)(100 + Math.random() * 155);
            int green = (int)(100 + Math.random() * 155);
            int blue = (int)(100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint();
            int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            
            paint.setShader(gradient);
            //paint.setColor(android.R.color.transparent);
            
           
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder ball : balls) {
                canvas.translate(ball.getX(), ball.getY());
                ball.getShape().draw(canvas);
                canvas.translate(-ball.getX(), -ball.getY());
            }
        }

        public void hideView(int id){
        	balls.get(id).setAlpha(0);
        }
       
        public void onAnimationUpdate(ValueAnimator animation) {
        	if(!GameManager.isOnPause){
        		invalidate();
        		valueAnimator=null;
        	}else{
        		if(valueAnimator==null) valueAnimator = animation;
        	}
        }
        
        public void restartAnimation() {
        	
        	balls.clear();
    		HashMap<BeatButton, Position> buttons = scene.buttonsMap();
    		//Log.e("SIZE MAP ===> ",scene.buttonsMap().size()+"");
    		for (BeatButton key : buttons.keySet()) {
    			addBall(buttons.get(key).x()-key.size()/2, buttons.get(key).y()-key.size()/2);
    		}
    		//Log.e("SIZE BALL ===> ",balls.size()+"");
    		createAnimation();
    		bounceAnim.start();
    	}
    }
