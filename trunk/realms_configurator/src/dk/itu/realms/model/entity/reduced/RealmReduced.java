package dk.itu.realms.model.entity.reduced;

import dk.itu.realms.model.entity.Realm;

public class RealmReduced {

	private String name;
	private String generalDescription;
	private String locationDecription;
	private Double latitude;
	private Double longitude;
	private Double radius;

	public RealmReduced() {
	}

	public RealmReduced(Realm realm) {
		this.name = realm.getName();
		this.generalDescription = realm.getGeneralDescription();
		this.locationDecription = realm.getLocationDecription();
		this.latitude = realm.getLatitude();
		this.longitude = realm.getLongitude();
		this.radius = realm.getRadius();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeneralDescription() {
		return generalDescription;
	}

	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}

	public String getLocationDecription() {
		return locationDecription;
	}

	public void setLocationDecription(String locationDecription) {
		this.locationDecription = locationDecription;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

}
