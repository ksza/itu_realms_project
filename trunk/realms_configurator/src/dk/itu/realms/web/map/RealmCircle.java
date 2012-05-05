package dk.itu.realms.web.map;

import org.primefaces.model.map.Circle;
import org.primefaces.model.map.LatLng;

public class RealmCircle extends Circle {

	private static final double DEFAULT_RADIUS 			= 500;
	private static final String DEFAULT_TROKE_COLOR 	= "#d93c3c";
	private static final String DEFAULT_FILL_COLOR 		= "#d93c3c";
	private static final double DEFAULT_OPACITY 		= 0.7;
	
	public RealmCircle(LatLng center, double radius) {
		super(center, radius);
	}

}
