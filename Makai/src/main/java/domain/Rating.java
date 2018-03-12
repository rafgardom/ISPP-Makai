
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Rating extends DomainEntity {

	// Constructors ----------------------------------------------------------

	public Rating() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Integer	stars;
	private String	comment;
	private Date	moment;


	@NotNull
	@Range(min = 0, max = 10)
	public Integer getStars() {
		return this.stars;
	}
	public void setStars(final Integer stars) {
		this.stars = stars;
	}

	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Past
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------
	private Customer	customer;
	private Travel		travel;
	private Trainer		trainer;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Travel getTravel() {
		return this.travel;
	}
	public void setTravel(final Travel travel) {
		this.travel = travel;
	}

	@Valid
	@ManyToOne(optional = true)
	public Trainer getTrainer() {
		return this.trainer;
	}
	public void setTrainer(final Trainer trainer) {
		this.trainer = trainer;
	}

}
