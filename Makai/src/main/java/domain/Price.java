package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Price extends DomainEntity{
	
	// Constructors ----------------------------------------------------------
	public Price(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Double bannerPrice;
	private Double adoptionFee;
	private Double travelFee;
	
	@NotNull
	@Min(0)
	public Double getBannerPrice() {
		return bannerPrice;
	}
	public void setBannerPrice(Double bannerPrice) {
		this.bannerPrice = bannerPrice;
	}
	
	@NotNull
	@Min(0)
	public Double getAdoptionFee() {
		return adoptionFee;
	}
	public void setAdoptionFee(Double adoptionFee) {
		this.adoptionFee = adoptionFee;
	}
	
	@NotNull
	@Min(0)
	public Double getTravelFee() {
		return travelFee;
	}
	public void setTravelFee(Double travelFee) {
		this.travelFee = travelFee;
	}
	
	

}
