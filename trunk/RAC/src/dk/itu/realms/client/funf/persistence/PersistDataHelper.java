package dk.itu.realms.client.funf.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class PersistDataHelper {

	public static synchronized void persistSensorData(Context context, PersistedSensorData data) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(
				PersistedSensorData.Columns.CONTENT_URI, 
				PersistedSensorData.Columns.SENSOR_DATA_QUERY_COLUMNS, 
				PersistedSensorData.Columns.DATA_TYPE + " = " + "'" + data.type + "'", 
				null, 
				PersistedSensorData.Columns.DEFAULT_SORT_ORDER
		);
		
		if(cursor != null) {
			if(cursor.moveToFirst()) {
				PersistedSensorData existingSensorData = new PersistedSensorData(cursor);
				existingSensorData.value = data.value;
				
				update(context, existingSensorData);
			} else {
				insertSensorData(context, data);
			}
			
			cursor.close();
		} else {
			insertSensorData(context, data);
		}
	}
	
	public static synchronized PersistedSensorData getSensorData(Context context, String type) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(
				PersistedSensorData.Columns.CONTENT_URI, 
				PersistedSensorData.Columns.SENSOR_DATA_QUERY_COLUMNS, 
				PersistedSensorData.Columns.DATA_TYPE + " = " + "'" + type + "'", 
				null, 
				PersistedSensorData.Columns.DEFAULT_SORT_ORDER
		);
		
		if(cursor != null) {
			if(cursor.moveToFirst()) {
				PersistedSensorData data = new PersistedSensorData(cursor);
				cursor.close();
				
				return data;
			} else {
				cursor.close();
				
				return null;
			}
		}
		
		return null;
	}

	/**
	 * Return an PersistedSensorData object representing the sensor_data id in the database.
	 * Returns null if no sensor_data exists.
	 */
	public static synchronized PersistedSensorData getSensorData(ContentResolver contentResolver, int sensorDataID) {
		Cursor cursor = contentResolver.query(ContentUris.withAppendedId(PersistedSensorData.Columns.CONTENT_URI, sensorDataID),
				PersistedSensorData.Columns.SENSOR_DATA_QUERY_COLUMNS,
				null, null, null);
		PersistedSensorData sensorData = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				sensorData = new PersistedSensorData(cursor);
			}
			cursor.close();
		}
		return sensorData;
	}
	
	private static int insertSensorData(Context context, PersistedSensorData data) {
		ContentValues values = createContentValues(data);
		Uri uri = context.getContentResolver().insert(PersistedSensorData.Columns.CONTENT_URI, values);
		return (int) ContentUris.parseId(uri);
	}

	private static void deleteSensorData(Context context, int sensorDataID) {
		ContentResolver contentResolver = context.getContentResolver();

		Uri uri = ContentUris.withAppendedId(PersistedSensorData.Columns.CONTENT_URI, sensorDataID);
		contentResolver.delete(uri, "", null);
	}

	/**
	 * Queries all sensor data
	 * 
	 * @return cursor over all sensor data
	 */
	public static synchronized Cursor getSensorDataCursor(ContentResolver contentResolver) {
		return contentResolver.query(PersistedSensorData.Columns.CONTENT_URI, PersistedSensorData.Columns.SENSOR_DATA_QUERY_COLUMNS,
				null, null, PersistedSensorData.Columns.DEFAULT_SORT_ORDER);
	}

	private static void update(Context context, PersistedSensorData data) {
		ContentValues values = createContentValues(data);
		ContentResolver resolver = context.getContentResolver();
		resolver.update(
				ContentUris.withAppendedId(PersistedSensorData.Columns.CONTENT_URI, data.id),
				values, null, null);
	}

	private static ContentValues createContentValues(PersistedSensorData data) {
		ContentValues values = new ContentValues(2);

		values.put(PersistedSensorData.Columns.DATA_TYPE, data.type);
		values.put(PersistedSensorData.Columns.VALUE, data.value);

		return values;
	}
}
