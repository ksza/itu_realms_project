package dk.itu.realms.client.funf.collectors;

import org.json.JSONException;
import org.json.JSONObject;

import dk.itu.realms.client.funf.persistence.PersistDataHelper;
import dk.itu.realms.client.funf.persistence.PersistedSensorData;

import edu.mit.media.funf.probe.Probe;

public class LocationProbeDataService extends AbstractProbeDataService {

	@Override
	protected void handleData(JSONObject dataJson) {
		try {
			if(dataJson.getString(Probe.PROBE).equals("edu.mit.media.funf.probe.builtin.LocationProbe")) {

				/* persist to database */
				final PersistedSensorData data = new PersistedSensorData();
				data.type = "LOCATION";
				data.value = dataJson.toString();
				
				PersistDataHelper.persistSensorData(this, data);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
