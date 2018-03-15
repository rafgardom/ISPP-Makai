
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Actor() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String		name;
	private String		email;
	private String		phone;
	private Coordinates	coordinates;
	private byte[]		picture;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@Email
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "((\\+|00)\\d{2,4}(\\s)?)?\\d{9,13}")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotNull
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@NotNull
	@Lob
	@Column(length = 16777215)
	public byte[] getPicture() {
		return this.picture;
	}
	public void setPicture(final byte[] bs) {
		this.picture = bs;
	}


	// Relationships ----------------------------------------------------------
	private UserAccount	userAccount;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
