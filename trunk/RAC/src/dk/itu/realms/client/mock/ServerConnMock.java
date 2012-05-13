package dk.itu.realms.client.mock;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import dk.itu.realms.client.IServerComm;
import dk.itu.realms.client.model.Mark;
import dk.itu.realms.client.model.Option;
import dk.itu.realms.client.model.Realm;

public class ServerConnMock implements IServerComm {
	
	private static final String TAG = "SERVER_CONN_MOCK";

	private static final List<Mark> MOCK_MARKS = new ArrayList<Mark>();
	static {
		final List<Option> answerOptions = new ArrayList<Option>();
		answerOptions.add(new Option(1L, "Answer 1", false));
		answerOptions.add(new Option(2L, "Answer 2", false));
		answerOptions.add(new Option(3L, "Answer 3", false));
		answerOptions.add(new Option(4L, "Answer 4", false));
		answerOptions.add(new Option(5L, "Answer 5", true));
		
		final List<Option> ratingOptions = new ArrayList<Option>();
		ratingOptions.add(new Option(1L, "1", false));
		ratingOptions.add(new Option(2L, "2", false));
		ratingOptions.add(new Option(3L, "3", false));
		
		MOCK_MARKS.add(new Mark(1L, "Question 1", "just a marker", true, "What's <b>up</b>?", answerOptions));
		MOCK_MARKS.add(new Mark(1L, "Information 1 1", "just another marker", false, "A very informative <i>marker</i> about this place!", null));
	};
	
	private int nextMark;
	
	public ServerConnMock() {
		this.nextMark = -1;
	}
	
	private int computeNextMark() {
		this.nextMark++;
		
		if(nextMark >= MOCK_MARKS.size()) {
			nextMark = 0;
		}
		
		return nextMark;
	}
	
	@Override
	public List<Realm> getRealms(final Double latitude, final Double longitude) {
		final List<Realm> realms = new ArrayList<Realm>();
		
		realms.add(new Realm(1L, "Realm1", "description one", "location description one"));
		realms.add(new Realm(2L, "Realm2", "description two", "location description 2"));
		realms.add(new Realm(3L, "Realm3", "description three", "location description 3"));
		
		return realms;
	}

	@Override
	public Mark updateStatus(Long realmId, Double lat, Double lon, String userID) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return MOCK_MARKS.get(computeNextMark());		
	}

	@Override
	public void rateInfo(Long realmId, Long markID, String rating, String userID) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Log.i(TAG, "Rate Info: " + "realmID " + realmId + ", markID " + markID + ", rating " + rating + ", userID " + userID);
	}

	@Override
	public void markOption(Long realmID, Long markID, Long optionID, String userID) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Log.i(TAG, "Rate Info: " + "realmID " + realmID + ", markID " + markID + ", optionID " + optionID + ", userID " + userID);
	}
	
}
