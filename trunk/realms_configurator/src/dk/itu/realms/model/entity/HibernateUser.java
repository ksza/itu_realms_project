package dk.itu.realms.model.entity;

import java.util.Set;

import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

@Named("userModel")
@Scope("request")

@Entity
@Table(name = "users", schema = "realms")
public class HibernateUser extends User {

	private Long id;

	private Set<Authorities> roles;

	public HibernateUser() { }

	public HibernateUser(User user) {
		setEmail(user.getEmail());
		setPassword(user.getPassword());
		setName(user.getName());
		setZipCode(user.getZipCode());
		setPhoneNo(user.getPhoneNo());
		setAddress(user.getAddress());
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

	@Column(name = "username", nullable = false, unique = true)
	public String getEmail() {
		return super.getEmail();
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return super.getPassword();
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return super.getName();
	}

	@Column(name = "address", nullable = false)
	public String getAddress() {
		return super.getAddress();
	}

	@Column(name = "zip", nullable = false)
	public String getZipCode() {
		return super.getZipCode();
	}

	@Column(name = "phone", nullable = true)
	public String getPhoneNo() {
		return super.getPhoneNo();
	}

	@Column(name = "enabled", nullable = false)
	public boolean getEnabled() {
		return super.getEnabled();
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Set<Authorities> getRoles() {
		return roles;
	}
	public void setRoles(Set<Authorities> roles) {
		this.roles = roles;
	}
	
}
