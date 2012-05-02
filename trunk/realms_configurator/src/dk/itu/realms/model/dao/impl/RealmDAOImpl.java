package dk.itu.realms.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.entity.Realm;

@Repository("realmDAO")
@Transactional
public class RealmDAOImpl implements RealmDAO {

	private HibernateTemplate hibernateTemplate;
	
	public RealmDAOImpl() { }
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void save(Realm realm) throws DataAccessException {
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
		
		hibernateTemplate.saveOrUpdate(realm);
		
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Realm> listAll() throws DataAccessException {
		return hibernateTemplate.find("from " + Realm.class.getName());
	}

	@Override
	public Realm get(long id) throws DataAccessException {
		Realm realm = hibernateTemplate.get(Realm.class, id);
		
		/* force lazy loading */
		
		return realm;
	}

	@Override
	public void delete(Realm realm) {
		hibernateTemplate.delete(realm);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Realm> getAllByUserId(String email) {
		/* TODO: re-implement this with a join! */
		List<Realm> allRealms = hibernateTemplate.find("from " + Realm.class.getName());;
		
		List<Realm> result = new ArrayList<Realm>();
		for(Realm r: allRealms) {
			if(r.getOwner() != null && r.getOwner().getEmail().equals(email)) {
				result.add(r);
			}
		}
		
		return result;
	}

	@Override
	public Realm getByName(String realmName) {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Realm.class).add(Restrictions.eq("name", realmName));
		final List<Realm> realms = hibernateTemplate.findByCriteria(criteria);
		
		if(realms != null && realms.size() > 0) {
			Realm r = realms.get(0);
			/* force lazy loading -- if necesary */
			
			return r;
		}
		
		return null;
	}
}
