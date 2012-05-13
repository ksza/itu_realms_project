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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import dk.itu.realms.client.model.Mark;
import dk.itu.realms.client.model.Realm;

public class RACServerConn implements IServerComm {

	private static final String TAG = "RAC_SERVER_CONN";
	private static final String URL = "http://130.226.142.177:8080/realms_configurator/services/realms";

	@Override
	public List<Realm> getRealms(final Double latitude, final Double longitude) {
		final List<Realm> realms = new ArrayList<Realm>();

		try {
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpGet request = new HttpGet(URL + "?lat=" + latitude + "&lon=" + longitude);
			request.addHeader("Accept", "application/json");

			final HttpResponse response = httpclient.execute(request);
			final HttpEntity entity = response.getEntity();
			final InputStream instream = entity.getContent();
			String content = read(instream);
			final JSONArray entries = new JSONObject(content).optJSONArray("realms");

			for(int i = 0; i < entries.length(); i++){
				final JSONObject o = entries.optJSONObject(i);

				realms.add(new Realm(
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
	public Mark updateStatus(Long realmId, Double lat, Double lon, String userID) {
		return null;
	}

	@Override
	public void rateInfo(Long realmId, Long markID, String rating, String userID) {
	}

	@Override
	public void markOption(Long realmID, Long markID, Long optionID, String userID) {
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
