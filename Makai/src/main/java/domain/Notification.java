
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Notification() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Date	moment;
	private String	reason;
	private String	description;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getReason() {
		return this.reason;
	}
	public void setReason(final String reason) {
		this.reason = reason;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Actor>	actors;


	@Valid
	@NotNull
	@ManyToMany()
	public Collection<Actor> getActors() {
		return this.actors;
	}
	public void setActors(final Collection<Actor> actors) {
		this.actors = actors;
	}

}
