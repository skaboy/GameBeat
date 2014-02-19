package com.BeatGame.Manager;

import static java.lang.System.currentTimeMillis;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeatGame.Animation.MyAnimationView;
import com.BeatGame.Component.BeatButton;
import com.BeatGame.Component.ButtonManager;
import com.BeatGame.Component.Position;
import com.BeatGame.Management.R;
import com.BeatGame.UI.Scene;

public class GameManager extends Activity {

	final int SCORE_PER_CLICK = 10;
	
	// Speed of the game
	private int speed;
	private String level; // SetupManager.level should be an enum
	private long score = 0;
	private long GameFactor = 100;
	private int levelRank = 0;

	private Button pauseButton;
	private ButtonManager buttonManager;
	private Scene sceneManager;
	private RelativeLayout container;
	private TextView scoreLabel;
    
	public static GameManager gameManager;
	private CircleListener threadsListener;
	public static boolean isOnPause;
	public static boolean exitGame;

	private int screenWidth, screenHeight;
	private int indexButtonToBeClicked = 0;
	
	private ImageView life1, life2, life3;
	private int life = 3;
	
	// Thread for playing music background
	private BackgroundSound mBackgroundSound = new BackgroundSound();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_manager);
		Intent intent = getIntent();
		level = intent.getStringExtra("level");
		container = (RelativeLayout) findViewById(R.id.container);

		// Score and life
		scoreLabel = (TextView) findViewById(R.id.score);
		scoreLabel.setText("0");
		life1 = (ImageView) findViewById(R.id.life_one);
		life2 = (ImageView) findViewById(R.id.life_two);
		life3 = (ImageView) findViewById(R.id.life_three);
		
       
		buttonManager = new ButtonManager(this);
		sceneManager = new Scene(this, 800, 800);

		exitGame = false;
		isOnPause = false;
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenHeight = metrics.heightPixels;
		screenWidth = metrics.widthPixels;

		gameManager = this;

		restartGame();
		
		pauseButton = (Button) findViewById(R.id.startButton);
		pauseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(GameManager.this,
						PauseActivity.class);
				// myIntent.putExtra("key", value); //Optional parameters
				startActivity(myIntent);
			}
		});
		
		// play music
		mBackgroundSound.execute();
	
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
		
		if (!exitGame) {
			isOnPause = false;
			
			synchronized (threadsListener) {
				if (threadsListener != null)
					threadsListener.notify();
			}

		} else {
			Intent returnIntent = new Intent();
			returnIntent.putExtra("score", score);
			setResult(RESULT_OK, returnIntent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent(GameManager.this, PauseActivity.class);
		startActivity(myIntent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (threadsListener != null)
			threadsListener.cancel(true);
		
		if(mBackgroundSound!=null){
			if(mBackgroundSound.player!=null){
				mBackgroundSound.player.stop();
				mBackgroundSound.player.release();
			}
			mBackgroundSound.cancel(true);
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

		// reset view
		sceneManager.clearBeatButtonType(container);

		// reset
		buttonManager.clearButtons();
		sceneManager.clearButtons();

		// reset listener threads
		if (threadsListener != null)
			threadsListener.cancel(true);

		// reset index button to be clicked
		indexButtonToBeClicked = 0;

		// remove animation view
		for (int i = 0; i < container.getChildCount(); i++) {
			if (container.getChildAt(i) instanceof MyAnimationView)
				container.removeViewAt(i);
		}

		// create list of button in ButtonManger
		if (level.equals("easy")) {
			levelRank = 1;
		} else if (level.equals("normal")) {
			levelRank = 3;
		} else if (level.equals("hard")) {
			levelRank = 5;
		}

		// Adding buttons
		final ArrayList<BeatButton> listForVerifyClick = new ArrayList<BeatButton>();
		while (sceneManager.buttonsMap().size() < levelRank) {
			int x = 50 + (int) (Math.random() * (screenWidth - 200));
			int y = 50 + (int) (Math.random() * (screenHeight - 250));
			Log.e("Width and Height: ", x+"  ana "+y);
			BeatButton button = buttonManager.createButton(
					new Position(x,
							y), 50,
					100, 1000);
			if (sceneManager.setButton(button)) {
				buttonManager.setButton(button);
				listForVerifyClick.add(buttonManager.buttons().get(
						buttonManager.buttons().size() - 1));
			}

		}

		// Adding animation
		final MyAnimationView animView = new MyAnimationView(this, sceneManager);
		container.addView(animView);
		animView.restartAnimation();

		HashMap<BeatButton, Position> buttons = sceneManager.buttonsMap();
		int i = 0;
		for (BeatButton key : buttons.keySet()) {

			final int t = i;
			sceneManager.drawButton(key, GameManager.this, container, key.id()
					+ "");

			key.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View button) {
					// TODO Auto-generated method stub
					if (((BeatButton) button).id() == listForVerifyClick.get(
							indexButtonToBeClicked).id()) {
						// Increase score
						score += SCORE_PER_CLICK;
						indexButtonToBeClicked++;
						
						// Update score view
						scoreLabel.setText(score+"");

						sceneManager.removeButton((BeatButton) button,
								GameManager.this, container);
						animView.hideView(t);

					} else {
						final Toast toast = Toast.makeText(GameManager.this,
								"Click the SMALLEST button first!",
								Toast.LENGTH_SHORT);
						toast.show();

						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								toast.cancel();
							}
						}, 500);
					}
				}
			});
			i++;
		}
		// Create and start thread to listen to the end of the animation
		threadsListener = new CircleListener();
		threadsListener.execute(new Object[] { null, 0, animView });

	}

	private class CircleListener extends AsyncTask<Object, Void, BeatButton> {
	
		@Override
		protected BeatButton doInBackground(Object... btns) {

			try {
				Thread.sleep(MyAnimationView.getDuration());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			synchronized (this) {
				Log.e("THREAD PAUSE =========> : ", "" + isOnPause);
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

			return (BeatButton) btns[0];
		}

		@Override
		protected void onPostExecute(BeatButton btn) {
				if(life == 3){
					life1.setImageResource(R.drawable.life_left);
					life --;
					restartGame();
				}else if(life == 2){
					life2.setImageResource(R.drawable.life_left);
					life --;
					restartGame();
				}else if(life == 1){	// Game over
					life3.setImageResource(R.drawable.life_left);
					life --;
					Intent returnIntent = new Intent();
					returnIntent.putExtra("score", score);
					returnIntent.putExtra("status", "gameover");
					setResult(RESULT_OK, returnIntent);
					finish();
				}
				
		}
	}
	
	public class BackgroundSound extends AsyncTask<Void, Void, Void> {
		MediaPlayer player;
	    
		@Override
	    protected Void doInBackground(Void... params) {
	        player = MediaPlayer.create(GameManager.this, R.raw.background); 
	        player.setLooping(true); // Set looping 
	        player.setVolume(100,100);
	        player.start();		    
	        return null;
	    }
	}
	
	private long calculateScoreFromClick(BeatButton b, long clickTime){
        // clickTime is the curentTimeInMs to know how fast was the click from the start
        // we calculate how fast and according to the percentage we give point
        long duration = b.circle ().duration();
        long delta =  (long)(currentTimeMillis() - b.circle().startTime());
        //long delta =  clickTime - b.circle().startTime()); // to uncomment when implemented

        // 10 - %reaction%
        return 10 - (delta*100 / duration);
    }
}
