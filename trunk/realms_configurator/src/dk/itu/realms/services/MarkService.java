package dk.itu.realms.services;

import org.primefaces.model.map.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.itu.realms.model.dao.MarkDAO;
import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.dao.UserDataDAO;
import dk.itu.realms.model.entity.Mark;
import dk.itu.realms.model.entity.Option;
import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.model.entity.UserData;
import dk.itu.realms.services.util.LatLonCalculator;

@Service("update")
public class MarkService {

	@Autowired
	private RealmDAO realmDAO;

	@Autowired
	private UserDataDAO userDataDAO;

	@Autowired
	private MarkDAO markDAO;

	public Mark getMark(String lat, String lon, long realmId, long userId) {

		Realm realm = realmDAO.get(realmId);

		if (realm != null) {
			LatLonCalculator cal = new LatLonCalculator();

			LatLng pos = new LatLng(Double.parseDouble(lat),
					Double.parseDouble(lon));
			for (Mark m : realm.getMarks()) {
				if (cal.insideMark(pos, m)) {
					return m;
				}
			}
		}
		return null;
	}

	public void rateInfo(long realmId, long markId, int rating, long userId) {
		UserData data = new UserData();
		data.setMarkId(markId);
		data.setData(String.valueOf(rating));
		data.setUserId(userId);
		userDataDAO.save(data);
	}

	public void markOption(long realmId, long markId, long optionId, long userId) {
		UserData data = new UserData();
		data.setMarkId(markId);
		data.setUserId(userId);

		Mark mark = markDAO.get(markId);
		for (Option o : mark.getOptions()) {
			if (o.getId() == optionId)
				data.setData(o.toString());
		}

		userDataDAO.save(data);

	}

}
