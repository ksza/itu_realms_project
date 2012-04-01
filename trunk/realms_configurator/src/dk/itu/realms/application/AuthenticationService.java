package dk.itu.realms.application;

import javax.annotation.security.RolesAllowed;

/**
 *  Defines the main operations for the authentication service.
 */
public interface AuthenticationService {
	
	public static final String ANONYMOUS = "anonymousUser"; 
	
	/**
	 * Try to log user in based on the given credentials.
	 * 
	 * @param username
	 * @param password
	 * @return true if the login was successful; false otherwise
	 */
	public boolean login(String username, String password);

	/**
	 * Logout the currently logged-in user. Only logged-in users can perform this operation!
	 */
	@RolesAllowed( {"ROLE_USER"} )
	public void logout();
	
	/**
	 * Get the ID of the currently logged in user.
	 */
	public String getLoginID();
	
	/**
	 * Check if there is a logged in user or not. "anonymousUser" user is not considered to be logged
	 * in!
	 */
	public boolean isLoggedIn();
	
	/**
	 * Check if the current entity has the given role.
	 */
	public boolean hasRole(String role);
}
