package dk.itu.realms.web;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.HibernateUser;

@Named("userAccessService")
@Scope("request")
public class UserAccessService {

	@Inject
	@Named("userDAO")
	private UserDAO userDAO;
	
	public List<HibernateUser> getUsers() {
		return userDAO.listAll();
	}
	
}
