package com.BeatGame.Manager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.BeatGame.Animation.MyAnimationView;
import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.ButtonManager;
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
	public static MyAnimationView animView;
	public static GameManager gameManager;
	private static boolean currentRound = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
		level = intent.getStringExtra("level");
		container = (RelativeLayout) findViewById(R.id.container);
		buttonManager = new ButtonManager(this);
		sceneManager = new Scene(this, 800, 800, buttonManager);
		animView = new MyAnimationView(this, sceneManager);
		container.addView(animView);
		startGame();

		
		Button starter = (Button) findViewById(R.id.startButton);
		/*starter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Add button to the screen
				

				ArrayList<Position> pos = new ArrayList<Position>();
				for (BeatButton btn : buttonManager.buttons()) {
					pos.add(btn.position());
				}

				//animView.startAnimation(pos);
				animView.restartAnimation();
				HashMap<BeatButton, Position> buttons = sceneManager.buttonsMap();
				int i=0;
	    		for (BeatButton key : buttons.keySet()) {
	    			final int t = i;
	    			final BeatButton tmpButton = key;
					sceneManager.drawButton(key,
							GameManager.this, container);

					key.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View button) {
									// TODO Auto-generated method stub
									sceneManager.removeButton( (BeatButton) button,GameManager.this, container);
									animView.hideView(t);
								}
							});
					
					CircleListener thread = new CircleListener();
					thread.execute(new Object[]{tmpButton, new Integer(t)});
					
					i++;
					
				}
			}
		});*/
		
		gameManager = this;
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
		Log.e("START GAME"," ===========> ");
		
		// set current round
		currentRound = !currentRound;
		
		// create list of button in ButtonManger
		if (level.equals("easy")) {
			levelRank = 5;
		} else if (level.equals("normal")) {
			levelRank = 20;
		} else if (level.equals("hard")) {
			levelRank = 25;
		}
		
		// reset
		buttonManager.clearButtons();
		sceneManager.clearButtons();
		
		// Initialize buttons
		for (int j =1; j <= levelRank; j++) {
			buttonManager.createButton(new Position(j * 50, 10), 50, 100,
					1000);
			sceneManager.setButton(buttonManager.buttons().get(j-1));
		}
		
		// Configure buttons
		animView.restartAnimation();
		HashMap<BeatButton, Position> buttons = sceneManager.buttonsMap();
		int i=0;
		for (BeatButton key : buttons.keySet()) {
			final int t = i;
			final BeatButton tmpButton = key;
			sceneManager.drawButton(key,
					GameManager.this, container);

			
			key.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View button) {
							// TODO Auto-generated method stub
							sceneManager.removeButton( (BeatButton) button,GameManager.this, container);
							//animView.hideView(t);
						}
			});
			
			CircleListener thread = new CircleListener(currentRound);
			thread.execute(new Object[]{tmpButton, new Integer(t)});
			
			i++;
			
		}
	}
	
	private class CircleListener extends AsyncTask<Object, Void, BeatButton> {

		int circleIndex = 0;
		boolean round;
		
		public CircleListener(boolean round){
			this.round = round;
		}
		
		@Override
		protected BeatButton doInBackground(Object... btns) {
			
			try {
				Thread.sleep(MyAnimationView.getDuration());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			circleIndex = (Integer) btns[1];
			return (BeatButton) btns[0];
		}

		@Override
		protected void onPostExecute(BeatButton btn) {
			//Log.e("Numbre Thread : ",""+sceneManager.buttonsMap().size());
			if(round==GameManager.currentRound){
				animView.hideView(circleIndex);
				sceneManager.removeButton(btn,
						GameManager.this, container);
			}
		}
	}
}
