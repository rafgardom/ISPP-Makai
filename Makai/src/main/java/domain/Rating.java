
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------
	private Customer	customer;
	private Travel		travel;
	private Request		request;


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
	@OneToOne(optional = true)
	public Request getRequest() {
		return this.request;
	}

	public void setRequest(final Request request) {
		this.request = request;
	}

}
