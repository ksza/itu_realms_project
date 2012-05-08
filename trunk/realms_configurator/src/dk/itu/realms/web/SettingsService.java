package dk.itu.realms.web;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.HibernateUser;

@Named("settingsService")
@Scope("request")
public class SettingsService {

	@Inject
	@Named("currentUser")
	private CurrentUserService currentUserService;

	@Inject
	@Named("userDAO")
	private UserDAO userDAO;

	@Inject
	@Named("appUtils")
	private AppUtils appUtils;

	private UploadedFile file;

	private HibernateUser currentUser;

	@PostConstruct
	public void loadCurrentUser() {
		currentUser = currentUserService.load();
	}

	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public HibernateUser getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(HibernateUser currentUser) {
		this.currentUser = currentUser;
	}


}
