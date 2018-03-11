package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends DomainEntity{
	
	// Constructors ----------------------------------------------------------
	public Offer(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private Coordinates destination;
	private Date startMoment;
	private Boolean isAccepted;
	private Double price;
	private String comment;
	private Duration duration;
	
	@NotNull
	public Coordinates getDestination() {
		return destination;
	}
	public void setDestination(Coordinates destination) {
		this.destination = destination;
	}
	
	@NotNull
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	
	@NotNull
	public Boolean getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	
	@Min(0)
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@NotNull
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	// Relationships ----------------------------------------------------------
	/**Completar**/
	private Request request;
	private Trainer trainer;
	private Animal animal;
	

}
