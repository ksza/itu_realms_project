package dk.itu.realms.model.entity.reduced;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dk.itu.realms.model.entity.Realm;

/**
 * We need a wrapper class for realms to be able to marshal / unmarshal
 * 
 */

@XmlRootElement(name = "realms")
public class RealmList {

	private List<RealmReduced> realms;

	public RealmList() {
	}

	public RealmList(List<Realm> realms) {
		this.realms = new ArrayList<RealmReduced>();
		for (Realm r : realms) {
			this.realms.add(new RealmReduced(r));
		}
	}

	public void setRealms(List<RealmReduced> realms) {
		this.realms = realms;
	}

	@XmlElement(name = "realm")
	public List<RealmReduced> getRealms() {
		return realms;
	}
}
