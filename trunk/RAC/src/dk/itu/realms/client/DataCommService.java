package dk.itu.realms.client;

import dk.itu.realms.client.mock.ServerConnMock;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class DataCommService extends Service {
	private static final String TAG = "DATA_COMM_SERVICE";

	private IServerComm adapter = new RACServerConn();

	public class LocalBinder extends Binder {
		public DataCommService getService() {
			return DataCommService.this;
		}
	}
	private final IBinder mBinder = new LocalBinder();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		/* keep the service running indefinitely */
		return START_NOT_STICKY;
	}

	public synchronized IServerComm getServerConn() {
		return adapter;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
}
