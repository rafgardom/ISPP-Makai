
package forms;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import utilities.Utilities;
import domain.Actor;
import domain.Banner;

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
	private boolean			paid;
	private boolean			validated;
	private Actor			actor;
	private double			dailyViews;
	private double			monthlyViews;
	private Integer			clicksNumber;
	private String			url;


	// Constructor ------------------------------------------------------------

	public BannerForm() {
		super();
	}

	public BannerForm(final Banner banner) {
		this.id = banner.getId();
		this.picture = banner.getPicture();
		this.stringImage = Utilities.showImage(banner.getPicture());
		this.totalViews = banner.getTotalViews();
		this.currentViews = banner.getCurrentViews();
		this.price = banner.getPrice();
		this.zone = banner.getZone();
		this.paid = banner.isPaid();
		this.validated = banner.isValidated();
		this.actor = banner.getActor();
		this.clicksNumber = banner.getClicksNumber();
	}

	public BannerForm(final Banner banner, double dailyViews, double monthlyViews) {
		double aux;

		if (dailyViews < 0)
			dailyViews = 0.0;
		else {
			aux = 1.0 * Math.round(dailyViews * 100);
			dailyViews = aux / 100;
		}

		if (monthlyViews < 0)
			monthlyViews = 0.0;
		else {
			aux = 1.0 * Math.round(monthlyViews * 100);
			monthlyViews = aux / 100;
		}

		this.id = banner.getId();
		this.picture = banner.getPicture();
		this.stringImage = Utilities.showImage(banner.getPicture());
		this.totalViews = banner.getTotalViews();
		this.currentViews = banner.getCurrentViews();
		this.price = banner.getPrice();
		this.zone = banner.getZone();
		this.paid = banner.isPaid();
		this.validated = banner.isValidated();
		this.actor = banner.getActor();
		this.clicksNumber = banner.getClicksNumber();
		this.dailyViews = dailyViews;
		this.monthlyViews = monthlyViews;
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

	public boolean isPaid() {
		return this.paid;
	}

	public void setPaid(final boolean paid) {
		this.paid = paid;
	}

	public boolean isValidated() {
		return this.validated;
	}

	public void setValidated(final boolean validated) {
		this.validated = validated;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	public double getDailyViews() {
		return this.dailyViews;
	}

	public void setDailyViews(final double dailyViews) {
		this.dailyViews = dailyViews;
	}

	public double getMonthlyViews() {
		return this.monthlyViews;
	}

	public void setMonthlyViews(final double monthlyViews) {
		this.monthlyViews = monthlyViews;
	}

	public Integer getClicksNumber() {
		return this.clicksNumber;
	}

	public void setClicksNumber(final Integer clicksNumber) {
		this.clicksNumber = clicksNumber;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
