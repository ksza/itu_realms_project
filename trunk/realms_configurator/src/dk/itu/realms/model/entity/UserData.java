package dk.itu.realms.model.entity;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Named("userDataModel")
@Scope("request")

@Entity
@Table(name = "userdata", schema = "realms")
public class UserData {
	
	private Long id;
	private Long realmId;
	private Long markId;
	private String userId;
	private String data;
	
	public UserData() {}

	@Id
	@GeneratedValue
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="realm_id", nullable= false)
	public Long getRealmId() {
		return realmId;
	}
	public void setRealmId(Long realmId) {
		this.realmId = realmId;
	}

	@Column(name="mark_id", nullable= false)
	public Long getMarkId() {
		return markId;
	}
	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	@Column(name="user_id", nullable = false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="data")
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
