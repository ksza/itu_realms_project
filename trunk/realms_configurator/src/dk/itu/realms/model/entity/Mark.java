package dk.itu.realms.model.entity;

import java.util.List;

import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Named("markModel")
@Scope("request")

@Entity
@Table(name = "marks", schema = "realms")
public class Mark {
	
	private static final Double DEFAULT_RADIUS = 500d;

	private Long id;
	
	private String markTitle;
	private String markDescription;
	private Double latitude;
	private Double longitude;
	private Double radius; // accuracy
	
	private List<Option> options;

	public Mark() { }
	
	public Mark(Double lat, Double lng) {
		this.latitude = lat;
		this.longitude = lng;
		
		radius = DEFAULT_RADIUS;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false)
	public String getMarkTitle() {
		return markTitle;
	}
	public void setMarkTitle(String markTitle) {
		this.markTitle = markTitle;
	}

	@Column(name = "description", nullable = false)
	public String getMarkDescription() {
		return markDescription;
	}
	public void setMarkDescription(String markDescription) {
		this.markDescription = markDescription;
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

	@OneToMany(targetEntity = Option.class, cascade = CascadeType.ALL)
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	
}
