package com.BeatGame.Manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.BeatGame.Management.R;

/**
 * Created by Franck on 07/02/14.
 */
public class SetupManager extends Activity {
	
	private RadioGroup radioLevel;
	private RadioButton radio;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_manager);
		radioLevel= (RadioGroup) findViewById(R.id.radioLevel);
		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int selectedId = radioLevel.getCheckedRadioButtonId();
				 
				// find the radiobutton by returned id
			        radio = (RadioButton) findViewById(selectedId);
	 
//				Toast.makeText(SetupManager.this,
//					radio.getText(), Toast.LENGTH_SHORT).show();
			        
			
			}
		});
		
		
		Button exit= (Button) findViewById(R.id.exit);
		
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
//		Button butNormal = (Button) findViewById(R.id.radioNormal);
//		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
//		layout.addView(butNormal);
//		Button butEasy = (Button) findViewById(R.id.radioEasy);
//		layout.addView(butEasy);
//		Button butHard = (Button) findViewById(R.id.radioHard);
//		layout.addView(butHard);
	}

}