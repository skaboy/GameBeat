package com.BeatGame.Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.BeatGame.Management.R;

/**
 * Created by Franck on 07/02/14.
 */
public class AddUserActivity extends Activity {

	EditText username;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_activity);
		username = (EditText) findViewById(R.id.username);

		final Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String currentName = bundle.getString("username");
			username.setText(currentName);
		}

		Button add = (Button) findViewById(R.id.add_button);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!username.getText().toString().equals("")) {

					// Add user to server
					if (bundle != null) {
						if (bundle.getString("purpose").equals("add")) {
							new ServerThread()
									.execute(new String[] { SetupManager.BASE_URL
											+ "?username="
											+ username.getText().toString()
											+ "&score="
											+ bundle.getString("score")
											+ "&uuid="
											+ bundle.getString("uuid") });
						} else if (bundle.getString("purpose").equals("edit")) {
							new ServerThread()
									.execute(new String[] { SetupManager.BASE_URL
											+ "?username="
											+ username.getText().toString()
											+ "&score="
											+ bundle.getString("score")
											+ "&edit="
											+ bundle.getString("uuid") });
						}
					}

				}

			}
		});
	}

	public class ServerThread extends AsyncTask<String, Void, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("=======> From server: ","Server: "+result);
			Intent returnIntent = new Intent();
			returnIntent.putExtra("username", username.getText().toString());
			setResult(RESULT_OK, returnIntent);
			finish();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.e("URL",params[0]);
			return SetupManager.readTextFromServer(params[0]);
		}
	}

}