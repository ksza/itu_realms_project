package dk.itu.realms.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dk.itu.realms.client.model.data.ObjectiveData;

/**
 * This is the main entity in the client-server communication. It communicates
 * all the necessary information needed by the server to compute the next action.
 * 
 * @author karcsi
 * @version 1.0
 */
public class CommunicationToken {

	private final String sessionID;
	private final Step currentStep;
	private final String chosenOption;
	
	private final List<ObjectiveData> data;
	
	public CommunicationToken(final String sessionID, final Step currentStep, final String chosenOption, final List<ObjectiveData> data) {
		this.sessionID = sessionID;
		this.currentStep = currentStep;
		this.chosenOption = chosenOption;
		
		this.data = new ArrayList<ObjectiveData>();
		Collections.copy(this.data, data);
	}
	
	public void addObjectiveData(final ObjectiveData entry) {
		data.add(entry);
	}

	public String getSessionID() {
		return sessionID;
	}

	public Step getStep() {
		return currentStep;
	}

	public String getChosenOption() {
		return chosenOption;
	}

	public List<ObjectiveData> getData() {
		return new ArrayList<ObjectiveData>(data);
	}
	
}
