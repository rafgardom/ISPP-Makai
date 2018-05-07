
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
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
	private String	typeSpa;
	private String	typeEng;


	@Column(unique = true)
	@NotBlank
	public String getTypeSpa() {
		return this.typeSpa;
	}
	public void setTypeSpa(final String typeSpa) {
		this.typeSpa = typeSpa;
	}

	@Column(unique = true)
	@NotBlank
	public String getTypeEng() {
		return this.typeEng;
	}
	public void setTypeEng(final String typeEng) {
		this.typeEng = typeEng;
	}

	// Relationships ----------------------------------------------------------

}
