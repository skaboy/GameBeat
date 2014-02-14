package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.BeatGame.Animation.MyAnimationView;
import com.BeatGame.Management.R;

public class GameManager extends Activity {
	
	//Speed of the game
	private int speed;
	
	//SetupManager.level enum
	private String level;
	
	//The current score
	private int score;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
		System.out.println(intent.getStringExtra("level"));
		
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
