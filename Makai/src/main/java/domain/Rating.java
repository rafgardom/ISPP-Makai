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
public class Rating extends DomainEntity{
	// Constructors ----------------------------------------------------------
	
	public Rating(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Integer stars;
	private String comment;
	private Date moment;
	
	@NotNull
	@Range(min = 0, max = 10)
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Past
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	// Relationships ----------------------------------------------------------
	private Customer customer;
	private Travel travel;
	private Trainer trainer;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Travel getTravel() {
		return travel;
	}
	public void setTravel(Travel travel) {
		this.travel = travel;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	
	
	
	

}
