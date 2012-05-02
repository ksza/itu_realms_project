package dk.itu.realms.client.mock;

import java.util.UUID;

import dk.itu.realms.client.IServerComm;
import dk.itu.realms.client.model.CommunicationToken;
import dk.itu.realms.client.model.Step;
import dk.itu.realms.client.model.StepState;

public class ServerConnMock implements IServerComm {

	private String currentSessionID;
	
	
	@Override
	public String connect(String userName, String password) {
		currentSessionID = UUID.randomUUID().toString();
		
		return currentSessionID;
	}

	@Override
	public Step reportStatus(CommunicationToken token) {
		
		return null;
	}

	@Override
	public void end(String sessionID) {
		currentSessionID = null;
	}

}
