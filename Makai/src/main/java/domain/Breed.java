
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
	private String	breed;


	@NotBlank
	public String getBreed() {
		return this.breed;
	}
	public void setBreed(final String breed) {
		this.breed = breed;
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
