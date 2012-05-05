package dk.itu.realms.web.map;

import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import dk.itu.realms.model.entity.Mark;

public class RealmsMarker extends Marker {

	private static final String UNCOMPLETE_ICON 			= "/realms_configurator/faces/resources/images/marker_q.gif";
	private static final String UNCOMPLETE_SELECTED_ICON	= "/realms_configurator/faces/resources/images/marker_q_selected.gif";
	private static final String COMPLETE_ICON 				= "/realms_configurator/faces/resources/images/marker_done.png";
	private static final String COMPLETE_SELECTED_ICON 		= "/realms_configurator/faces/resources/images/marker_done_selected.png";
	
	public RealmsMarker(LatLng latlng) {
		this(latlng, new Mark());
	}
	
	public RealmsMarker(LatLng latlng, Mark data) {
		super(latlng);
		
		setData(data);
		setDraggable(true);
		
		setIcon(UNCOMPLETE_ICON);
	}

	private boolean selected = false;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isComplete() {
//		if(getData() == null || ! (getData() instanceof Mark)) {
//			return false;
//		}
//		
//		Mark markData = (Mark)getData();
//		
//		return markData.getMarkTitle() != null && !markData.getMarkTitle().isEmpty() &&
//			markData.getMarkDescription() != null && !markData.getMarkDescription().isEmpty() &&
//			markData.getLatitude() != null && markData.getLongitude() != null;
		
		return true;
	}
	
	public void updateIcon() {
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
