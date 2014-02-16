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
import com.BeatGame.UI.Scene;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.ArrayList;

public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

        private static final float BALL_SIZE = 100f;
        private static final int DURATION = 3500;

        public ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        AnimatorSet animation = null;
        Animator bounceAnim = null;
        ShapeHolder ball = null;
        private Scene scene;
        
        int abc = 10;
        
        public MyAnimationView(Context context, Scene sc) {
            super(context);
            scene=sc;
            
        }

        private void createAnimation() {
            //if (bounceAnim == null) {
                //ShapeHolder ball;
               /* ball = balls.get(0);
               ObjectAnimator yBouncer = ObjectAnimator.ofFloat(ball, "y",
                        ball.getY(), getHeight() - BALL_SIZE).setDuration(DURATION);
                yBouncer.setInterpolator(new CycleInterpolator(2));
                yBouncer.addUpdateListener(this);*/

                /*ball = balls.get(1);
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                        getHeight() - BALL_SIZE);
                PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
                ObjectAnimator yAlphaBouncer = ObjectAnimator.ofPropertyValuesHolder(ball,
                        pvhY, pvhAlpha).setDuration(DURATION/2);
                yAlphaBouncer.setInterpolator(new AccelerateInterpolator());
                yAlphaBouncer.setRepeatCount(1);
                yAlphaBouncer.setRepeatMode(ValueAnimator.REVERSE);*/

            	ArrayList<Animator> listAnimator = new ArrayList<Animator>();
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
	                //whxyBouncer.setRepeatCount(1);
	                whxyBouncer.setRepeatMode(ValueAnimator.REVERSE);
	                listAnimator.add(whxyBouncer);
                }

                ((ObjectAnimator)listAnimator.get(0)).addUpdateListener(this);
                /*ball = balls.get(3);
                pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(), getHeight() - BALL_SIZE);
                float ballX = ball.getX();
                Keyframe kf0 = Keyframe.ofFloat(0f, ballX);
                Keyframe kf1 = Keyframe.ofFloat(.5f, ballX + 100f);
                Keyframe kf2 = Keyframe.ofFloat(1f, ballX + 50f);
                PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("x", kf0, kf1, kf2);
                ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhY,
                        pvhX).setDuration(DURATION/2);
                yxBouncer.setRepeatCount(1);
                yxBouncer.setRepeatMode(ValueAnimator.REVERSE);*/

                bounceAnim = new AnimatorSet();
                ((AnimatorSet)bounceAnim).playTogether( listAnimator
                        );

                
                
          //  }
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
        	invalidate();
        }

        public void restartAnimation() {
        	balls.clear();
    		HashMap<BeatButton, Position> buttons = scene.buttonsMap();
    		for (BeatButton key : buttons.keySet()) {
    			addBall(buttons.get(key).x(), buttons.get(key).y());
    		}
    		for(ShapeHolder ball: balls){
        		ball.setAlpha(50);
     	}
    		createAnimation();
    		bounceAnim.start();
    	}
    }
