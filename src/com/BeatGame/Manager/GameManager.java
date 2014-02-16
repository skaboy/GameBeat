package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BeatGame.Animation.MyAnimationView;
import com.BeatGame.Component.ButtonManager;
import com.BeatGame.Component.Circle;
import com.BeatGame.Component.Position;
import com.BeatGame.Management.R;
import com.BeatGame.UI.Scene;

public class GameManager extends Activity {
	
	//Speed of the game
	private int speed;
	
	//SetupManager.level enum
	private String level;
	
	//The current score
	private int score;
	
	private ButtonManager buttonManager;
	private Scene sceneManager;
	private RelativeLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
		System.out.println(intent.getStringExtra("level"));
		
		container = (RelativeLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        
        
        buttonManager = new ButtonManager(this);
        
        buttonManager.createButton(50, new Position(10, 0), null, new Circle(new Position(0,1), 0, 2, 11, null));
        buttonManager.createButton(50, new Position(80, 80), null, new Circle(new Position(0,1), 0, 2, 11, null));
        
        sceneManager = new Scene();
        Log.e("==> Scene",sceneManager.setButton(buttonManager.buttons().get(0))+"");
        Log.e("==> Scene",sceneManager.setButton(buttonManager.buttons().get(1))+"");
        
        
        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Add button to the screen
                
            	sceneManager.drawButton(buttonManager.buttons().get(0), GameManager.this, container);
            	sceneManager.drawButton(buttonManager.buttons().get(1), GameManager.this, container);
            	
            	            	animView.startAnimation();
            	
                	
            }
        });
        
		level =intent.getStringExtra("level");
		System.out.println(level);
        if(level.equals(SetupManager.level.easy)){
        	speed=0;
        }else if (level.equals(SetupManager.level.normal)){
        	speed=1;
        }else if(level.equals(SetupManager.level.hard)){
        	speed=2;
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_manager, menu);
		return true;
	}
	
	
	public String level(){
		return level;
	}
	
	
	public void setLevel(String lvl){
		level=lvl;
	}

}