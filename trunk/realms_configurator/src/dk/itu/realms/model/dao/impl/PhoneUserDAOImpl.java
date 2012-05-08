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

import dk.itu.realms.model.dao.PhoneUserDAO;
import dk.itu.realms.model.entity.PhoneUser;

@Repository("phoneusersDAO")
@Transactional
public class PhoneUserDAOImpl implements PhoneUserDAO {

	
	private HibernateTemplate hibernateTemplate;
	
	public PhoneUserDAOImpl(){ }
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void save(PhoneUser user) throws DataAccessException {
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		hibernateTemplate.saveOrUpdate(user);	
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public PhoneUser findUserByUserName(String username) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(PhoneUser.class).add(Restrictions.eq("username", username));
		final List<PhoneUser> users = hibernateTemplate.findByCriteria(criteria);
		
		if(users != null && users.size() > 0) {
			PhoneUser u = users.get(0);		
			return u;
		}		
		return null;
	}
}
