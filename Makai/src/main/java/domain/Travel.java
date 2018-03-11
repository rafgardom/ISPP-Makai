package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Travel  extends DomainEntity {
	// Constructors ----------------------------------------------------------
	public Travel(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Coordinates origin;
	private Coordinates destination;
	private Date startMoment;
	private Date endMoment;
	private Integer animalSeats;
	private Integer humanSeats;
	
	@NotNull
	public Coordinates getOrigin() {
		return origin;
	}
	public void setOrigin(Coordinates origin) {
		this.origin = origin;
	}
	
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
	public Date getEndMoment() {
		return endMoment;
	}
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	
	@Min(0)
	public Integer getAnimalSeats() {
		return animalSeats;
	}
	public void setAnimalSeats(Integer animalSeats) {
		this.animalSeats = animalSeats;
	}
	
	@Min(0)
	public Integer getHumanSeats() {
		return humanSeats;
	}
	public void setHumanSeats(Integer humanSeats) {
		this.humanSeats = humanSeats;
	}
	
	// Relationships ----------------------------------------------------------
	private Transporter transporterOwner;
	private Vehicle vehicle;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Transporter getTransporterOwner() {
		return transporterOwner;
	}
	public void setTransporterOwner(Transporter transporterOwner) {
		this.transporterOwner = transporterOwner;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	

}
