package dk.itu.realms.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import dk.itu.realms.client.model.Mark;
import dk.itu.realms.client.model.Option;
import dk.itu.realms.client.model.Realm;

public class RACServerConn implements IServerComm {

	private static final String TAG = "RAC_SERVER_CONN";
	private static final String URL = "http://130.226.142.177:8080/realms_configurator/services";
	
	private static final String REALMS_URL 	= URL + "/realms";
	private static final String UPDATE_URL 	= URL + "/update";
	private static final String MARK_URL 	= URL + "/update/mark/markoption";
	private static final String RATE_URL 	= URL + "/update/mark/rate";

	@Override
	public List<Realm> getRealms(final Double latitude, final Double longitude, final String userID) {
		final List<Realm> realms = new ArrayList<Realm>();

		try {
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpGet request = new HttpGet(REALMS_URL + "?lat=" + latitude + "&lon=" + longitude + "&userid=" + userID);
			request.addHeader("Accept", "application/json");

			final HttpResponse response = httpclient.execute(request);
			final HttpEntity entity = response.getEntity();
			final InputStream instream = entity.getContent();
			String content = read(instream);
			final JSONArray entries = new JSONObject(content).optJSONArray("realms");

			for(int i = 0; i < entries.length(); i++){
				final JSONObject o = entries.optJSONObject(i);

				realms.add(new Realm(
						o.optLong("id"),
						o.optString("name"), 
						o.optString("generalDescription"), 
						o.optString("locationDecription")));
			}
		} catch(Exception e) {
			Log.i(TAG, e.getStackTrace().toString());
		}

		return realms;
	}

	@Override
	public Mark updateStatus(Long realmID, Double lat, Double lon, String userID) {
		Mark mark = null;

		try {
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpGet request = new HttpGet(UPDATE_URL + "?lat=" + lat + "&lon=" + lon + "&realm=" + realmID + "&userid=" + userID);
			request.addHeader("Accept", "application/json");

			final HttpResponse response = httpclient.execute(request);
			final HttpEntity entity = response.getEntity();
			final InputStream instream = entity.getContent();
			String content = read(instream);

			final JSONObject entry = new JSONObject(content);
			if(entry.has("markTitle") && entry.optString("markTitle") != null) {

				if(entry.optString("type").equalsIgnoreCase("information")) {
					mark = new Mark(
							entry.optLong("id"), 
							entry.optString("markTitle"), 
							entry.optString("markDescription"), 
							false, 
							entry.optString("textBlob"), 
							null
					);
				} else if(entry.optString("type").equalsIgnoreCase("question")) {
					final List<Option> options = new ArrayList<Option>();

					if(entry.has("options")) {
						final JSONArray jsonOptions = entry.optJSONArray("options");

						for(int i = 0; i < jsonOptions.length(); i++) {
							final JSONObject o = jsonOptions.optJSONObject(i);

							options.add(new Option(o.optLong("id"), o.optString("optionName"), o.optBoolean("correctAnswer")));
						}
					}

					mark = new Mark(
							entry.optLong("id"), 
							entry.optString("markTitle"), 
							entry.optString("markDescription"), 
							true, 
							entry.optString("textBlob"), 
							options
					);
				}

			}

		} catch(Exception e) {
			Log.i(TAG, e.getStackTrace().toString());
		}

		return mark;
	}

	@Override
	public void rateInfo(Long realmId, Long markID, String rating, String userID) {
		try {
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpPost request = new HttpPost(RATE_URL + "?realmid=" + realmId + "&markid=" + markID + "&rating=" + (int)Float.parseFloat(rating) + "&userid=" + userID);
			request.addHeader("Accept", "application/json");

			final HttpResponse response = httpclient.execute(request);
			final HttpEntity entity = response.getEntity();
			final InputStream instream = entity.getContent();
			String content = read(instream);
		} catch(Exception e) {
			Log.i(TAG, e.getStackTrace().toString());
		}
	}

	@Override
	public void markOption(Long realmId, Long markID, Long optionID, String userID) {
		try {
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpPost request = new HttpPost(MARK_URL + "?realmid=" + realmId + "&markid=" + markID + "&optionid=" + optionID + "&userid=" + userID);
			request.addHeader("Accept", "application/json");

			final HttpResponse response = httpclient.execute(request);
			final HttpEntity entity = response.getEntity();
			final InputStream instream = entity.getContent();
			String content = read(instream);
		} catch(Exception e) {
			Log.i(TAG, e.getStackTrace().toString());
		}
	}

	private static String read(InputStream instream) {
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					instream));
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				sb.append(line);
			}

			r.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

}
