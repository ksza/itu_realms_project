package dk.itu.realms.client.funf.collectors;

import org.json.JSONException;
import org.json.JSONObject;

import edu.mit.media.funf.probe.Probe;

public class AccelerometerProbeDataService extends AbstractProbeDataService {

	@Override
	protected void handleData(JSONObject dataJson) {
		try {
			if(dataJson.getString(Probe.PROBE).equals("edu.mit.media.funf.probe.builtin.AccelerometerSensorProbe")) {

				/* compute avg. accuracy */
				final double accuracyAvg = computeIntArrayAvg(dataJson.optJSONArray("ACCURACY"));

				/* compute avg. values for each of the axis */
				final double avgX = computeDoubleArrayAvg(dataJson.optJSONArray("X"));
				final double avgY = computeDoubleArrayAvg(dataJson.optJSONArray("Y"));
				final double avgZ = computeDoubleArrayAvg(dataJson.optJSONArray("Z"));

				JSONObject accel = new JSONObject();

				final JSONObject axisData = new JSONObject();
				axisData.put("x", avgX);
				axisData.put("y", avgY);
				axisData.put("z", avgZ);

				accel.put("data", axisData);
				accel.put("accuracy", accuracyAvg);
				accel.put("number_of_readings", dataJson.optJSONArray("ACCURACY").length());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

}
