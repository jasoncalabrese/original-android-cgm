package com.ht1.cc.upload;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ht1.cc.cgm.EGVRecord;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadHelper extends AsyncTask<EGVRecord, Integer, Long> {

	Context context;

	private static final String TAG = "DexcomUploadHelper";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
	private static final int SOCKET_TIMEOUT = 60 * 1000;
	private static final int CONNECTION_TIMEOUT = 30 * 1000;


	public UploadHelper(Context context) {
        this.context = context;
    }

	protected Long doInBackground(EGVRecord... records) {

		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
			HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);

			DefaultHttpClient httpclient = new DefaultHttpClient(params);

			HttpPost localPost = new HttpPost("http://192.168.1.105:9000/api/entries");
			HttpPost remotePost = new HttpPost("http://[your server here]/api/entries");

			for (EGVRecord record : records) {
				Date date = DATE_FORMAT.parse(record.displayTime);
				JSONObject json = new JSONObject();
				json.put("timestamp", date.getTime());
				json.put("bg", Integer.parseInt(record.bGValue));
				json.put("direction", record.trend);

				String jsonString = json.toString();

				Log.i(TAG, "DEXCOM JSON: " + jsonString);

				doPost(httpclient, localPost, jsonString);
				doPost(httpclient, remotePost, jsonString);
			}

		} catch (Exception e) {
			Log.e(TAG, "Unable to post data", e);
		}

		return 1L;
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
