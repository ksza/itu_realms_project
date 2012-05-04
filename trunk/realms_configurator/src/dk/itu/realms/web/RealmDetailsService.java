package dk.itu.realms.web;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.web.map.AbstractMapService;

@Named("realmDetails")
@Scope("session")
public class RealmDetailsService extends AbstractMapService {

	@Inject
	@Named("realmDAO")
	private RealmDAO realmDAO;
	
	@Inject
	@Named("currentUser")
	protected CurrentUserService currentUserService;
	
	@Override
	@PostConstruct
	public void init() {
		super.init();
	}
	
	private Realm realm;
	public Realm getRealm() {
		return realm;
	}
	public void setRealm(Realm realm) {
		this.realm = realm;
	}
	
	private Long paramRealmID;
	public Long getParamRealmID() {
		return paramRealmID;
	}
	public void setParamRealmID(Long paramRealmID) {
		this.paramRealmID = paramRealmID;
		
		if(this.paramRealmID != null) {
			realm = realmDAO.get(paramRealmID);
			
			mapModel = new DefaultMapModel();
			
			LatLng realmCenterCoord = new LatLng(realm.getLatitude(), realm.getLongitude());
			
			/* circle */
			Circle realmRadius = new Circle(realmCenterCoord, realm.getRadius());
			realmRadius.setStrokeColor("#d93c3c");
			realmRadius.setFillColor("#d93c3c");
			realmRadius.setFillOpacity(0.2);
			
			mapModel.addOverlay(realmRadius);
			
			mapParams.setCenter(realm.getLatitude() + ", " + realm.getLongitude());
		}
	}
	
	private Double viewParamLat;
	private Double viewParamLong;
	private String viewParamTitle;
	private String viewParamDescription;
	public Double getViewParamLat() {
		return viewParamLat;
	}
	public void setViewParamLat(Double viewParamLat) {
		this.viewParamLat = viewParamLat;
	}
	public Double getViewParamLong() {
		return viewParamLong;
	}
	public void setViewParamLong(Double viewParamLong) {
		this.viewParamLong = viewParamLong;
	}
	public String getViewParamTitle() {
		return viewParamTitle;
	}
	public void setViewParamTitle(String viewParamTitle) {
		this.viewParamTitle = viewParamTitle;
	}
	public String getViewParamDescription() {
		return viewParamDescription;
	}
	public void setViewParamDescription(String viewParamDescription) {
		this.viewParamDescription = viewParamDescription;
	}

	public void addMarker(ActionEvent actionEvent) {
		Marker marker = new Marker(new LatLng(viewParamLat, viewParamLong), viewParamTitle);
		mapModel.addOverlay(marker);
	}
	
}
