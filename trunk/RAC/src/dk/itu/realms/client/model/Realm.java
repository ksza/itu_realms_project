package dk.itu.realms.client.model;

public class Realm {

	private String name;
	private String generalDescription;
	private String locationDecription;
	
	public Realm(String name, String generalDescription, String locationDecription) {
		this.name = name;
		this.generalDescription = generalDescription;
		this.locationDecription = locationDecription;
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

	
	
}
