package dk.itu.realms.client.funf.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class PersistedSensorDataProvider extends ContentProvider {
	private static final String TAG = "PERSISTED_SENSOR_DATA_PROVIDER";

	private SQLiteOpenHelper mOpenHelper;

	private static final int SENSOR_DATA	 	= 1;
	private static final int SENSOR_DATA_ID 	= 2;

	private static final UriMatcher sURLMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		sURLMatcher.addURI("dk.itu.realms.client", "sensor_data", SENSOR_DATA);
		sURLMatcher.addURI("dk.itu.realms.client", "sensor_data/#", SENSOR_DATA_ID);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "sensor_data.db";
		private static final int DATABASE_VERSION = 1;

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE sensor_data (" + "_id INTEGER PRIMARY KEY," + "type TEXT, " + "value TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int currentVersion) {
			Log.d(TAG,
					"Upgrading sensor_data database from version " +
					oldVersion + " to " + currentVersion +
			", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS alarms");
			onCreate(db);
		}
	}

	public PersistedSensorDataProvider() { }

	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri url, String[] projectionIn, String selection, String[] selectionArgs, String sort) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		// Generate the body of the query
		int match = sURLMatcher.match(url);
		switch (match) {
		case SENSOR_DATA:
			qb.setTables("sensor_data");
			break;
		case SENSOR_DATA_ID:
			qb.setTables("sensor_data");
			qb.appendWhere("_id=");
			qb.appendWhere(url.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor ret = qb.query(db, projectionIn, selection, selectionArgs, null, null, sort);

		if (ret == null) {
			Log.d(TAG, "Alarms.query: failed");
		} else {
			ret.setNotificationUri(getContext().getContentResolver(), url);
		}

		return ret;
	}

	@Override
	public String getType(Uri url) {
		int match = sURLMatcher.match(url);
		switch (match) {
		case SENSOR_DATA:
			return "vnd.android.cursor.dir/sensor_data";
		case SENSOR_DATA_ID:
			return "vnd.android.cursor.item/sensor_data";
		default:
			throw new IllegalArgumentException("Unknown URL");
		}
	}

	@Override
	public int update(Uri url, ContentValues values, String where, String[] whereArgs) {
		int count;
		long rowId = 0;
		int match = sURLMatcher.match(url);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		switch (match) {
		case SENSOR_DATA_ID: {
			String segment = url.getPathSegments().get(1);
			rowId = Long.parseLong(segment);
			count = db.update("sensor_data", values, "_id=" + rowId, null);
			break;
		}
		default: {
			throw new UnsupportedOperationException("Cannot update URL: " + url);
		}
		}
		Log.d(TAG, "*** notifyChange() rowId: " + rowId + " url " + url);
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	@Override
	public Uri insert(Uri url, ContentValues initialValues) {
		if (sURLMatcher.match(url) != SENSOR_DATA) {
			throw new IllegalArgumentException("Cannot insert into URL: " + url);
		}

		ContentValues values = new ContentValues(initialValues);

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert("sensor_data", PersistedSensorData.Columns.VALUE, values);
		if (rowId < 0) {
			throw new SQLException("Failed to insert row into " + url);
		}
		Log.d(TAG, "Added sensor_data rowId = " + rowId);

		Uri newUrl = ContentUris.withAppendedId(PersistedSensorData.Columns.CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(newUrl, null);
		return newUrl;
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		long rowId = 0;
		switch (sURLMatcher.match(url)) {
		case SENSOR_DATA:
			count = db.delete("sensor_data", where, whereArgs);
			break;
		case SENSOR_DATA_ID:
			String segment = url.getPathSegments().get(1);
			rowId = Long.parseLong(segment);
			if (TextUtils.isEmpty(where)) {
				where = "_id=" + segment;
			} else {
				where = "_id=" + segment + " AND (" + where + ")";
			}
			count = db.delete("sensor_data", where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Cannot delete from URL: " + url);
		}

		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}
}
