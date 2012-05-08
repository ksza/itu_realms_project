package dk.itu.realms.model.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * We need a wrapper class for realms to be able to marshal / unmarshal
 *
 */

@XmlRootElement(name="realms")
public class RealmList {
	
	private List<Realm> realms;
	
	public RealmList() {}
	
	public RealmList(List<Realm> realms) {
		this.realms = realms;
	}
	
	public void setRealms(List<Realm> realms) {
		this.realms = realms;
	}
	
	@XmlElement(name="realm")
	public List<Realm> getRealms() {
		return realms;
	}
}
