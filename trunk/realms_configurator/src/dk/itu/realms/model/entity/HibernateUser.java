package dk.itu.realms.model.entity;

import java.util.Set;

import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;

@Named("userModel")
@Scope("request")

@Entity
@Table(name = "users", schema = "realms")
public class HibernateUser {

	private Long id;

	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String name;

	private boolean enabled;
	
	private Set<Authorities> roles;

	public HibernateUser() { }

	public HibernateUser(HibernateUser user) {
		setEmail(user.getEmail());
		setPassword(user.getPassword());
		setName(user.getName());
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
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	public Set<Authorities> getRoles() {
		return roles;
	}
	public void setRoles(Set<Authorities> roles) {
		this.roles = roles;
	}

}
