package com.ht1.cc.upload;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;

public class UploadHelper extends AsyncTask<String, Integer, Long> {

	Context context;

	private final String TAG = UploadHelper.class.getSimpleName();

    public UploadHelper(Context context) {
        this.context = context;
    }

	protected Long doInBackground(String... data) {

		try {
			String timestamp = data[0];
			String bg =  data[1];
			String direction = data[2];

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost localPost = new HttpPost("http://192.168.1.105:9000/api/entries");
			HttpPost remotePost = new HttpPost("http://[top-secrect-url]/api/entries");

			JSONObject json = new JSONObject();
			//TODO: parse timestamp and include in json, currently server defaults to current time
			//json.put("timestamp", parseTimestamp(timestamp));
			json.put("bg", Integer.parseInt(bg));
			json.put("direction", direction);

			String jsonString = json.toString();

			Log.i(TAG, "DEXCOM JSON: " + jsonString);

			doPost(httpclient, localPost, jsonString);
			doPost(httpclient, remotePost, jsonString);
		} catch (Exception e) {
			Log.e(TAG, "Unable to post data", e);
		}

		return 1L;
	}

	protected void onPostExecute(Long result) {
		super.onPostExecute(result);
		Log.i(TAG, result + " Status: FINISHED");

	}

	private void doPost(DefaultHttpClient httpclient, HttpPost post, String json) {
		try {
			StringEntity se = new StringEntity(json);
			post.setEntity(se);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");

			ResponseHandler responseHandler = new BasicResponseHandler();
			httpclient.execute(post, responseHandler);
		} catch (Exception e) {
			Log.w(TAG, "Unable to post data to " + post.getURI().toString(), e);
		}

	}

}
