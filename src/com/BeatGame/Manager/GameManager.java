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

	// Speed of the game
	private int speed;
	private String level; // SetupManager.level should be an enum
	private int score;
	private int levelRank = 0;
	private ButtonManager buttonManager;
	private Scene sceneManager;
	private RelativeLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
		level = intent.getStringExtra("level");
		container = (RelativeLayout) findViewById(R.id.container);
		buttonManager = new ButtonManager(this);
		sceneManager = new Scene(this, 800, 800);
		final MyAnimationView animView = new MyAnimationView(this, sceneManager);
		container.addView(animView);
		this.startGame();

		Button starter = (Button) findViewById(R.id.startButton);
		starter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Add button to the screen
				for (int j = 0; j < levelRank; j++) {
					sceneManager.drawButton(buttonManager.buttons().get(j),
							GameManager.this, container);
				}
				// animView.startAnimation();
				animView.restartAnimation();
			}
		});
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

	public void startGame() {
		// create list of button in ButtonManger
		if (level.equals("easy")) {
			levelRank = 10;
		} else if (level.equals("normal")) {
			levelRank = 20;
		} else if (level.equals("hard")) {
			levelRank = 25;
		}
		for (int j = 0; j < levelRank; j++) {
			buttonManager.createButton(new Position(j*10, 10 * j), 10, 25, 1000);
			sceneManager.setButton(buttonManager.buttons().get(j));
		}

	}
}
