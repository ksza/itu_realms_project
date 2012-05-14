package dk.itu.realms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.itu.realms.model.dao.PhoneUserDAO;
import dk.itu.realms.model.entity.PhoneUser;

@Service("phoneuser")
public class PhoneUserService {

	@Autowired
	private PhoneUserDAO puDAO;
	
	public PhoneUser getUser(long id) {
		return puDAO.getUser(id);
	}

	public PhoneUser createUser(String username, String password, String fullname) {
		// Chekc if user exists
		if (puDAO.findUserByUserName(username) == null) {
			PhoneUser user = new PhoneUser();
			user.setUsername(username);
			user.setPassword(password);
			user.setFullname(fullname);
			puDAO.save(user);
			return puDAO.findUserByUserName(username);
		}
		return null;
	}
}
