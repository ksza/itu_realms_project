package dk.itu.realms.model.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dk.itu.realms.model.dao.MarkDAO;
import dk.itu.realms.model.entity.Mark;

@Repository("markDAO")
@Transactional
public class MarkDAOImpl implements MarkDAO {

	private HibernateTemplate hibernateTemplate;

	public MarkDAOImpl() { }

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void save(Mark mark) throws DataAccessException {
		hibernateTemplate.getSessionFactory().getCurrentSession().flush();

		hibernateTemplate.saveOrUpdate(mark);

		hibernateTemplate.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Mark> listAll() throws DataAccessException {
		return hibernateTemplate.find("from " + Mark.class.getName());
	}

	@Override
	public Mark get(long id) throws DataAccessException {
		Mark mark = hibernateTemplate.get(Mark.class, id);

		/* force lazy loading */
		if(mark != null) {
			if(mark.getOptions() != null)
				mark.getOptions().size();
		}

		return mark;
	}

	@Override
	public void delete(Mark mark) {
		hibernateTemplate.delete(mark);	
	}

}
