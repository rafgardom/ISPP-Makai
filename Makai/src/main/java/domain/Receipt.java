
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Receipt extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Receipt() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Date	moment;
	private Double	amount;
	private Double	commission;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Min(1)
	public Double getAmount() {
		return this.amount;
	}
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	@Min(0)
	public Double getCommission() {
		return this.commission;
	}
	public void setCommission(final Double commission) {
		this.commission = commission;
	}


	// Relationships ----------------------------------------------------------
	private Trainer	trainer;
	private Request	request;


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
	public Request getRequest() {
		return this.request;
	}
	public void setRequest(final Request request) {
		this.request = request;
	}

}
