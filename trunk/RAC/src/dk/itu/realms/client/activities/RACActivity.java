package dk.itu.realms.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import dk.itu.realms.client.R;
import dk.itu.realms.client.funf.MainPipeline;

public class RACActivity extends Activity {
	
	private static final String TAG = "RAC_ACTIVITY";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startService(new Intent(this, MainPipeline.class));
        
        
    }
}