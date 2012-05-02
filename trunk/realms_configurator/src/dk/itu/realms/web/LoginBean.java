package dk.itu.realms.web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import dk.itu.realms.application.AuthenticationService;

@Named("loginBean")
@Scope("session")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;

	@Inject
	@Named("authenticationService")
	private AuthenticationService authenticationService;
	
	public String login() {

		boolean success = authenticationService.login(login, password);
		
		if (success){
			return "/regular_user/my_realms.xhtml?faces-redirect=true";
		} else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect username or password!", null));
			return null;
		}
	}
	
	public void logout() {
		authenticationService.logout();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
}
