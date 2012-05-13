package dk.itu.realms.client.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import dk.itu.realms.client.DataCommService;
import dk.itu.realms.client.R;
import dk.itu.realms.client.funf.persistence.PersistDataHelper;
import dk.itu.realms.client.funf.persistence.PersistedSensorData;
import dk.itu.realms.client.model.Mark;
import dk.itu.realms.client.model.Option;

/**
 * An activity configured to interact with one specific realm!
 * 
 * @author karcsi
 */
public class RealmActivity extends Activity {

	private ViewSwitcher switcher;
	private Mark currentMark;

	private SharedPreferences settings;

	private DataCommService dataComm;
	private final ServiceConnection conn = new ServiceConnection() {

		public void onServiceConnected(ComponentName component, IBinder service) {
			dataComm = ((DataCommService.LocalBinder)service).getService();
		}

		public void onServiceDisconnected(ComponentName component) {
		}
	};

	private int checkedItem = -1;
	private String chosenAnswer = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.realm_activity_layout);

		new Thread(new Runnable() {

			public void run() {
				bindService(new Intent(RealmActivity.this, DataCommService.class), conn, Context.BIND_AUTO_CREATE);
			}
		}).start();

		currentMark = null;

		settings = getSharedPreferences(UserSetupScreen.PREFS_NAME, 0);
		setTitle(settings.getString("realm_name", ""));

		switcher = (ViewSwitcher)findViewById(R.id.viewSwitcher);
		switcher.showNext();

		((TextView)findViewById(R.id.text_blob)).setMovementMethod(LinkMovementMethod.getInstance());
		((TextView)findViewById(R.id.text_blob)).setText(R.string.look_for_tag);

		((Button)findViewById(R.id.option_button)).setVisibility(View.INVISIBLE);
		((RatingBar)findViewById(R.id.rating_bar)).setVisibility(View.INVISIBLE);

		final Button updateButton = (Button)findViewById(R.id.update_button);
		updateButton.setText(R.string.update_button);
		updateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {

				switcher.showNext();
				v.setEnabled(false);

				new Thread(new Runnable() {

					@Override
					public void run() {
						if(currentMark != null) {

							if(currentMark.isQuestion()) {

								long optionID = -1;
								for(Option o: currentMark.getOptions()) {
									if(o.getDescription().equals(chosenAnswer)) {
										optionID = o.getId();
										break;
									}
								}

								dataComm.getServerConn().markOption(settings.getLong("realm_id", 0), currentMark.getId(), optionID, settings.getString("userID", "anonymous"));

							} else {

								dataComm.getServerConn().rateInfo(settings.getLong("realm_id", 0), currentMark.getId(), ((RatingBar)findViewById(R.id.rating_bar)).getRating() + "", settings.getString("userID", "anonymous"));

							}

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									((TextView)findViewById(R.id.text_blob)).setText(R.string.look_for_tag);
									((Button)findViewById(R.id.option_button)).setVisibility(View.INVISIBLE);
									((RatingBar)findViewById(R.id.rating_bar)).setVisibility(View.INVISIBLE);
									
									((Button)v).setText(R.string.update_button);
									v.setEnabled(true);
									
									String toastText = "";
									
									if(currentMark.isQuestion()) {
										Option selectedOption = null;
										for(Option o: currentMark.getOptions()) {
											if(o.getDescription().equals(chosenAnswer)) {
												selectedOption = o;
												break;
											}
										}
										
										if(selectedOption != null && selectedOption.isAnswer()) {
											toastText = "Correct Ansewer!";
										} else {
											toastText = "Wrong Answer!";
										}
									} else {
										toastText = "You've rated this information with " + ((RatingBar)findViewById(R.id.rating_bar)).getRating() + "!";
									}
									Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
									
									currentMark = null;
									chosenAnswer = "";
									checkedItem = -1;
								}
							});
							
						} else {
							PersistedSensorData data = PersistDataHelper.getSensorData(RealmActivity.this, "LOCATION");

							try {
								JSONObject jsonData = new JSONObject(data.value);

								if(jsonData.has("LOCATION")) {
									final JSONObject locationData = jsonData.optJSONObject("LOCATION");
									currentMark = dataComm.getServerConn().updateStatus(settings.getLong("realm_id", 0), locationData.optDouble("mLatitude"), locationData.optDouble("mLongitude"), settings.getString("userID", "anonymous"));

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											if(currentMark != null) {

												((TextView)findViewById(R.id.text_blob)).setText(Html.fromHtml(currentMark.getTextBlob()));

												if(currentMark.isQuestion()) {
													findViewById(R.id.option_button).setVisibility(View.VISIBLE);

													checkedItem = -1;
													chosenAnswer = "";

													AlertDialog.Builder builder = new AlertDialog.Builder(RealmActivity.this);
													builder.setTitle("Select an answer");
													builder.setSingleChoiceItems(currentMark.getOptionsNameArray(), checkedItem, new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog, int item) {
															v.setEnabled(true);	
															
															chosenAnswer = currentMark.getOptionsNameArray()[item];
															dialog.dismiss();
														}
													});
													final AlertDialog alert = builder.create();


													((Button)findViewById(R.id.option_button)).setOnClickListener(new View.OnClickListener() {

														@Override
														public void onClick(View v) {
															alert.show();
														}
													});
												} else {
													findViewById(R.id.rating_bar).setVisibility(View.VISIBLE);
													((RatingBar)findViewById(R.id.rating_bar)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
														
														@Override
														public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
															if(fromUser) {
																v.setEnabled(true);
															}
														}
													});
												};

												((Button)findViewById(R.id.update_button)).setText(R.string.submit_button);
											}
										}
									});
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								switcher.showNext();
							}
						});
					}
				}).start();
			}
		});
	}

}
