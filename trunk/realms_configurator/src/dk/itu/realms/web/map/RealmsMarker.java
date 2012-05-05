package dk.itu.realms.web.map;

import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import dk.itu.realms.model.entity.Mark;

public class RealmsMarker extends Marker {

	private static final String UNCOMPLETE_ICON 			= "/realms_configurator/faces/resources/images/marker_q.gif";
	private static final String UNCOMPLETE_SELECTED_ICON	= "/realms_configurator/faces/resources/images/marker_q_selected.gif";
	private static final String COMPLETE_ICON 				= "/realms_configurator/faces/resources/images/marker_done.png";
	private static final String COMPLETE_SELECTED_ICON 		= "/realms_configurator/faces/resources/images/marker_done_selected.png";
	
	private RealmCircle accuracyCircle;
	
	/**
	 * Constructor for a newly added marker 
	 */
	public RealmsMarker(LatLng latlng) {
		this(new Mark(latlng.getLat(), latlng.getLng()));
	}
	
	/**
	 * Constructor for an existing marker 
	 */
	public RealmsMarker(Mark data) {
		super(new LatLng(data.getLatitude(), data.getLongitude()));
		
		setData(data);
		setDraggable(true);
		
		accuracyCircle = new RealmCircle(getLatlng(), data.getRadius(), this);
		
		updateIcon();
	}

	public RealmCircle getCircle() {
		return accuracyCircle;
	}
	
	/* data manipulation */
	@Override
	public void setLatlng(LatLng latlng) {
		super.setLatlng(latlng);
		
		((Mark)getData()).setLatitude(latlng.getLat());
		((Mark)getData()).setLongitude(latlng.getLng());

		accuracyCircle.setCenter(latlng);
		
		updateIcon();
	}
	@Override
	public LatLng getLatlng() {
		return super.getLatlng();
	}
	
	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		
		((Mark)getData()).setMarkTitle(title);
		
		updateIcon();
	}
	@Override
	public String getTitle() {
		return ((Mark)getData()).getMarkTitle();
	}
	
	public void setDescription(String description) {
		((Mark)getData()).setMarkDescription(description);
		
		updateIcon();
	}
	public String getDescription() {
		return ((Mark)getData()).getMarkDescription();
	}
	
	public void setRadius(Double radius) {
		((Mark)getData()).setRadius(radius);
		
		accuracyCircle.setRadius(radius);
		
		updateIcon();
	}
	public Double getRadius() {
		return ((Mark)getData()).getRadius();
	}
	
	/* UI element manipulation */
	private boolean selected = false;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
		accuracyCircle.setSelected(selected);
		
		updateIcon();
	}
	
	public boolean isComplete() {
		if(getData() == null || ! (getData() instanceof Mark)) {
			return false;
		}
		
		Mark markData = (Mark)getData();
		
		return markData.getMarkTitle() != null && !markData.getMarkTitle().isEmpty() &&
			markData.getMarkDescription() != null && !markData.getMarkDescription().isEmpty() &&
			markData.getLatitude() != null && markData.getLongitude() != null;
	}
	
	private void updateIcon() {
		if(isComplete()) {
			if(isSelected()) {
				setIcon(COMPLETE_SELECTED_ICON);
			} else {
				setIcon(COMPLETE_ICON);
			}
		} else {
			if(isSelected()) {
				setIcon(UNCOMPLETE_SELECTED_ICON);
			} else {
				setIcon(UNCOMPLETE_ICON);
			}
		}
	}
}
