package dk.itu.realms.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import dk.itu.realms.model.entity.Option;
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

		optionBean = new OptionBean();
		addMarksMode = false;
		showRealmRadius = false;
	}

	private Circle realmRadiusCircle;
	
	private OptionBean optionBean;
	private Option selectedOption;
	public OptionBean getOptionBean() {
		return optionBean;
	}
	public void setOptionBean(OptionBean optionBean) {
		this.optionBean = optionBean;
	}
	public Option getSelectedOption() {
		return selectedOption;
	}
	public void setSelectedOption(Option selectedOption) {
		this.selectedOption = selectedOption;
	}
	public void addOption() {
		Option newOption = new Option();
		newOption.setOptionName(optionBean.getOptionName());
		newOption.setCorrectAnswer(false);
		
		((List<Option>)selectedMarker.getOptions()).add(newOption);
		optionBean.clean();
	}
	public void deleteSelectedOption() {
		if(selectedOption != null) {
			((List<Option>)selectedMarker.getOptions()).remove(selectedOption);
		}
	}
	public void markAnswer() {
		if(selectedMarker != null) {
			for(Option o: selectedMarker.getOptions()) {
				if(! o.getOptionName().equals(selectedOption.getId())) {
					o.setCorrectAnswer(false);
				}
				
				selectedOption.setCorrectAnswer(true);
			}
		}
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
			realmRadiusCircle = new Circle(new LatLng(realm.getLatitude(), realm.getLongitude()), realm.getRadius());
			realmRadiusCircle.setStrokeColor("#d93c3c");
			realmRadiusCircle.setFillColor("#d93c3c");
			realmRadiusCircle.setFillOpacity(0.2);
			
			Marker realmCenterMarker = new Marker(realmCenterCoord);
			realmCenterMarker.setDraggable(false);
			realmCenterMarker.setIcon("/realms_configurator/faces/resources/images/marker_flag.png");
			mapModel.addOverlay(realmCenterMarker);

			mapParams.setCenter(realm.getLatitude() + ", " + realm.getLongitude());

			selectedMarker = null;

			/* load markers */
			for(Mark m: realm.getMarks()) {
				RealmsMarker marker = new RealmsMarker(m);
				mapModel.addOverlay(marker);
				mapModel.addOverlay(marker.getCircle());
			}
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

	private boolean addMarksMode;
	public boolean isAddMarksMode() {  
		return addMarksMode;  
	}  
	public void setAddMarksMode(boolean addMarksMode) {  
		this.addMarksMode = addMarksMode;  
	}
	
	private boolean showRealmRadius;
	public boolean isShowRealmRadius() {
		return showRealmRadius;
	}
	public void setShowRealmRadius(boolean showRealmRadius) {
		this.showRealmRadius = showRealmRadius;
		
		if(showRealmRadius) {
			mapModel.addOverlay(realmRadiusCircle);
		} else {
			mapModel.getCircles().remove(realmRadiusCircle);
		}
	}

	private RealmsMarker selectedMarker;
	public RealmsMarker getSelectedMarker() {
		return selectedMarker;
	}

	public boolean getAllComplete() {
		boolean complete = true;
		for(Marker m: getMapModel().getMarkers()) {
			if((m instanceof RealmsMarker) && ! ((RealmsMarker)m).isComplete()) {
				complete = false;
			}
		}

		return complete;
	}

	public String saveRealm() {
		if(realm != null) {
			final Set<Mark> marks = new HashSet<Mark>();
			for(Marker m: mapModel.getMarkers()) {
				if(m instanceof RealmsMarker) {
					marks.add((Mark)m.getData());
				}
			}
			realm.setMarks(marks);

			realmDAO.save(realm);
		}

		return "/regular_user/my_realms.xhtml?faces-redirect=true";
	}

	public void onPointSelect(PointSelectEvent event) {  
		if(addMarksMode) {
			LatLng latlng = event.getLatLng();

			//		if(latlng.getLat() <= (realm.getLatitude() + realm.getRadius()/1000) && 
			//				latlng.getLat() >= (realm.getLatitude() - realm.getRadius()/1000) &&
			//				latlng.getLng() <= (realm.getLongitude() + realm.getRadius()/1000) &&
			//				latlng.getLng() >= (realm.getLongitude() - realm.getRadius()/1000)) {

			RealmsMarker marker = new RealmsMarker(new LatLng(latlng.getLat(), latlng.getLng()));
			mapModel.addOverlay(marker);
			mapModel.addOverlay(marker.getCircle());

			selectedMarker = marker;
			if(marker != null) {
				marker.setSelected(true);
				for(Marker m: mapModel.getMarkers()) {
					if(! m.getId().equals(marker.getId()) && (m instanceof RealmsMarker)) {
						((RealmsMarker)m).setSelected(false);
					}
				}
			}
			marker.setSelected(true);

			//		}
		}
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
				if(! m.getId().equals(marker.getId())  && (m instanceof RealmsMarker)) {
					((RealmsMarker)m).setSelected(false);
				}
			}

			selectedMarker = marker;
		}
	}  

	public class OptionBean {
		private String optionName;

		public String getOptionName() {
			return optionName;
		}
		public void setOptionName(String optionName) {
			this.optionName = optionName;
		}

		public void clean() {
			optionName = "";
		}

	}
}
