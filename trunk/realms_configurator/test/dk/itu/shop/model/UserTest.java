package dk.itu.shop.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dk.itu.realms.model.dao.AuthoritiesDAO;
import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.Authorities;
import dk.itu.realms.model.entity.HibernateUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/applicationContext.xml" })
public class UserTest {

	private UserDAO userDAO;
	private AuthoritiesDAO authoritiesDAO;

	@Autowired
	public void setUserDao(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Autowired
	public void setAuthoritiesDao(AuthoritiesDAO authoritiesDAO) {
		this.authoritiesDAO = authoritiesDAO;
	}
	
	@Test
	public void test_createUser() {
		int expectedResult = 1;

		HibernateUser user = new HibernateUser();
		user.setEmail("user@test.com");
		user.setName("Test User");
		user.setPassword("six_char_12");

		Set<Authorities> roles = new HashSet<Authorities>();
		
		Authorities authorities = new Authorities();
		authorities.setUsername("user@test.com");
		authorities.setAuthority("ROLE_USER");
		roles.add(authorities);
		
		authorities = new Authorities();
		authorities.setUsername("user@test.com");
		authorities.setAuthority("ROLE_ADMIN");
		roles.add(authorities);
		
		user.setRoles(roles);
		
		userDAO.save(user);
		Assert.assertEquals(expectedResult, userDAO.listAll().size());

		/* persistence should have been cascaded */
		Assert.assertEquals(2, authoritiesDAO.listAll().size());
	}

	@Test
	public void test_getUser() {
		List<HibernateUser> userList = userDAO.listAll();
		Assert.assertEquals(1, userList.size());
		
		HibernateUser expectedUser = userList.get(0);
		HibernateUser userResult = userDAO.get(expectedUser.getId());
		
		Assert.assertEquals(expectedUser.getId(), userResult.getId());
	}

	@Test
	public void test_updateUser() {
		List<HibernateUser> userList = userDAO.listAll();
		Assert.assertEquals(1, userList.size());
		
		HibernateUser user = userList.get(0);
		user.setName("Test User update");
		
		userDAO.save(user);
		
		HibernateUser userResult = userDAO.get(user.getId());
		Assert.assertEquals(user.getName(), userResult.getName());
	}

	@Test
	public void test_deleteUser() {
		List<HibernateUser> userList = userDAO.listAll();
		Assert.assertEquals(1, userList.size());
		
		HibernateUser user = userList.get(0);
		userDAO.delete(user);
		
		HibernateUser userResult = userDAO.get(user.getId());
		Assert.assertEquals(userResult, null);
		
		/* deletion should been cascaded */
		Assert.assertEquals(0, authoritiesDAO.listAll().size());
	}
}
