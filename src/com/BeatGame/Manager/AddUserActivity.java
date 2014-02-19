package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.BeatGame.Management.R;
import com.BeatGame.UI.SceneTest;

/**
 * Created by Franck on 07/02/14.
 */
public class AddUserActivity extends Activity {
	
	
	public void onCreate(Bundle savedInstanceState) {

        //SceneTest sc = new SceneTest(this); // Some Debug, let it comment

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_activity);
		final EditText username = (EditText) findViewById(R.id.username);
		
		
		Button add= (Button) findViewById(R.id.add_button);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Exit the application
				Intent returnIntent = new Intent();
				returnIntent.putExtra("username", username.getText().toString());
				setResult(RESULT_OK, returnIntent);
				finish();
				finish();
				
			}
		});
	}

}