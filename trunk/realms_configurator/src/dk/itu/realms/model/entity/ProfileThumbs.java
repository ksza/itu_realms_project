package dk.itu.realms.model.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "profile_thumbs", schema = "realms")
public class ProfileThumbs {
	
	private Long id;
	private Blob pic;
	
	public ProfileThumbs() { }
	
	public ProfileThumbs(final Blob blob) {
		this.pic = blob;
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
	
	@Column(name = "pic")
	@Lob
	public Blob getPic() {
		return pic;
	}
	public void setPic(Blob pic) {
		this.pic = pic;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(! (obj instanceof ProfileThumbs)) {
			return false;
		}
		
		ProfileThumbs a = (ProfileThumbs)obj;
		if(this.id != a.id) {
			return false;
		}
        if((this.pic == null) ? (a.pic != null) : ! this.pic.equals(a.pic)) {
            return false;
        }
        
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 73 * hash + (this.pic != null ? this.pic.hashCode() : 0);
		
		return hash;
	}
}
