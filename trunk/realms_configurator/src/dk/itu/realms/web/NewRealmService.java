package dk.itu.realms.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.context.annotation.Scope;

import dk.itu.realms.model.dao.RealmDAO;
import dk.itu.realms.model.dao.UserDAO;
import dk.itu.realms.model.entity.Realm;
import dk.itu.realms.web.map.AbstractMapService;

@Named("newRealmService")
@Scope("session")
public class NewRealmService extends AbstractMapService {

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

	private String coordsFromAddress;

	@Override
	@PostConstruct
	public void init() {
		super.init();

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
		mapModel.addOverlay(new Marker(coord1, "Realm Center"));

		for(Marker marker : mapModel.getMarkers()) {
			marker.setDraggable(true);
		}

		mapModel.addOverlay(realmRadius);
	}

	public Realm getRealm() {
		return realm;
	}

	public void update() {
		mapModel.getCircles().get(0).setRadius(realm.getRadius());
	}

	public String save() {
		realm.setOwner(userDAO.findByEmail(currentUserService.getEmail()));

		realmDAO.save(realm);

		realm = realmDAO.getByName(realm.getName());

		return "/regular_user/realm_details.xhtml?faces-redirect=true&realmID=" + realm.getId();
	}

	public void onMarkerDrag(MarkerDragEvent event) {
		Marker marker = event.getMarker();

		realm.setLatitude(marker.getLatlng().getLat());
		realm.setLongitude(marker.getLatlng().getLng());

		mapModel.getCircles().get(0).setCenter(new LatLng(marker.getLatlng().getLat(), marker.getLatlng().getLng()));
	}

	public void modifyAddress() {
		if(coordsFromAddress != null) {
			final String[] latLng = coordsFromAddress.split(", ");

			realm.setLatitude(Double.parseDouble(latLng[0]));
			realm.setLongitude(Double.parseDouble(latLng[1]));

			mapModel.getMarkers().get(0).setLatlng(new LatLng(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1])));
			mapModel.getCircles().get(0).setCenter(new LatLng(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1])));
			
			getMapParams().setCenter(coordsFromAddress);
			
			coordsFromAddress = "";			
		}
	}

	public String getCoordsFromAddress() {
		return coordsFromAddress;
	}
	public void setCoordsFromAddress(String coordsFromAddress) {
		this.coordsFromAddress = coordsFromAddress;
	}
}
