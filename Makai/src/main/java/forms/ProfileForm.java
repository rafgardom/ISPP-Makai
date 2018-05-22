
package forms;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import domain.Coordinates;

public class ProfileForm {

	// Attributes -------------------------------------------------------------

	private int				id;
	private String			name;
	private String			email;
	private String			phone;
	private Coordinates		coordinates;
	private byte[]			picture;
	private String			nid;
	private String			surname;
	private String			password;
	private String			repeatPassword;
	private MultipartFile	userImage;
	private String			certifyingCompany;
	private String			stringImage;


	// Constructor ------------------------------------------------------------

	public ProfileForm() {
		super();
	}

	// Getters & setters ------------------------------------------------------

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
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

	public String getNid() {
		return this.nid;
	}

	public void setNid(final String nid) {
		this.nid = nid;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	public void setRepeatPassword(final String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public MultipartFile getUserImage() {
		return this.userImage;
	}

	public void setUserImage(final MultipartFile userImage) {
		this.userImage = userImage;
	}

	public String getCertifyingCompany() {
		return this.certifyingCompany;
	}

	public void setCertifyingCompany(final String certifyingCompany) {
		this.certifyingCompany = certifyingCompany;
	}

	public String getStringImage() {
		return this.stringImage;
	}

	public void setStringImage(final String stringImage) {
		this.stringImage = stringImage;
	}

}
