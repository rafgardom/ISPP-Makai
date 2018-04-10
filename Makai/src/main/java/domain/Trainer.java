
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Trainer extends Actor {

	// Constructors ----------------------------------------------------------
	public Trainer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	surname;
	private String	nid;
	private Double	avgRating;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNid() {
		return this.nid;
	}
	public void setNid(final String nid) {
		this.nid = nid;
	}

	@Min(0)
	public Double getAvgRating() {
		return this.avgRating;
	}
	public void setAvgRating(final Double avgRating) {
		this.avgRating = avgRating;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Offer>	offers;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "trainer")
	public Collection<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(final Collection<Offer> offers) {
		this.offers = offers;
	}

}
