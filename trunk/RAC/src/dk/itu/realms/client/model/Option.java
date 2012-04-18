package dk.itu.realms.client.model;

/**
 * An option which can be optionally selected in a communication session
 * as a response of a certain step.
 * 
 * @author karcsi
 * @version 1.0
 */
public class Option {

	private int id;
	private String description;
	
	public Option(final int id, final String description) {
		this.id = id;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
}
