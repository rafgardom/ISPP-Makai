
package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Animal;
import domain.Coordinates;
import domain.Specie;
import domain.Vehicle;

public class TravelForm {

	// Attributes 

	private int					id;
	private Coordinates			destination;
	private Coordinates			origin;
	private Date				startDate;
	private Date				startTime;
	private int					duration;
	private Integer				animalSeats;
	private Integer				humanSeats;
	private Vehicle				vehicle;
	private boolean				principalPassenger;
	private Collection<Animal>	animals;
	private Collection<Specie>	species;


	//Constructor

	public TravelForm() {
		super();
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}

	@Valid
	public Coordinates getDestination() {
		return this.destination;
	}
	public void setDestination(final Coordinates destination) {
		this.destination = destination;
	}

	@Valid
	public Coordinates getOrigin() {
		return this.origin;
	}
	public void setOrigin(final Coordinates origin) {
		this.origin = origin;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
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

	@NotNull
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public boolean isPrincipalPassenger() {
		return this.principalPassenger;
	}
	public void setPrincipalPassenger(final boolean principalPassenger) {
		this.principalPassenger = principalPassenger;
	}

	public Collection<Animal> getAnimals() {
		return this.animals;
	}
	public void setAnimals(final Collection<Animal> animals) {
		this.animals = animals;
	}

	@NotNull
	public int getDuration() {
		return this.duration;
	}
	public void setDuration(final int duration) {
		this.duration = duration;
	}

	public Collection<Specie> getSpecies() {
		return this.species;
	}
	public void setSpecies(final Collection<Specie> species) {
		this.species = species;
	}

}
