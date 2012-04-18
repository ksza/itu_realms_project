package dk.itu.realms.client.funf.persistence;

import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public final class PersistedSensorData implements Parcelable {

	//////////////////////////////
	// Parcelable apis
	//////////////////////////////
	public static final Parcelable.Creator<PersistedSensorData> CREATOR
	= new Parcelable.Creator<PersistedSensorData>() {
		public PersistedSensorData createFromParcel(Parcel p) {
			return new PersistedSensorData(p);
		}

		public PersistedSensorData[] newArray(int size) {
			return new PersistedSensorData[size];
		}
	};

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel p, int flags) {
		p.writeInt(id);
		p.writeString(type);
		p.writeString(value);
	}
	//////////////////////////////
	// end Parcelable apis
	//////////////////////////////

	//////////////////////////////
	// Column definitions
	//////////////////////////////
	public static class Columns implements BaseColumns {
		/**
		 * The content:// style URL for this table
		 */
		public static final Uri CONTENT_URI =
			Uri.parse("content://dk.itu.realms.client/sensor_data");

		/**
		 * Sensor data type as string
		 */
		public static final String DATA_TYPE = "type";

		/**
		 * Sensor data value -> store in raw format! 
		 * [advised to use JSON]
		 */
		public static final String VALUE = "value";


		/**
		 * The default sort order for this table
		 */ 
		public static final String DEFAULT_SORT_ORDER = DATA_TYPE + " ASC";

		static final String[] SENSOR_DATA_QUERY_COLUMNS = {
			_ID, DATA_TYPE, VALUE };

		/**
		 * These save calls to cursor.getColumnIndexOrThrow()
		 * THEY MUST BE KEPT IN SYNC WITH ABOVE QUERY COLUMNS
		 */
		public static final int DATA_ID_INDEX = 0;
		public static final int DATA_TYPE_INDEX = 1;
		public static final int DATA_VAUE_INDEX = 2;
	}
	//////////////////////////////
	// End column definitions
	//////////////////////////////

	// Public fields
	public int      id;
	public String	type;
	public String   value;

	public PersistedSensorData(Cursor c) {
		id = c.getInt(Columns.DATA_ID_INDEX);
		type = c.getString(Columns.DATA_TYPE_INDEX);
		value = c.getString(Columns.DATA_VAUE_INDEX);
	}

	public PersistedSensorData(Parcel p) {
		id = p.readInt();
		type = p.readString();
		value = p.readString();
	}

	public PersistedSensorData() {
		id = -1;
	}
	
	public String toString() {
		return "[id] " + id + "; " + 
		"[type] " + type + "; " + 
		"[value] " + value + ":";
	}
}
