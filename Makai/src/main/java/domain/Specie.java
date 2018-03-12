
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Specie extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Specie() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	type;


	@NotBlank
	public String getType() {
		return this.type;
	}
	public void setType(final String type) {
		this.type = type;
	}

	// Relationships ----------------------------------------------------------

}
