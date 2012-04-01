package dk.itu.realms.model.dao;

import dk.itu.realms.model.entity.HibernateUser;

public interface UserDAO extends GenericDAO<HibernateUser> {
	
	public HibernateUser findByEmail(final String email);
	
}
