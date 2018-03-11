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
public class Vehicle extends DomainEntity{
	
	// Constructors ----------------------------------------------------------
	public Vehicle(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Brand brand;
	private Integer seats;
	private CarType carType;
	private String accommodation;
	private Integer year;
	private String description;
	private Byte picture;
	private String color;
	
	@NotNull
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@Min(0)
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	
	@NotNull
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	
	
	public String getAccommodation() {
		return accommodation;
	}
	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}
	
	@Min(0)
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Byte getPicture() {
		return picture;
	}
	public void setPicture(Byte picture) {
		this.picture = picture;
	}
	
	@NotBlank
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	// Relationships ----------------------------------------------------------
	private Transporter transporter;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	
	
	
	

}
