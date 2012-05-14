package dk.itu.realms.model.dao;

import java.util.List;

import dk.itu.realms.model.entity.UserData;

public interface UserDataDAO extends GenericDAO<UserData> {

	public List<UserData> getFiltered(Long realmID);
	
}
