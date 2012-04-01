package dk.itu.realms.model.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dk.itu.realms.model.dao.AuthoritiesDAO;
import dk.itu.realms.model.entity.Authorities;

@Repository("authoritiesDAO")
@Transactional
public class AuthoritiesDAOImpl implements AuthoritiesDAO {

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void save(Authorities instance) throws DataAccessException {
		hibernateTemplate.saveOrUpdate(instance);
	}

	@Override
	public Authorities get(long id) throws DataAccessException {
		return hibernateTemplate.get(Authorities.class, id);	
	}

	@Override
	public void delete(Authorities instance) {
		hibernateTemplate.delete(instance);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Authorities> listAll() throws DataAccessException {
		return hibernateTemplate.find("from " + Authorities.class.getName());
	}

}
