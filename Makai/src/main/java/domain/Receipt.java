package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Receipt extends DomainEntity{
	
	// Constructors ----------------------------------------------------------
	public Receipt(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private Date moment;
	private Double amount;
	private Double commission;
	
	@NotNull
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@Min(1)
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Min(0)
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
	// Relationships ----------------------------------------------------------
	private Trainer trainer;
	private Request request;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	
	
	

}
