package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity{
	// Constructors ----------------------------------------------------------
	public Notification(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Date moment;
	private String reason;
	private String description;
	
	@NotNull
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<Actor> actors;

	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Actor> getActors() {
		return actors;
	}
	public void setActors(Collection<Actor> actors) {
		this.actors = actors;
	}
	
	

}
