
package forms;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class BannerForm {

	// Attributes -------------------------------------------------------------

	private int				id;
	private byte[]			picture;
	private String			stringImage;
	private MultipartFile	bannerImage;
	private Integer			totalViews;
	private Integer			currentViews;
	private Double			price;
	private String			zone;


	// Constructor ------------------------------------------------------------

	public BannerForm() {
		super();
	}

	// Getters & setters ------------------------------------------------------

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}

	public String getStringImage() {
		return this.stringImage;
	}

	public void setStringImage(final String stringImage) {
		this.stringImage = stringImage;
	}

	@NotNull
	public MultipartFile getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(final MultipartFile bannerImage) {
		this.bannerImage = bannerImage;
	}

	public Integer getTotalViews() {
		return this.totalViews;
	}

	public void setTotalViews(final Integer totalViews) {
		this.totalViews = totalViews;
	}

	public Integer getCurrentViews() {
		return this.currentViews;
	}

	public void setCurrentViews(final Integer currentViews) {
		this.currentViews = currentViews;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public String getZone() {
		return this.zone;
	}

	public void setZone(final String zone) {
		this.zone = zone;
	}

}
