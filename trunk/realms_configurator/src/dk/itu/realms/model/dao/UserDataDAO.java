package dk.itu.realms.model.dao;

import org.springframework.dao.DataAccessException;

import dk.itu.realms.model.entity.UserData;

public interface UserDataDAO {

	public void save(UserData data) throws DataAccessException;
}
