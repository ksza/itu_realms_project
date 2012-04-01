package dk.itu.realms.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface GenericDAO<T> {

	public void save(final T instance) throws DataAccessException;
	public T get(final long id) throws DataAccessException;
	public void delete(final T instance);
	public List<T> listAll() throws DataAccessException;
		
}
