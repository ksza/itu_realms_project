package dk.itu.realms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities", schema = "realms")
public class Authorities {

	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String authority;

	public Authorities() { }
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "authority", nullable = false)
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(! (obj instanceof Authorities)) {
			return false;
		}
		
		Authorities a = (Authorities)obj;
		if(this.id != a.id) {
			return false;
		}
        if((this.username == null) ? (a.username != null) : ! this.username.equals(a.username)) {
            return false;
        }
        if((this.authority == null) ? (a.authority != null) : ! this.authority.equals(a.authority)) {
            return false;
        }
        
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 73 * hash + (this.username != null ? this.username.hashCode() : 0);
		hash = 73 * hash + (this.authority != null ? this.authority.hashCode() : 0);
		
		return hash;
	}
}
