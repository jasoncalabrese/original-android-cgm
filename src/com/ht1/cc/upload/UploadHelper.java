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

import java.util.Arrays;

public class UploadHelper extends AsyncTask<String, Integer, Long> {

	Context context;

    public UploadHelper(Context context) {
        this.context = context;
    }

	protected Long doInBackground(String... data) {

		System.out.println("DEXCOM DATA: " + Arrays.toString(data));

		try {
			String timestamp = data[0];
			String bg =  data[1];
			String direction = data[2];

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpost = new HttpPost("http://192.168.1.105:9000/api/entries");

			JSONObject json = new JSONObject();
			//TODO: parse timestamp and include in json, currently server defaults to current time
			//json.put("timestamp", parseTimestamp(timestamp));
			json.put("bg", Integer.parseInt(bg));
			json.put("direction", direction);
			System.out.println("DEXCOM JSON: " + json.toString());

			StringEntity se = new StringEntity(json.toString());
			httpost.setEntity(se);
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-type", "application/json");

			ResponseHandler responseHandler = new BasicResponseHandler();
			httpclient.execute(httpost, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1L;
	}

	protected void onPostExecute(Long result) {
		super.onPostExecute(result);
		Log.i("Uploader", result + " Status: FINISHED");

	}

}
