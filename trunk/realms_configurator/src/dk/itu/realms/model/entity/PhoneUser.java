package dk.itu.realms.model.entity;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.context.annotation.Scope;

@Named("phoneuserModel")
@Scope("request")

@Entity
@Table(name = "phoneusers", schema = "realms")
public class PhoneUser {
	
	
	private long id;
	private String username;
	private String password;
	private String fullname;
	
	public PhoneUser() {};
	
	public PhoneUser(String username, String password, String fullname) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String userName) {
		this.username = userName;
	}
	
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password; 
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "fullname", nullable = false)
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullName) {
		this.fullname = fullName;
	}

}
