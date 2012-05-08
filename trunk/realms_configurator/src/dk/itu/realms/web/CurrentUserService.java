package dk.itu.realms.web;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import dk.itu.realms.application.AuthenticationService;
import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.HibernateUser;

/**
 * Provides information about the current user and make a few operations
 * available to be performed on the current user.
 */

@Named("currentUser")
@Scope("request")
public class CurrentUserService {

	@Inject
	@Named("userDAO")
	private UserDAO userDAO;
	
	@Inject
	@Named("authenticationService")
	private AuthenticationService authenticationService;
	
	private StreamedContent thumbImage;
	
	private HibernateUser user;
	
	/**
	 * Store a copy of the newly logged in user.
	 */
	@PostConstruct
	public void loadOnCreate() {
		user = load();
	}
	
	/**
	 * Load a fresh instance of the current user from the database. 
	 */
	public HibernateUser load() {
		return userDAO.findByEmail(authenticationService.getLoginID());
	}
	
	
	
	public String getUserName() {
		if(user != null) {
			return user.getName();
		}
		
		return authenticationService.getLoginID();
	}
	
	public String getEmail() {
		if(user != null) {
			return user.getEmail();
		}
		
		return authenticationService.getLoginID();
	}
	
	/**
	 * Makes it possible for pages to check if there is a logged in user or not. Delegates
	 * the task to the authenticationService. 
	 */
	public boolean getIsLoggedIn() {
		return authenticationService.isLoggedIn();
	}
	
	/**
	 * Makes it possible for pages to check if currently logged in user has ROLE-USER. Delegates
	 * the task to the authenticationService. 
	 */
	public boolean getHasUserRole() {
		return authenticationService.hasRole("ROLE_USER");
	}
	
	/**
	 * Log out the current user. Delegates the task to the authenticationService.
	 * 
	 *  Once logged out, return to the index page.
	 */
	public String logout() {
		authenticationService.logout();
		
		return "index";
	}
}
