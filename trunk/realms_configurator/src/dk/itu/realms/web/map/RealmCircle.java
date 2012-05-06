package dk.itu.realms.web.map;

import org.primefaces.model.map.Circle;
import org.primefaces.model.map.LatLng;

public class RealmCircle extends Circle {

	private static final String STROKE_COLOR 	= "#ff8c00";
	private static final String FILL_COLOR 		= "#ff7f50";
	private static final double OPACITY 		= 0.2;
	
	private static final String STROKE_COLOR_SELECTED 		= "#228b22";
	private static final String FILL_COLOR_SELECTED 		= "#7cfc00";
	
	private RealmsMarker parent;
	
	public RealmCircle(LatLng center, double radius, RealmsMarker parent) {
		super(center, radius);
		
		setStrokeColor(STROKE_COLOR);
		setFillColor(FILL_COLOR);
		setFillOpacity(OPACITY);
		
		this.parent = parent;
	}

	public RealmsMarker getParent() {
		return this.parent;
	}
	
	public void setSelected(boolean selected) {
		if(! selected) {
			setStrokeColor(STROKE_COLOR);
			setFillColor(FILL_COLOR);
		} else {
			setStrokeColor(STROKE_COLOR_SELECTED);
			setFillColor(FILL_COLOR_SELECTED);
		}
	}
}
