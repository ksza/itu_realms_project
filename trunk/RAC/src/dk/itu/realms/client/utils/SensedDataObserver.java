package dk.itu.realms.client.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class SensedDataObserver extends ContentObserver {

	private static final String TAG = "SENSED_DATA_OBSERVER";

	private static final Uri SENSED_DATA_URI = Uri.parse("content://dk.itu.realms.client/sensor_data");

	private static final ContentObserver observer = new SensedDataObserver();

	private static Context context = null;

	private static Runnable chageStrategy = null;

	public SensedDataObserver() {
		super(new Handler());
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		if(context != null && chageStrategy != null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					chageStrategy.run();
					chageStrategy = null;
					
					unregisterObserver(context.getContentResolver());
				}
			}).start();
		}
	}

	public static void registerObserver(final Context c) {
		/* in case it was registered already */
		unregisterObserver(c.getContentResolver());

		context = c;

		context.getContentResolver().registerContentObserver(SENSED_DATA_URI, true, observer);
	}

	private static void unregisterObserver(final ContentResolver cr) {
		context = null;
		cr.unregisterContentObserver(observer);
	}

	public static synchronized void registerChangeStrategy(final Runnable strategy) {
		chageStrategy = strategy;
	}

}
