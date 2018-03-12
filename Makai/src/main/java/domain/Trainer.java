
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
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
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
	private Collection<Receipt>	receipts;
	private Collection<Offer>	offers;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "trainer")
	public Collection<Receipt> getReceipts() {
		return this.receipts;
	}

	public void setReceipts(final Collection<Receipt> receipts) {
		this.receipts = receipts;
	}

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
