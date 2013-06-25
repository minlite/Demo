package com.minlite.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private final static String url = "http://10.0.2.2/api.php";
	JSONParser jParser = new JSONParser();
	
	String type = null;
	String input = null;
	
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((Button) findViewById(R.id.btn_echo)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				type = "echo";
				input = ((EditText) findViewById(R.id.input)).getText().toString();
				new doAction().execute();
			}
		});
		
		((Button) findViewById(R.id.btn_len)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				type = "len";
				input = ((EditText) findViewById(R.id.input)).getText().toString();
				new doAction().execute();
			}
		});
	}

	class doAction extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setCancelable(false);
			pDialog.setIndeterminate(true);
			pDialog.setMessage("Loading...");
			pDialog.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Void... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("type", type));
			params.add(new BasicNameValuePair("input", input));
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);
			Log.d("Response", json.toString());
			String output = null;
			try {
				int code = json.getInt("code");
				if(code == 0) {
					output = json.getString("data");
				} else {
					Log.e("Error", json.getString("msg"));
					this.cancel(true);
				}
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			return output;
		}
		
		@Override
		protected void onPostExecute(String output) {
			((EditText) findViewById(R.id.output)).setText(output);
			super.onPostExecute(output);
			pDialog.dismiss();
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
