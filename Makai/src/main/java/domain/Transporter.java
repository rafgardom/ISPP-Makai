package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Transporter extends Actor{
	
	// Constructors ----------------------------------------------------------
	public Transporter(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Double avgRating;

	@NotNull
	@Min(0)
	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
	
	// Relationships ----------------------------------------------------------
	private Travel travelPassenger;
	

}
