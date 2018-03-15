
package forms;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import domain.Coordinates;

public class AnimalShelterForm {

	// Attributes 

	private int				id;
	private String			name;
	private String			email;
	private String			phone;
	private Coordinates		coordinates;
	private byte[]			picture;
	private String			password;
	private String			repeatPassword;
	private boolean			acceptCondition;
	private String			userName;
	private MultipartFile	userImage;


	//Constructor

	public AnimalShelterForm() {
		super();
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@Valid
	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}

	@NotBlank
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	@AssertTrue
	public boolean isAcceptCondition() {
		return this.acceptCondition;
	}

	public void setAcceptCondition(final boolean acceptCondition) {
		this.acceptCondition = acceptCondition;
	}

	@Column(unique = true)
	@NotBlank
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public MultipartFile getUserImage() {
		return this.userImage;
	}

	public void setUserImage(final MultipartFile userImage) {
		this.userImage = userImage;
	}

}
