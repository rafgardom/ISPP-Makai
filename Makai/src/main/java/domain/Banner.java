
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
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
	private String	email;
	private String	enterprise;
	private Double	price;


	@Lob
	@Column(length = 16777215)
	public byte[] getPicture() {
		return this.picture;
	}
	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}

	@Min(1)
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

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getEnterprise() {
		return this.enterprise;
	}

	public void setEnterprise(final String enterprise) {
		this.enterprise = enterprise;
	}

	@NotNull
	@Min(0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

}
