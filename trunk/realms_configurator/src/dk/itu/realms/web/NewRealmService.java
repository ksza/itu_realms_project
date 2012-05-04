package dk.itu.realms.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.Realm;

@Named("newRealmService")
@Scope("session")
public class NewRealmService {

	@Inject
	@Named("realmDAO")
	private RealmDAO realmDAO;

	@Inject
	@Named("currentUser")
	protected CurrentUserService currentUserService;

	@Inject
	@Named("userDAO")
	protected UserDAO userDAO;

	private Realm realm;

	private MapModel draggableModel;

	private MapParams mapParams;

	@PostConstruct
	public void intit() {
		mapParams = new MapParams();

		draggableModel = new DefaultMapModel();

		realm = new Realm();
		realm.setLatitude(55.676097);
		realm.setLongitude(12.568337);
		realm.setRadius(1000d);

		//Shared coordinates
		LatLng coord1 = new LatLng(realm.getLatitude(), realm.getLongitude());
		LatLng coord2 = new LatLng(realm.getLatitude(), realm.getLongitude());

		/* circle */
		Circle realmRadius = new Circle(coord2, realm.getRadius());
		realmRadius.setStrokeColor("#d93c3c");
		realmRadius.setFillColor("#d93c3c");
		realmRadius.setFillOpacity(0.7);

		//Draggable
		draggableModel.addOverlay(new Marker(coord1, "Realm Center"));

		for(Marker marker : draggableModel.getMarkers()) {
			marker.setDraggable(true);
		}

		draggableModel.addOverlay(realmRadius);
	}

	public MapParams getMapParams() {
		return mapParams;
	}

	public Realm getRealm() {
		return realm;
	}

	public void update() {
		draggableModel.getCircles().get(0).setRadius(realm.getRadius());
	}

	public String save() {
		//		if(realmDAO.getByName(realm.getName()) != null) {
		//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Already Exists!"));
		//		} else {

		realm.setOwner(userDAO.findByEmail(currentUserService.getEmail()));

		realmDAO.save(realm);

		realm = realmDAO.getByName(realm.getName());

		return "/regular_user/realm_details.xhtml?faces-redirect=true&realmID=" + realm.getId();
		//		}
		//		
		//		return null;
	}

	public MapModel getDraggableModel() {
		return draggableModel;
	}

	public void onMarkerDrag(MarkerDragEvent event) {
		Marker marker = event.getMarker();

		realm.setLatitude(marker.getLatlng().getLat());
		realm.setLongitude(marker.getLatlng().getLng());

		draggableModel.getCircles().get(0).setCenter(new LatLng(marker.getLatlng().getLat(), marker.getLatlng().getLng()));
	}

	public void onStateChange(StateChangeEvent event) {  
		//        LatLngBounds bounds = event.getBounds();  
		int zoomLevel = event.getZoomLevel();  

		mapParams.setCenter(event.getCenter().getLat() + ", " + event.getCenter().getLng());
		mapParams.setZoom(String.valueOf(zoomLevel));
	}  

	public class MapParams {
		private String center = "55.676097,12.568337";
		private String zoom = "11";

		public String getCenter() {
			return center;
		}
		public void setCenter(String center) {
			this.center = center;
		}
		public String getZoom() {
			return zoom;
		}
		public void setZoom(String zoom) {
			this.zoom = zoom;
		}
	}
}
