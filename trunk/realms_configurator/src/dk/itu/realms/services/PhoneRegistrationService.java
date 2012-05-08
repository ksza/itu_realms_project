package dk.itu.realms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.itu.realms.model.dao.PhoneUserDAO;
import dk.itu.realms.model.entity.PhoneUser;

@Service("phoneregister")
public class PhoneRegistrationService {

	@Autowired
	private PhoneUserDAO puDAO;

	public PhoneUser createUser(String username, String password, String fullname) {
		// Chekc if user exists
		if (puDAO.findUserByUserName(username) == null) {
			PhoneUser user = new PhoneUser();
			user.setUsername(username);
			user.setPassword(password);
			user.setFullname(fullname);
			puDAO.save(user);
			return user;
		}
		return null;
	}
}
