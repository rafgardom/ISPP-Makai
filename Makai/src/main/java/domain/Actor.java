package domain;

import javax.persistence.Entity;
import javax.persistence.Access;
import javax.persistence.AccessType;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {
	
	// Constructors ----------------------------------------------------------
	public Actor() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	private String email;
	private String phone;

}
