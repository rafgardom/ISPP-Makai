
package forms;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.web.multipart.MultipartFile;

import security.UserAccount;
import domain.Coordinates;

public class AdvertisingForm {

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
	private String			username;
	private MultipartFile	userImage;
	private UserAccount		userAccount;


	//Constructor

	public AdvertisingForm() {
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
	@NotNull
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
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
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
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public MultipartFile getUserImage() {
		return this.userImage;
	}

	public void setUserImage(final MultipartFile userImage) {
		this.userImage = userImage;
	}

	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}
