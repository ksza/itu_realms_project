package dk.itu.realms.client.model.data;

/**
 * The base class for any objective data monitored by the client.
 * 
 * @author karcsi
 * @version 1.0
 */
public class ObjectiveData {

	private final String type;
	
	public ObjectiveData(final String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
