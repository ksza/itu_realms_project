package dk.itu.realms.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.web.map.AbstractMapService;
import dk.itu.realms.web.map.RealmsMarker;

@Named("realmDetails")
@Scope("session")
public class RealmDetailsService extends AbstractMapService {

	private static final double MARKER_ACCURACY = 500;
	private static final String STROKE_COLOR = "#d93c3c";
	private static final String FILL_COLOR = "#d93c3c";
	private static final double OPACITY = 0.7;

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

	private boolean addMarksMode;
	public boolean isAddMarksMode() {
		return addMarksMode;
	}
	public void setAddMarksMode(boolean addMarksMode) {
		this.addMarksMode = addMarksMode;
	}

	public void onPointSelect(PointSelectEvent event) {  
		//		 if(addMarksMode) {
		LatLng latlng = event.getLatLng();

		//		if(latlng.getLat() <= (realm.getLatitude() + realm.getRadius()/1000) && 
		//				latlng.getLat() >= (realm.getLatitude() - realm.getRadius()/1000) &&
		//				latlng.getLng() <= (realm.getLongitude() + realm.getRadius()/1000) &&
		//				latlng.getLng() >= (realm.getLongitude() - realm.getRadius()/1000)) {

		RealmsMarker marker = new RealmsMarker(new LatLng(latlng.getLat(), latlng.getLng()));
		mapModel.addOverlay(marker);

		Circle markerAccuracy = new Circle(marker.getLatlng(), MARKER_ACCURACY);
		markerAccuracy.setStrokeColor(STROKE_COLOR);
		markerAccuracy.setFillColor(FILL_COLOR);
		markerAccuracy.setFillOpacity(OPACITY);
		mapModel.addOverlay(markerAccuracy);

		String markerID = marker.getId().replace("marker", "circle");
		markerAccuracy.setId(markerID);
		//		}
		//		 }
	}

	public void onMarkerDrag(MarkerDragEvent event) {
		RealmsMarker marker = (RealmsMarker)event.getMarker();

		LatLng latlng = marker.getLatlng();

		//		if(latlng.getLat() <= (realm.getLatitude() + realm.getRadius()/1000) && 
		//				latlng.getLat() >= (realm.getLatitude() - realm.getRadius()/1000) &&
		//				latlng.getLng() <= (realm.getLongitude() + realm.getRadius()/1000) &&
		//				latlng.getLng() >= (realm.getLongitude() - realm.getRadius()/1000)) {

		marker.setLatlng(new LatLng(marker.getLatlng().getLat(), marker.getLatlng().getLng()));
		String id = marker.getId();
		id = id.replace("marker", "circle");

		for(Circle c: mapModel.getCircles()) {
			if(c.getId().equals(id)) {
				c.setCenter(marker.getLatlng());
				break;
			}
		}
		//		}
	}

	public void onMarkerSelect(OverlaySelectEvent event) {  
		RealmsMarker marker = (RealmsMarker) event.getOverlay();

		for(Marker m: mapModel.getMarkers()) {
			if(! m.getId().equals(marker.getId())) {
				((RealmsMarker)m).setSelected(false);
				((RealmsMarker)m).updateIcon();
			}
		}
		marker.setSelected(true);
		marker.updateIcon();
		
//		String id = marker.getId();
//		id = id.replace("marker", "circle");
//
//		for(Circle c: mapModel.getCircles()) {
//			if(c.getId().equals(id)) {
//				c.setCenter(marker.getLatlng());
//				break;
//			}
//		}
	}  

}
