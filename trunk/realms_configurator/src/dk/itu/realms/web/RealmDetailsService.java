package dk.itu.realms.web;

import java.util.Set;
import java.util.TreeSet;

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
import dk.itu.realms.model.entity.Mark;
import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.web.map.AbstractMapService;
import dk.itu.realms.web.map.RealmCircle;
import dk.itu.realms.web.map.RealmsMarker;

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

			selectedMarker = null;
		}
	}

	public boolean getHasSelected() {
		return selectedMarker != null;
	}

	public void deleteSelected() {
		if(selectedMarker != null) {
			mapModel.getCircles().remove(selectedMarker.getCircle());
			mapModel.getMarkers().remove(selectedMarker);
			selectedMarker = null;
		}
	}

	private boolean value1;
	public boolean isValue1() {  
		return value1;  
	}  
	public void setValue1(boolean value1) {  
		this.value1 = value1;  
	}
	public void buttonStateChanged() {
		if(value1) {

		}
	}

	private RealmsMarker selectedMarker;
	public RealmsMarker getSelectedMarker() {
		return selectedMarker;
	}

	public boolean getAllComplete() {
		boolean complete = true;
		for(Marker m: getMapModel().getMarkers()) {
			if(! ((RealmsMarker)m).isComplete()) {
				complete = false;
			}
		}
		
		return complete;
	}
	
	public void saveRealm() {
		if(realm != null) {
			final Set<Mark> marks = new TreeSet<Mark>();
			for(Marker m: mapModel.getMarkers()) {
				marks.add((Mark)m.getData());
			}
			
			realmDAO.save(realm);
		}
	}
	
	public void onPointSelect(PointSelectEvent event) {  
		//		if(value1) {
		LatLng latlng = event.getLatLng();

		//		if(latlng.getLat() <= (realm.getLatitude() + realm.getRadius()/1000) && 
		//				latlng.getLat() >= (realm.getLatitude() - realm.getRadius()/1000) &&
		//				latlng.getLng() <= (realm.getLongitude() + realm.getRadius()/1000) &&
		//				latlng.getLng() >= (realm.getLongitude() - realm.getRadius()/1000)) {

		RealmsMarker marker = new RealmsMarker(new LatLng(latlng.getLat(), latlng.getLng()));
		mapModel.addOverlay(marker);
		mapModel.addOverlay(marker.getCircle());

		//		}
		//		 }
	}

	public void onMarkerDrag(MarkerDragEvent event) {
		RealmsMarker marker = (RealmsMarker)event.getMarker();

		//		LatLng latlng = marker.getLatlng();

		//		if(latlng.getLat() <= (realm.getLatitude() + realm.getRadius()/1000) && 
		//				latlng.getLat() >= (realm.getLatitude() - realm.getRadius()/1000) &&
		//				latlng.getLng() <= (realm.getLongitude() + realm.getRadius()/1000) &&
		//				latlng.getLng() >= (realm.getLongitude() - realm.getRadius()/1000)) {

		marker.setLatlng(new LatLng(marker.getLatlng().getLat(), marker.getLatlng().getLng()));
		//		}
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		RealmsMarker marker = null;

		if(event.getOverlay() instanceof RealmsMarker) {
			marker = (RealmsMarker) event.getOverlay();
		} else if(event.getOverlay() instanceof RealmCircle) {
			marker = ((RealmCircle)event.getOverlay()).getParent();
		}

		if(marker != null) {
			marker.setSelected(true);
			for(Marker m: mapModel.getMarkers()) {
				if(! m.getId().equals(marker.getId())) {
					((RealmsMarker)m).setSelected(false);
				}
			}

			selectedMarker = marker;
		}
	}  

}
