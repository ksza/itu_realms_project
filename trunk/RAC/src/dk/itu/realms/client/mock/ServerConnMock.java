package dk.itu.realms.client.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dk.itu.realms.client.IServerComm;
import dk.itu.realms.client.model.CommunicationToken;
import dk.itu.realms.client.model.Realm;
import dk.itu.realms.client.model.Step;

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
	
	@Override
	public List<Realm> getRealms(final Double latitude, final Double longitude) {
		final List<Realm> realms = new ArrayList<Realm>();
		
		realms.add(new Realm(1L, "Realm1", "description one", "location description one"));
		realms.add(new Realm(2L, "Realm2", "description two", "location description 2"));
		realms.add(new Realm(3L, "Realm3", "description three", "location description 3"));
		
		return realms;
	}

}
