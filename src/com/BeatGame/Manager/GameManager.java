package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.BeatGame.Animation.MyAnimationView;
import com.BeatGame.Component.ButtonManager;
import com.BeatGame.Component.Position;
import com.BeatGame.Management.R;

public class GameManager extends Activity {

	// Speed of the game
	private int speed;

	// SetupManager.level enum
	private String level;

	// The current score
	private int score;

	private ButtonManager buttonManager;

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
		level = intent.getStringExtra("level");
		System.out.println(level);
		buttonManager = new ButtonManager(this);
		this.startGame();
		// if(level.equals(SetupManager.level.easy)){
		// speed=0;
		// }else if (level.equals(SetupManager.level.normal)){
		// speed=1;
		// }else if(level.equals(SetupManager.level.hard)){
		// speed=2;
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_manager, menu);
		return true;
	}

	public String level() {
		return level;
	}

	public void setLevel(String lvl) {
		level = lvl;
	}

	public void startGame(){
		//create list oif button in ButtonManger
		int i = 0;
		if(level.equals("easy")){
			i=10;
		}else if(level.equals("normal")){
			i=20;
		} else if(level.equals("hard")){
			i=25;
		}
		for(int j=0; j<i;j++){
			buttonManager.createButton(10, new Position(j, 10+j), null, null);
		}
	}
}
