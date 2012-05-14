package dk.itu.realms.web.feedback;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.MarkDAO;
import dk.itu.realms.model.dao.UserDataDAO;
import dk.itu.realms.model.entity.Mark;
import dk.itu.realms.model.entity.Option;
import dk.itu.realms.model.entity.UserData;
import dk.itu.realms.web.CurrentUserService;

@Named("realmFeedback")
@Scope("session")
public class RealmFeedbackService {

	@Inject
	@Named("userDataDAO")
	private UserDataDAO userDataDAO;
	
	@Inject
	@Named("markDAO")
	private MarkDAO markDAO;

	@Inject
	@Named("currentUser")
	protected CurrentUserService currentUserService;
	
	private List<UserDataAdapter> userData;

	@PostConstruct
	public void init() {
		userData = new ArrayList<UserDataAdapter>();
	}
	
	private Long paramRealmID;
	public Long getParamRealmID() {
		return paramRealmID;
	}
	public void setParamRealmID(Long paramRealmID) {
		userData = new ArrayList<UserDataAdapter>();
		
		final List<UserData> rawUserData = userDataDAO.getFiltered(paramRealmID);
		
		for(UserData data: rawUserData) {
			final Mark m = markDAO.get(data.getMarkId());
			Option selectedOption = null;

			if(m != null) {
				if(m.getType().equals("QUESTION")) {
					Long optionID = Long.parseLong(data.getData());

					for(Option o: m.getOptions()) {
						if(o.getId() == optionID) {
							selectedOption = o;
							break;
						}
					}
				}

				userData.add(new UserDataAdapter(data.getUserId(), data.getData(), m, selectedOption));
			}
		}
	}
	
	public List<UserDataAdapter> getUserData() {
		return userData;
	}
	public void setUserData(List<UserDataAdapter> userData) {
	}
	
}
