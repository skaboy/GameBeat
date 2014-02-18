package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.BeatGame.Management.R;
import com.BeatGame.UI.SceneTest;

/**
 * Created by Franck on 07/02/14.
 */
public class PauseActivity extends Activity {
	
	
	public void onCreate(Bundle savedInstanceState) {

        //SceneTest sc = new SceneTest(this); // Some Debug, let it comment

		super.onCreate(savedInstanceState);
		setContentView(R.layout.pause_activity);
		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		Button exit= (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Exit the application
				GameManager.exitGame = true;
				finish();
				
			}
		});
	}

}