
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Offer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Coordinates	destination;
	private Date		startMoment;
	private Boolean		isAccepted;
	private Double		price;
	private String		comment;
	private Duration	duration;
	private String		paymentId;


	@NotNull
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
	public Boolean getIsAccepted() {
		return this.isAccepted;
	}
	public void setIsAccepted(final Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	@Min(0)
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotBlank
	@Size(max = 2000)
	@Column(name = "comment", length = 2000)
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotNull
	public Duration getDuration() {
		return this.duration;
	}
	public void setDuration(final Duration duration) {
		this.duration = duration;
	}


	// Relationships ----------------------------------------------------------
	private Request	request;
	private Trainer	trainer;
	private Animal	animal;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Request getRequest() {
		return this.request;
	}

	public void setRequest(final Request request) {
		this.request = request;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trainer getTrainer() {
		return this.trainer;
	}

	public void setTrainer(final Trainer trainer) {
		this.trainer = trainer;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Animal getAnimal() {
		return this.animal;
	}

	public void setAnimal(final Animal animal) {
		this.animal = animal;
	}

}
