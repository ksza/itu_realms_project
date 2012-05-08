package dk.itu.realms.web;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.Authorities;
import dk.itu.realms.model.entity.HibernateUser;

@Named("registrationService")
@Scope("request")
public class RegistrationService {

	@Inject
	@Named("userDAO")
	private UserDAO userDAO;

	@Inject
	@Named("userModel")
	private HibernateUser userModel;

	@Inject
	@Named("appUtils")
	private AppUtils appUtils;

	private UploadedFile file;

	public String register() {
		try {
			userModel.setEnabled(true);
			
			Authorities userRole = new Authorities();
			userRole.setUsername(userModel.getEmail());
			userRole.setAuthority("ROLE_USER");

			Set<Authorities> auth = new HashSet<Authorities>();
			auth.add(userRole);
			userModel.setRoles(auth);

			userDAO.save(userModel);
		} catch(DataAccessException e) {
			return "error";
		}

		return "index";
	}

	public String showAdminPage() {
		return "/admin/admin_page.xhtml?faces-redirect=true";
	}

	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
