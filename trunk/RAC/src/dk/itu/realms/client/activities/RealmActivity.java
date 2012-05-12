package dk.itu.realms.client.activities;

import dk.itu.realms.client.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * An activity configured to interact with one specific realm!
 * 
 * @author karcsi
 */
public class RealmActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.realm_activity_layout);
		
		SharedPreferences settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);
		setTitle(settings.getString("realm_name", ""));
	}
}
