package dk.itu.realms.model.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dk.itu.realms.model.dao.UserDataDAO;
import dk.itu.realms.model.entity.Mark;
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

}
