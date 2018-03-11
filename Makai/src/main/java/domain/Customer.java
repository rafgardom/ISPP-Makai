package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Transporter{
	
	// Constructors ----------------------------------------------------------
	public Customer(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private String nid;
	private String surname;
	
	@NotBlank
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	

}
