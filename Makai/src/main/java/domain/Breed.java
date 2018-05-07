
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
	private String	nameSpa;
	private String	nameEng;


	@Column(unique = true)
	@NotBlank
	public String getNameSpa() {
		return this.nameSpa;
	}
	public void setNameSpa(final String nameSpa) {
		this.nameSpa = nameSpa;
	}

	@Column(unique = true)
	@NotBlank
	public String getNameEng() {
		return this.nameEng;
	}
	public void setNameEng(final String nameEng) {
		this.nameEng = nameEng;
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
