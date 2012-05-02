package dk.itu.realms.client.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ViewSwitcher;
import dk.itu.realms.client.DataCommService;
import dk.itu.realms.client.R;
import dk.itu.realms.client.funf.MainPipeline;

public class RACActivity extends Activity {

	private static final String TAG = "RAC_ACTIVITY";

	private ViewSwitcher switcher;

	private DataCommService dataComm;
	private final ServiceConnection conn = new ServiceConnection() {

		public void onServiceConnected(ComponentName component, IBinder service) {
			dataComm = ((DataCommService.LocalBinder)service).getService();
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					//switcher.showNext();
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

		startService(new Intent(this, MainPipeline.class));

		switcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
		new Thread(new Runnable() {

			public void run() {
				RACActivity.this.bindService(new Intent(RACActivity.this, DataCommService.class), conn, Context.BIND_AUTO_CREATE);
			}
		}).start();

		SharedPreferences settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);
		
		Resources res = getResources();
		String formatString = res.getString(R.string.realm_title);
		setTitle(String.format(formatString, settings.getString("realm", "")));
//		((TextView)findViewById(R.id.realm_title)).setText(String.format(formatString, "asd"));
		
		//		SharedPreferences settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);
		//		if(! settings.getBoolean("setupDone", false)) {
		//		startActivity(new Intent(this, UserSetupScreen.class));
		//		finish();
		//		return;
		//		}
	}
}