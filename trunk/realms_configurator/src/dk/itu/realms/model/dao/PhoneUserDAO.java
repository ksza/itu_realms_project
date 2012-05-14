package dk.itu.realms.model.dao;

import dk.itu.realms.model.entity.PhoneUser;

public interface PhoneUserDAO {
	
	public void save(PhoneUser user);

	public PhoneUser findUserByUserName(final String userName);
	
	public PhoneUser getUser(long id);
}
