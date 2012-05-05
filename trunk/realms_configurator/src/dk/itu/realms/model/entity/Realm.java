package dk.itu.realms.model.entity;

import java.util.Set;

import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Named("realmModel")
@Scope("request")

@Entity
@Table(name = "realm", schema = "realms")
public class Realm {

	private Long id;
	
	private String name;
	private String generalDescription;
	private String locationDecription;
	private Double latitude;
	private Double longitude;
	private Double radius;
	private Set<Mark> marks;
	
	private HibernateUser owner;
	
	public Realm() { }

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false)
	public String getGeneralDescription() {
		return generalDescription;
	}
	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}

	@Column(name = "location_description", nullable = true)
	public String getLocationDecription() {
		return locationDecription;
	}
	public void setLocationDecription(String locationDecription) {
		this.locationDecription = locationDecription;
	}

	@Column(name = "latitude", nullable = false)
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", nullable = false)
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "radius", nullable = false)
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}

	@OneToMany(targetEntity = Mark.class, cascade = CascadeType.ALL)
	public Set<Mark> getMarks() {
		return marks;
	}
	public void setMarks(Set<Mark> marks) {
		this.marks = marks;
	}
	
	@OneToOne(targetEntity = HibernateUser.class, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_realms", 
			joinColumns = { @JoinColumn(name = "realm_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "user_id") }
			)
	public HibernateUser getOwner() {
		return owner;
	}
	public void setOwner(HibernateUser owner) {
		this.owner = owner;
	}
}
