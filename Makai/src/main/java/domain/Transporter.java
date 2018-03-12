
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Transporter extends Actor {

	// Constructors ----------------------------------------------------------
	public Transporter() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Double	avgRating;


	@NotNull
	@Min(0)
	public Double getAvgRating() {
		return this.avgRating;
	}

	public void setAvgRating(final Double avgRating) {
		this.avgRating = avgRating;
	}


	// Relationships ----------------------------------------------------------
	private Travel	travelPassenger;


	@Valid
	@ManyToOne(optional = true)
	public Travel getTravelPassenger() {
		return this.travelPassenger;
	}

	public void setTravelPassenger(final Travel travelPassenger) {
		this.travelPassenger = travelPassenger;
	}

}
