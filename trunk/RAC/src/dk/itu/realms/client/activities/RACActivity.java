package dk.itu.realms.client.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ListView;
import android.widget.ViewSwitcher;
import dk.itu.realms.client.DataCommService;
import dk.itu.realms.client.R;
import dk.itu.realms.client.activities.realm.RealmListAdapter;
import dk.itu.realms.client.funf.MainPipeline;
import dk.itu.realms.client.funf.persistence.PersistDataHelper;
import dk.itu.realms.client.mock.ServerConnMock;
import dk.itu.realms.client.utils.SensedDataObserver;

public class RACActivity extends Activity {

	private static final String TAG = "RAC_ACTIVITY";

	private ViewSwitcher switcher;

	private ListView realmsList;

	private DataCommService dataComm;
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
		setContentView(R.layout.main);
		PersistDataHelper.clearDatabase(this);

		realmsList = (ListView)findViewById(R.id.realms_list);
		
		SensedDataObserver.registerChangeStrategy(new Runnable() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						RealmListAdapter adapter = new RealmListAdapter(RACActivity.this, ServerConnMock.getRealms());
						realmsList.setAdapter(adapter);
						
						switcher.showNext();
					}
				});
			}
		});
		SensedDataObserver.registerObserver(this);
		startService(new Intent(this, MainPipeline.class));

		switcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
		new Thread(new Runnable() {

			public void run() {
				RACActivity.this.bindService(new Intent(RACActivity.this, DataCommService.class), conn, Context.BIND_AUTO_CREATE);
			}
		}).start();
		setTitle(R.string.choose_realm_title);

		SharedPreferences settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);

		if(! settings.getBoolean("setupDone", false)) {
			startActivity(new Intent(this, UserSetupScreen.class));
			finish();
			return;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		PersistDataHelper.clearDatabase(this);
		stopService(new Intent(this, MainPipeline.class));
	}
}