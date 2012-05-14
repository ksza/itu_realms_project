package dk.itu.realms.client;

import java.util.List;

import dk.itu.realms.client.model.Mark;
import dk.itu.realms.client.model.Realm;

/**
 * Server communication API.
 * 
 * @author karcsi
 * @version 1.0
 */
public interface IServerComm {

	/**
	 * Send the data to the server and receive the computed result.
	 * 
	 * @param token
	 * @return the computed step
	 */
	public Mark updateStatus(Long realmId, Double lat, Double lon, String userID);

	public List<Realm> getRealms(final Double latitude, final Double longitude, String userID);
	
	public void rateInfo(Long realmId, Long markID, String rating, String userID);
	
	public void markOption(Long realmID, Long markID, Long optionID, String userID);
	
}
