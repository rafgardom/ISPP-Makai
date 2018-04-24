
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Banner() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private byte[]	picture;
	private Integer	totalViews;
	private Integer	currentViews;
	private Double	price;
	private String	zone;
	private boolean	validated;
	private boolean	paid;
	private Date	paidMoment;
	private Integer	clicksNumber;


	@Lob
	@Column(length = 16777215)
	public byte[] getPicture() {
		return this.picture;
	}
	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}

	@Min(1)
	@Max(250000)
	public Integer getTotalViews() {
		return this.totalViews;
	}
	public void setTotalViews(final Integer totalViews) {
		this.totalViews = totalViews;
	}

	@Min(0)
	public Integer getCurrentViews() {
		return this.currentViews;
	}
	public void setCurrentViews(final Integer currentViews) {
		this.currentViews = currentViews;
	}

	@NotNull
	@Min(0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotBlank
	public String getZone() {
		return this.zone;
	}

	public void setZone(final String zone) {
		this.zone = zone;
	}

	@NotNull
	public boolean isPaid() {
		return this.paid;
	}

	public void setPaid(final boolean paid) {
		this.paid = paid;
	}

	@Past
	public Date getPaidMoment() {
		return this.paidMoment;
	}

	public void setPaidMoment(final Date paidMoment) {
		this.paidMoment = paidMoment;
	}

	@NotNull
	public boolean isValidated() {
		return this.validated;
	}

	public void setValidated(final boolean validated) {
		this.validated = validated;
	}

	@NotNull
	public Integer getClicksNumber() {
		return this.clicksNumber;
	}

	public void setClicksNumber(final Integer clicksNumber) {
		this.clicksNumber = clicksNumber;
	}


	//Relationships

	private Actor	actor;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
