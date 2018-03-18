
package forms;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.web.multipart.MultipartFile;

import domain.Brand;
import domain.CarType;

public class VehicleForm {

	// Attributes 

	private int				id;
	private Brand			brand;
	private Integer			seats;
	private CarType			carType;
	private String			accommodation;
	private Integer			year;
	private String			description;
	private byte[]			picture;
	private String			color;
	private String			license;
	private MultipartFile	userImage;


	//Constructor

	public VehicleForm() {
		super();
	}

	//Getter & setter

	public int getId() {
		return this.id;
	}
	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
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

	public CarType getCarType() {
		return this.carType;
	}
	public void setCarType(final CarType carType) {
		this.carType = carType;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
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
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public byte[] getPicture() {
		return this.picture;
	}
	public void setPicture(final byte[] bs) {
		this.picture = bs;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getColor() {
		return this.color;
	}
	public void setColor(final String color) {
		this.color = color;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getLicense() {
		return this.license;
	}
	public void setLicense(final String license) {
		this.license = license;
	}

	public MultipartFile getUserImage() {
		return this.userImage;
	}

	public void setUserImage(final MultipartFile userImage) {
		this.userImage = userImage;
	}

}
