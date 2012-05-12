package dk.itu.realms.client.model;

public class Realm {

	private long id;
	private String name;
	private String generalDescription;
	private String locationDecription;
	
	public Realm(String name, String generalDescription, String locationDecription) {
		this.name = name;
		this.generalDescription = generalDescription;
		this.locationDecription = locationDecription;
	}
	
	public Realm(Long id, String name, String generalDescription, String locationDecription) {
		this(name, generalDescription, locationDecription);
		
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
