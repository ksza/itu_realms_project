package dk.itu.realms.model.entity;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Named("optionModel")
@Scope("request")

@Entity
@Table(name = "options", schema = "realms")
public class Option {
	
	private Long id;
	
	private String optionTitle;
	private String optionDescription;
	private Double weight;
	
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
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	@Column(name = "description", nullable = true)
	public String getOptionDescription() {
		return optionDescription;
	}
	public void setOptionDescription(String optionDescription) {
		this.optionDescription = optionDescription;
	}
	
	@Column(name = "weight", nullable = false)
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
