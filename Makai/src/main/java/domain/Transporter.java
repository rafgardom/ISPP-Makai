
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
	private Collection<Travel>	travelPassengers;


	@Valid
	@ManyToMany()
	public Collection<Travel> getTravelPassengers() {
		return this.travelPassengers;
	}

	public void setTravelPassengers(final Collection<Travel> travelPassengers) {
		this.travelPassengers = travelPassengers;
	}

}
