
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Travel extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Travel() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Coordinates	origin;
	private Coordinates	destination;
	private Date		startMoment;
	private Date		endMoment;
	private Integer		animalSeats;
	private Integer		humanSeats;


	@NotNull
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "country", column = @Column(name = "countryOrigin")), @AttributeOverride(name = "state", column = @Column(name = "stateOrigin")), @AttributeOverride(name = "province", column = @Column(name = "provinceOrigin")),
		@AttributeOverride(name = "city", column = @Column(name = "cityOrigin")), @AttributeOverride(name = "zip_code", column = @Column(name = "zip_codeOrigin")),
	})
	public Coordinates getOrigin() {
		return this.origin;
	}
	public void setOrigin(final Coordinates origin) {
		this.origin = origin;
	}

	@NotNull
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "country", column = @Column(name = "countryDestination")), @AttributeOverride(name = "state", column = @Column(name = "stateDestination")),
		@AttributeOverride(name = "province", column = @Column(name = "provinceDestination")), @AttributeOverride(name = "city", column = @Column(name = "cityDestination")),
		@AttributeOverride(name = "zip_code", column = @Column(name = "zip_codeDestination")),
	})
	public Coordinates getDestination() {
		return this.destination;
	}
	public void setDestination(final Coordinates destination) {
		this.destination = destination;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}
	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	@Min(0)
	public Integer getAnimalSeats() {
		return this.animalSeats;
	}
	public void setAnimalSeats(final Integer animalSeats) {
		this.animalSeats = animalSeats;
	}

	@Min(0)
	public Integer getHumanSeats() {
		return this.humanSeats;
	}
	public void setHumanSeats(final Integer humanSeats) {
		this.humanSeats = humanSeats;
	}


	// Relationships ----------------------------------------------------------
	private Transporter			transporterOwner;
	private Vehicle				vehicle;
	private Collection<Animal>	animals;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Transporter getTransporterOwner() {
		return this.transporterOwner;
	}
	public void setTransporterOwner(final Transporter transporterOwner) {
		this.transporterOwner = transporterOwner;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Animal> getAnimals() {
		return this.animals;
	}

	public void setAnimals(final Collection<Animal> animals) {
		this.animals = animals;
	}

}
