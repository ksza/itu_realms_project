package dk.itu.realms.client.funf.collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public abstract class AbstractProbeDataService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {

		if(intent != null && intent.hasExtra("probeData")) {
			JSONObject jsonData = new JSONObject();
			try {
				jsonData = new JSONObject(intent.getExtras().getString("probeData"));
			} catch (JSONException e) {
				e.printStackTrace();
			}

			handleData(jsonData);
		}
		
		return START_STICKY;
	}

	protected abstract void handleData(final JSONObject dataJson);

	protected double computeIntArrayAvg(final JSONArray array) {
		double arrayAvg = 0;
		for(int i = 0; i < array.length(); i++) {
			arrayAvg += array.optInt(i);
		}
		arrayAvg = arrayAvg / array.length();

		return arrayAvg;
	}

	protected double computeDoubleArrayAvg(final JSONArray array) {
		double arrayAvg = 0;
		for(int i = 0; i < array.length(); i++) {
			arrayAvg += array.optDouble(i);
		}
		arrayAvg = arrayAvg / array.length();

		return arrayAvg;
	}
}
