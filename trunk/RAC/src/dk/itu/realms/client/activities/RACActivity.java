package dk.itu.realms.client.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import dk.itu.realms.client.DataCommService;
import dk.itu.realms.client.R;
import dk.itu.realms.client.activities.realm.RealmListAdapter;
import dk.itu.realms.client.funf.MainPipeline;
import dk.itu.realms.client.funf.persistence.PersistDataHelper;
import dk.itu.realms.client.funf.persistence.PersistedSensorData;

public class RACActivity extends Activity {

	private static final String TAG = "RAC_ACTIVITY";
	private final Uri SENSED_DATA_URI = Uri.parse("content://dk.itu.realms.client/sensor_data");

	private ViewSwitcher switcher;

	private ListView realmsList;

	private DataCommService dataComm;

	private SensedDataObserver observer = new SensedDataObserver();
	private final ServiceConnection conn = new ServiceConnection() {

		public void onServiceConnected(ComponentName component, IBinder service) {
			dataComm = ((DataCommService.LocalBinder)service).getService();

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
				}
			});
		}

		public void onServiceDisconnected(ComponentName component) {
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SharedPreferences settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);
		if(! settings.getBoolean("setupDone", false)) {
			startActivity(new Intent(this, UserSetupScreen.class));
			finish();
			return;
		}

		dk.itu.realms.client.utils.SensedDataObserver.registerChangeStrategy(new Runnable() {

			@Override
			public void run() {
				PersistedSensorData data = PersistDataHelper.getSensorData(RACActivity.this, "LOCATION");
				if(data != null) {
					JSONObject jsonData = new JSONObject();

					try {
						jsonData = new JSONObject(data.value);

						if(jsonData.has("LOCATION")) {
							final JSONObject locationData = jsonData.optJSONObject("LOCATION");

							RACActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									((TextView)RACActivity.this.findViewById(R.id.current_location)).setText(
											"" + locationData.optDouble("mLatitude") + ", " + locationData.optDouble("mLongitude") + ", " + locationData.optDouble("mAccuracy"));
								}
							});
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		dk.itu.realms.client.utils.SensedDataObserver.registerObserver(this);

		setContentView(R.layout.main);
		PersistDataHelper.clearDatabase(this);

		realmsList = (ListView)findViewById(R.id.realms_list);

		getContentResolver().registerContentObserver(SENSED_DATA_URI, true, observer);

		startService(new Intent(this, MainPipeline.class));

		switcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
		new Thread(new Runnable() {

			public void run() {
				RACActivity.this.bindService(new Intent(RACActivity.this, DataCommService.class), conn, Context.BIND_AUTO_CREATE);
			}
		}).start();
		setTitle(R.string.choose_realm_title);

		((Button)findViewById(R.id.refresh_button)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PersistedSensorData data = PersistDataHelper.getSensorData(RACActivity.this, "LOCATION");

				if(data != null) {
					JSONObject jsonData = new JSONObject();

					try {
						jsonData = new JSONObject(data.value);

						if(jsonData.has("LOCATION")) {
							JSONObject locationData = jsonData.optJSONObject("LOCATION");

							if(locationData.has("mAccuracy") && locationData.optDouble("mAccuracy") <= 15) {
								RACActivity.this.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										switcher.showPrevious();
									}
								});

								RealmListAdapter adapter = new RealmListAdapter(
										RACActivity.this, 
										dataComm.getServerConn().getRealms(
												locationData.optDouble("mLatitude"), locationData.optDouble("mLongitude"), settings.getString("userID", "anonymous"))
								);
								realmsList.setAdapter(adapter);

								RACActivity.this.runOnUiThread(new Runnable() {

									@Override
									public void run() {
										switcher.showNext();
									}
								});
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});

		((Button)findViewById(R.id.close_button)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RACActivity.this.finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		PersistDataHelper.clearDatabase(this);
		stopService(new Intent(this, MainPipeline.class));
		
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public void finish() {
		super.finish();

		PersistDataHelper.clearDatabase(this);
		stopService(new Intent(this, MainPipeline.class));
		
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private class SensedDataObserver extends ContentObserver {

		private static final String TAG = "SENSED_DATA_OBSERVER";

		public SensedDataObserver() {
			super(new Handler());
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			PersistedSensorData data = PersistDataHelper.getSensorData(RACActivity.this, "LOCATION");
			final SharedPreferences settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);
			
			if(data != null) {
				JSONObject jsonData = new JSONObject();

				try {
					jsonData = new JSONObject(data.value);

					if(jsonData.has("LOCATION")) {
						JSONObject locationData = jsonData.optJSONObject("LOCATION");

						if(locationData.has("mAccuracy") && locationData.optLong("mAccuracy") <= 5000) {
							RealmListAdapter adapter = new RealmListAdapter(
									RACActivity.this, 
									dataComm.getServerConn().getRealms(
											locationData.optDouble("mLatitude"), locationData.optDouble("mLongitude"), settings.getString("userID", "anonymous"))
							);
							realmsList.setAdapter(adapter);

							RACActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									switcher.showNext();
								}
							});

							RACActivity.this.getContentResolver().unregisterContentObserver(observer);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	}
}