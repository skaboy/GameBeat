package com.BeatGame.Manager;

import java.util.ArrayList;
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
	public  static GameManager gameManager;
	private static boolean currentRound = false;
	private ArrayList<CircleListener> circleListeners;
	public static GameMainThread gameMainThread;
	private static boolean isOnPause = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
		level = intent.getStringExtra("level");
		container = (RelativeLayout) findViewById(R.id.container);
		buttonManager = new ButtonManager(this);
		sceneManager = new Scene(this, 800, 800);
		circleListeners = new ArrayList<GameManager.CircleListener>();
		gameMainThread = new GameMainThread();
		restartGame();
		
		gameManager = this;
		
		Button btn = (Button) findViewById(R.id.startButton);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(GameManager.this, PauseActivity.class);
				//myIntent.putExtra("key", value); //Optional parameters
				startActivity(myIntent);
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isOnPause = true;
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isOnPause = false;
		for(CircleListener listenerThread : circleListeners){
			synchronized (listenerThread) {
				listenerThread.notify();
			}
				
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		for(CircleListener listenerThread : circleListeners){
			listenerThread.cancel(true);
		}
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

	public void restartGame() {
		Log.e("START GAME", " ===========> ");

		// set current round
		currentRound = !currentRound;

		// reset view
		sceneManager.clearBeatButtonType(container);

		// reset
		buttonManager.clearButtons();
		sceneManager.clearButtons();
		circleListeners.clear();

		// remove animation view
		for (int i = 0; i < container.getChildCount(); i++) {
			if (container.getChildAt(i) instanceof MyAnimationView)
				container.removeViewAt(i);
		}

		// create list of button in ButtonManger
		if (level.equals("easy")) {
			levelRank = 5;
		} else if (level.equals("normal")) {
			levelRank = 20;
		} else if (level.equals("hard")) {
			levelRank = 25;
		}

		// Initialize buttons
		for (int j = 1; j <= levelRank; j++) {
			buttonManager.createButton(new Position(j * 100, 100), 50, 100,
					1000);
			sceneManager.setButton(buttonManager.buttons().get(j - 1));
		}

		final MyAnimationView animView = new MyAnimationView(this, sceneManager);
		container.addView(animView);
		animView.restartAnimation();

		HashMap<BeatButton, Position> buttons = sceneManager.buttonsMap();
		int i = 0;
		for (BeatButton key : buttons.keySet()) {
			final int t = i;
			final BeatButton tmpButton = key;
			sceneManager.drawButton(key, GameManager.this, container);

			key.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View button) {
					// TODO Auto-generated method stub
					sceneManager.removeButton((BeatButton) button,
							GameManager.this, container);
					animView.hideView(t);
				}
			});

			circleListeners.add(t,new CircleListener(currentRound));
			circleListeners.get(t).execute(new Object[] { tmpButton, new Integer(t), animView });
			
			i++;

		}
	}
	
	private class GameMainThread extends AsyncTask<Object, Void, String> {


		@Override
		protected String doInBackground(Object... btns) {

			try {
				Thread.sleep(MyAnimationView.getDuration());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String str) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.e("======> ","Thread WAKEUP");

		}
	}
	

	private class CircleListener extends AsyncTask<Object, Void, BeatButton> {

		int circleIndex = 0;
		MyAnimationView anV;
		boolean round;

		public CircleListener(boolean round) {
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
			
			synchronized (this) {
				Log.e("THREAD PAUSE =========> : ", ""+isOnPause);
				if (isOnPause) {
					// Log.e("=========> : ", "ON PAUSE");

					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
				
			
			circleIndex = (Integer) btns[1];
			anV = (MyAnimationView) btns[2];
			return (BeatButton) btns[0];
		}

		@Override
		protected void onPostExecute(BeatButton btn) {
				if (round == GameManager.currentRound) {
					anV.hideView(circleIndex);
					sceneManager.removeButton(btn, GameManager.this, container);
				} else {
					Log.e("THREAD =========> : ", "NOT RUNNING");
				}
		}
	}
}
