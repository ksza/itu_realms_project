package dk.itu.realms.client.model;

/**
 * An option which can be optionally selected in a communication session
 * as a response of a certain step.
 * 
 * @author karcsi
 * @version 1.0
 */
public class Option {

	private Long id;
	private String description;
	private boolean answer;
	
	public Option(final Long id, final String description, final boolean answer) {
		this.id = id;
		this.description = description;
		this.answer = answer;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isAnswer() {
		return answer;
	}
}
