package dk.itu.realms.client.mock;

import java.util.UUID;

import dk.itu.realms.client.IServerConn;
import dk.itu.realms.client.model.CommunicationToken;
import dk.itu.realms.client.model.Step;
import dk.itu.realms.client.model.StepState;

public class ServerConnMock implements IServerConn {

	private String currentSessionID;
	
	
	@Override
	public String startSession(String userName, String password) {
		currentSessionID = UUID.randomUUID().toString();
		
		return currentSessionID;
	}

	@Override
	public Step communicate(CommunicationToken token) {
		
		return null;
	}

	@Override
	public void stopSession(String sessionID) {
		currentSessionID = null;
	}

}
