package dk.itu.realms.model.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dk.itu.realms.model.dao.UserDataDAO;
import dk.itu.realms.model.entity.UserData;

@Repository("userDataDAO")
@Transactional
public class UserDataDAOImpl implements UserDataDAO {

	private HibernateTemplate hibernateTemplate;
	
	public UserDataDAOImpl() { }
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void save(UserData data) throws DataAccessException {
		System.out.println("Saving data: " + data.getData());
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		
		hibernateTemplate.saveOrUpdate(data);
		
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public List<UserData> getFiltered(Long realmID) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(UserData.class).add(Restrictions.eq("realm_id", realmID));
		final List<UserData> result = hibernateTemplate.findByCriteria(criteria);
		
		return result;
	}

	@Override
	public List<UserData> listAll() throws DataAccessException {
		final List<UserData> result = hibernateTemplate.find("from " + UserData.class.getName());
		
		return result;
	}
	
	@Override
	public UserData get(long id) throws DataAccessException {
		throw new NotYetImplementedException("");
	}

	@Override
	public void delete(UserData instance) {
		throw new NotYetImplementedException("");
	}
	
}
