package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.BeatGame.Management.R;

public class GameManager extends Activity {
	
	private int speed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
        String level =intent.getStringExtra("level");
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

}
