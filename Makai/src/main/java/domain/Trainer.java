package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Trainer extends Actor{
	
	// Constructors ----------------------------------------------------------
	public Trainer(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private String surname;
	private String nid;
	private Double avgRating;
	
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotBlank
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	
	@Min(0)
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<Receipt> receipts;
	private Collection<Offer> offers;
	
	

}
