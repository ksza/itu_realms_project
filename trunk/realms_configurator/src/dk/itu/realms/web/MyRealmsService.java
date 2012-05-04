package dk.itu.realms.web;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.entity.Realm;

@Named("myRealmsService")
@Scope("request")
public class MyRealmsService {

	@Inject
	@Named("realmDAO")
	private RealmDAO realmDAO;
	
	@Inject
	@Named("currentUser")
	protected CurrentUserService currentUserService;
	
	@Inject
	@Named("newRealmService")
	private NewRealmService newRealmService;
	
	public List<Realm> getRealms() {
		return realmDAO.getAllByUserId(currentUserService.getEmail());
	}
	
	public void deleteRealm() {
		if(selectedRealm != null) {
			realmDAO.delete(selectedRealm);
		}
	}

	public String editRealm() {
		return "/regular_user/realm_details.xhtml?faces-redirect=true&realmID=" + selectedRealm.getId();
	}
	
	private Realm selectedRealm;
	
	public void setSelectedRealm(Realm realm) {
		selectedRealm = realm;
	}
	
	public String newRealm() {
		newRealmService.init();
		
		return "new_realm";
	}
	
}
