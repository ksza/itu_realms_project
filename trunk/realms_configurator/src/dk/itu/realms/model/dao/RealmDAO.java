package dk.itu.realms.model.dao;

import java.util.List;

import dk.itu.realms.model.entity.Realm;

public interface RealmDAO extends GenericDAO<Realm> {

	public List<Realm> getAllByUserId(String email);
	
	public Realm getByName(String realmName);
	
}
