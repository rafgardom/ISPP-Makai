
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Price extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Price() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Double	bannerPrice;
	private Double	adoptionFee;
	private Double	travelFee;


	@NotNull
	@Min(0)
	public Double getBannerPrice() {
		return this.bannerPrice;
	}
	public void setBannerPrice(final Double bannerPrice) {
		this.bannerPrice = bannerPrice;
	}

	@NotNull
	@Min(0)
	public Double getAdoptionFee() {
		return this.adoptionFee;
	}
	public void setAdoptionFee(final Double adoptionFee) {
		this.adoptionFee = adoptionFee;
	}

	@NotNull
	@Min(0)
	public Double getTravelFee() {
		return this.travelFee;
	}
	public void setTravelFee(final Double travelFee) {
		this.travelFee = travelFee;
	}

}
