package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.BeatGame.Management.R;

/**
 * Created by Franck on 07/02/14.
 */
public class SetupManager extends Activity {
	
	public enum level{easy, normal, hard};
	
	private RadioGroup radioLevel;
	private RadioButton radio;

	public void onCreate(Bundle savedInstanceState) {

        //SceneTest sc = new SceneTest(this); // Some Debug, let it comment

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
			        Intent intent = new Intent(SetupManager.this,GameManager.class);
			        if(radio.getText().equals("Easy")){
			        	intent.putExtra("level", level.easy.toString());
			        }
			        else if(radio.getText().equals("Normal")){
			        	intent.putExtra("level", level.normal.toString());
			        }else if(radio.getText().equals("Hard")){
			        	intent.putExtra("level", level.hard.toString());
			        }
			        startActivityForResult(intent,1);
			}
		});
		
		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				long score = data.getLongExtra("score",0);
				Log.e("SCORE ===>>>>>",""+score);
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

}