package com.BeatGame.Manager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.BeatGame.Database.DatabaseHandler;
import com.BeatGame.Database.User;
import com.BeatGame.Management.R;

public class SetupManager extends Activity {

	public enum level {
		easy, normal, hard
	};

	private final String SCORE_TEXT = "BEST SCORE";
	public static final String BASE_URL = "http://gamebeat.net46.net/api/function.php";
	private RadioGroup radioLevel;
	private RadioButton radio;
	private TextView score, username;
	private Button addUser;
	private String usernameString;
	private long currentScore = 0;
	private User user;
	private DatabaseHandler db;
	private Button scoreBoard;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setup_manager);

		radioLevel = (RadioGroup) findViewById(R.id.radioLevel);
		Button start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int selectedId = radioLevel.getCheckedRadioButtonId();
				radio = (RadioButton) findViewById(selectedId);
				Intent intent = new Intent(SetupManager.this, GameManager.class);
				if (radio.getText().equals("EASY")) {
					intent.putExtra("level", level.easy.toString().toLowerCase());
				} else if (radio.getText().equals("NORMAL")) {
					intent.putExtra("level", level.normal.toString().toLowerCase());
				} else if (radio.getText().equals("HARD")) {
					intent.putExtra("level", level.hard.toString().toLowerCase());
				}
				startActivityForResult(intent, 1);
			}
		});

		score = (TextView) findViewById(R.id.score);
		username = (TextView) findViewById(R.id.username);

		// Database
		db = new DatabaseHandler(this);
		user = db.getUser();
		if (user == null) {
			username.setText("NO NAME");
		} else {
			username.setText(user.getName());
			score.setText(SCORE_TEXT + ": " + user.getScore());
			currentScore = user.getScore();
		}

		addUser = (Button) findViewById(R.id.add_user);
		if (user != null)
			addUser.setBackgroundResource(R.drawable.edit);

		addUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (user == null) {
					Intent intent = new Intent(SetupManager.this,
							AddUserActivity.class);
					intent.putExtra("score", currentScore + "");
					intent.putExtra("uuid", generateUIID());
					intent.putExtra("purpose", "add");
					startActivityForResult(intent, 2);
				} else {
					Intent intent = new Intent(SetupManager.this,
							AddUserActivity.class);
					intent.putExtra("username", user.getName());
					intent.putExtra("score", currentScore + "");
					intent.putExtra("uuid", user.getUUID());
					intent.putExtra("purpose", "edit");
					startActivityForResult(intent, 3);
				}
			}
		});

		scoreBoard = (Button) findViewById(R.id.score_board);
		scoreBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetupManager.this,
						ScoreBoardActivity.class);
				startActivity(intent);
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data == null)
			return;

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) { // Edit best score
				if (data.getLongExtra("score", 0) > currentScore) {
					currentScore = data.getLongExtra("score", 0);
					this.score.setText(SCORE_TEXT + ": " + currentScore);

					if (user != null) {
						// Update local data
						user.setScore(currentScore);
						db.updateUser(user);

						// Update server data
						new ServerThread()
								.execute(new String[] { SetupManager.BASE_URL
										+ "?username=" + user.getName()
										+ "&score=" + currentScore + "&edit="
										+ user.getUUID() });
					}

				}

				if (data.getStringExtra("status") != null) {
					if (data.getStringExtra("status").equals("gameover")) {
						// custom dialog
						final Dialog dialog = new Dialog(this);
						dialog.setContentView(R.layout.dialog_layout);
						dialog.setTitle("GAME OVER");

						// set the custom dialog components - text, image and
						// button
						TextView text = (TextView) dialog
								.findViewById(R.id.text);
						text.setText("Your Score: "
								+ data.getLongExtra("score", 0));
						ImageView image = (ImageView) dialog
								.findViewById(R.id.image);
						image.setImageResource(R.drawable.score_ball);

						Button dialogButton = (Button) dialog
								.findViewById(R.id.dialogButtonOK);
						// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});

						dialog.show();
					}
				}
			}

		} else if (requestCode == 2) { // Add new user with currentScore
			if (resultCode == RESULT_OK) {
				usernameString = data.getStringExtra("username");
				username.setText(usernameString);

				user = new User();
				user.setName(usernameString);
				user.setScore(currentScore);
				user.setUUID(generateUIID());
				db.addUser(user);
				addUser.setBackgroundResource(R.drawable.edit);

			}
		} else if (requestCode == 3) { // Edit existing username
			usernameString = data.getStringExtra("username");
			username.setText(usernameString);
			user.setName(usernameString);
			db.updateUser(user);
		}
	}

	public String generateUIID() {
		String android_id = Secure.getString(getApplicationContext()
				.getContentResolver(), Secure.ANDROID_ID);
		Log.i("System out", "android_id : " + android_id);

		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		Log.i("System out", "tmDevice : " + tmDevice);
		tmSerial = "" + tm.getSimSerialNumber();
		Log.i("System out", "tmSerial : " + tmSerial);
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String UUID = deviceUuid.toString();
		Log.i("System out", "UUID : " + UUID);
		return UUID;
	}

	public static String readTextFromServer(String urlStr) {
		try {

			URI _url;
			_url = new URI(urlStr.replaceAll(" ", "%20"));

			HttpGet httpget = new HttpGet(_url);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				String s = new String(
						EntityUtils.toString(response.getEntity()));
				return s.trim();
			} else {
				// Log.d("TESTING", "error receiving content");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public class ServerThread extends AsyncTask<String, Void, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.e("URL", params[0]);
			return SetupManager.readTextFromServer(params[0]);
		}
	}
}