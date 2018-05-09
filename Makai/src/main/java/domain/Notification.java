
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Notification extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Notification() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Date				moment;
	private String				reason;
	private String				description;
	private NotificationType	type;
	private Boolean				isRead;


	@NotNull
	public Boolean getIsRead() {
		return this.isRead;
	}

	public void setIsRead(final Boolean isRead) {
		this.isRead = isRead;
	}
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
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getReason() {
		return this.reason;
	}
	public void setReason(final String reason) {
		this.reason = reason;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public NotificationType getType() {
		return this.type;
	}

	public void setType(final NotificationType type) {
		this.type = type;
	}


	// Relationships ----------------------------------------------------------
	private Actor	actor;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
