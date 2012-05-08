package dk.itu.realms.services.util;

import org.primefaces.model.map.LatLng;

import dk.itu.realms.model.entity.Realm;

public class LatLonCalculator {
	
	private static int EARTH_RADIUS = 6371; //In kilometers
	
	public boolean insideRealm(LatLng position, Realm realm) {
		double dLat = Math.toRadians(realm.getLatitude() - position.getLat());
		double dLon = Math.toRadians(realm.getLongitude() - position.getLng());
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(realm.getLatitude()) * Math.cos(position.getLng());
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return c * EARTH_RADIUS * 1000 <= realm.getRadius();
			
	}
}
