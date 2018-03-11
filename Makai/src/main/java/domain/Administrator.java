package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor{
	
	// Constructors ----------------------------------------------------------
	public Administrator(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String surname;
	private String nid;
	
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotBlank
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	
	

}
