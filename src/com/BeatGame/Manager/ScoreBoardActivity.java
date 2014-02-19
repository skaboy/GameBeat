package com.BeatGame.Manager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.BeatGame.Management.R;

public class ScoreBoardActivity extends Activity {

	ListView listUser;
	ProgressBar progressBar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_board_activity);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		listUser = (ListView) findViewById(R.id.list_user);
		new ServerThread().execute(SetupManager.BASE_URL + "?allUser");

	}

	public class ServerThread extends
			AsyncTask<String, Void, ArrayList<String>> {
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressBar.setVisibility(View.GONE);
			listUser.setAdapter(new ArrayAdapter<String>(
					ScoreBoardActivity.this, R.layout.list_user_item, result));
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			Log.e("URL", params[0]);
			String result = SetupManager.readTextFromServer(params[0]);
			Log.e("Result", result);
			return parseJSON(result);
		}
	}

	public ArrayList<String> parseJSON(String jsonString) {

		ArrayList<String> listUser = new ArrayList<String>();

		try {
			JSONArray jArray = new JSONArray(jsonString);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObject = jArray.getJSONObject(i);
				listUser.add((i + 1) + ". " + jObject.getString("username")
						+ "     " + jObject.getString("score"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listUser;
	}
}