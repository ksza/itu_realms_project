package dk.itu.realms.client.model.data;

/**
 * Reresentation of location data.
 * 
 * @author karcsi
 * @version 1.0
 */
public class LocationData extends ObjectiveData {

	private double latitude;
	private double longitude;
	private double accuracy;
	
	public LocationData(final double latitude, final double longitude, final double accuracy) {
		super("location");
		
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getAccuracy() {
		return accuracy;
	}
	
}
