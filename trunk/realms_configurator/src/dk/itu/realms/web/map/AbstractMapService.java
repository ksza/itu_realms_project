package dk.itu.realms.web.map;

import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

public abstract class AbstractMapService {

	protected MapModel mapModel;
	protected MapParams mapParams;

	public void init() {
		mapParams = new MapParams();
		mapModel = new DefaultMapModel();
	}

	public MapParams getMapParams() {
		return mapParams;
	}
	public MapModel getMapModel() {
		return mapModel;
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
