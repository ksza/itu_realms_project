package dk.itu.realms.client;

import java.util.List;

import dk.itu.realms.client.model.CommunicationToken;
import dk.itu.realms.client.model.Realm;
import dk.itu.realms.client.model.Step;

/**
 * Server communication API.
 * 
 * @author karcsi
 * @version 1.0
 */
public interface IServerComm {

	/**
	 * Start a new session on the server for the specified user. The sessionID is returned
	 * as a result.
	 * 
	 * @param userName
	 * @param password
	 * @return the generated sessionID
	 */
	public String connect(String userName, String password);
	
	/**
	 * Send the data to the server and receive the computed result.
	 * 
	 * @param token
	 * @return the computed step
	 */
	public Step reportStatus(CommunicationToken token);

	/**
	 * Stop the session with the given sessionID.
	 * 
	 * @param sessionID
	 */
	public void end(String sessionID);
	
	public List<Realm> getRealms(final Double latitude, final Double longitude);
	
}
