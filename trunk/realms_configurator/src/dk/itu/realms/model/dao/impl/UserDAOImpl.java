package dk.itu.realms.model.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.HibernateUser;
import dk.itu.realms.model.entity.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	private HibernateTemplate hibernateTemplate;
	
	public UserDAOImpl() { }
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void save(HibernateUser user) throws DataAccessException {
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		
		hibernateTemplate.saveOrUpdate(user);
		
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HibernateUser> listAll() throws DataAccessException {
		return hibernateTemplate.find("from " + User.class.getName());
	}

	@Override
	public HibernateUser get(long id) throws DataAccessException {
		HibernateUser user = hibernateTemplate.get(HibernateUser.class, id);
		
		/* force lazy loading */
		
		return user;
	}

	@Override
	public void delete(HibernateUser user) {
		hibernateTemplate.delete(user);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public HibernateUser findByEmail(String email) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(HibernateUser.class).add(Restrictions.eq("email", email));
		final List<HibernateUser> users = hibernateTemplate.findByCriteria(criteria);
		
		if(users != null && users.size() > 0) {
			HibernateUser u = users.get(0);
			/* force lazy loading */
			
			return u;
		}
		
		return null;
	}
}
