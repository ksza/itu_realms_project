package dk.itu.realms.client.model;

import java.util.List;

/**
 * A step within a communication session with the server.
 * 
 * @author karcsi
 * @version 1.0
 */
public class Step {

	private final String number;
	private final StepState state;
	private final String title;
	private final String message;
	private final List<Option> options;
	
	public Step(final String number, final StepState state, final String title, final String message, final List<Option> options) {
		this.number = number;
		this.state = state;
		this.title = title;
		this.message = message;
		this.options = options;
	}

	public String getNumber() {
		return number;
	}
	
	public StepState getState() {
		return state;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public List<Option> getOptions() {
		return options;
	}	
	
}
