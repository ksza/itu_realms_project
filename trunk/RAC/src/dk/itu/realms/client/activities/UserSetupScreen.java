package dk.itu.realms.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import dk.itu.realms.client.R;

public class UserSetupScreen extends Activity {
	public static final String PREFS_NAME = "monarcaSettings";

	private static final String DEFAULT_PASSWORD = "q";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.usersetupscreen_layout);

		setupButtonListeners();
	}

	private void setupButtonListeners() {
		findViewById(R.id.save_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText userID = (EditText) findViewById(R.id.ss_userid);
				EditText pass = (EditText) findViewById(R.id.ss_pass);
				EditText realm = (EditText) findViewById(R.id.ss_realm);
				
				TextView userIDError = (TextView) findViewById(R.id.ss_useriderror);
				
				// Reset error messages
				userIDError.setVisibility(View.INVISIBLE);
				
				boolean error = false;
				
				// Is there a user id?
				if(userID.getText().toString().equals("")) {
					userIDError.setVisibility(View.VISIBLE);
					error = true;
				}
				
				if(!error) {
					
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					Editor editor = settings.edit();

					// Enter userID
					editor.putString("userID", userID.getText().toString());
					
					editor.putString("pass", pass.getText().toString());
					
					editor.putString("realm", realm.getText().toString());
					
					// Put password
					editor.putString("password", "");
					
					

					// Tell the app that we've done this
					editor.putBoolean("setupDone", true);

					// Commit the changes
					editor.commit();

					// Change back to home screen
					startActivity(new Intent(UserSetupScreen.this, RACActivity.class));
					finish();
				}
			}
		});
	}
}
