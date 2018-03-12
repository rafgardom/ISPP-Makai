
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Vehicle extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Vehicle() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Brand	brand;
	private Integer	seats;
	private CarType	carType;
	private String	accommodation;
	private Integer	year;
	private String	description;
	private Byte[]	picture;
	private String	color;


	@NotNull
	public Brand getBrand() {
		return this.brand;
	}
	public void setBrand(final Brand brand) {
		this.brand = brand;
	}

	@Min(0)
	public Integer getSeats() {
		return this.seats;
	}
	public void setSeats(final Integer seats) {
		this.seats = seats;
	}

	@NotNull
	public CarType getCarType() {
		return this.carType;
	}
	public void setCarType(final CarType carType) {
		this.carType = carType;
	}

	public String getAccommodation() {
		return this.accommodation;
	}
	public void setAccommodation(final String accommodation) {
		this.accommodation = accommodation;
	}

	@Min(0)
	public Integer getYear() {
		return this.year;
	}
	public void setYear(final Integer year) {
		this.year = year;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public Byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(final Byte[] picture) {
		this.picture = picture;
	}
	@NotBlank
	public String getColor() {
		return this.color;
	}
	public void setColor(final String color) {
		this.color = color;
	}


	// Relationships ----------------------------------------------------------
	private Transporter	transporter;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Transporter getTransporter() {
		return this.transporter;
	}
	public void setTransporter(final Transporter transporter) {
		this.transporter = transporter;
	}

}
