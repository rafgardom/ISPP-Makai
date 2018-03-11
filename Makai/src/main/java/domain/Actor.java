package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {
	
	// Constructors ----------------------------------------------------------
	public Actor() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	private String email;
	private String phone;
	private Coordinates coordinates;
	private Byte[] picture;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotBlank
	@Pattern(regexp = "(\\+\\d{1,3} )?(\\(\\d{1,3}\\) )?(\\w{4,})?")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotNull
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	@NotNull
	public Byte[] getPicture() {
		return picture;
	}
	public void setPicture(Byte[] picture) {
		this.picture = picture;
	}
	
	// Relationships ----------------------------------------------------------
	private UserAccount			userAccount;
	private Collection<Notification> notifications;

	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy = "actors")
	public Collection<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(Collection<Notification> notifications) {
		this.notifications = notifications;
	}
	
	
	
	

}
