
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Breed extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Breed() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	name;


	@Column(unique = true)
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}


	// Relationships ----------------------------------------------------------
	private Specie	specie;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Specie getSpecie() {
		return this.specie;
	}

	public void setSpecie(final Specie specie) {
		this.specie = specie;
	}

}
