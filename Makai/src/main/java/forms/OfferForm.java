
package forms;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Animal;
import domain.Request;

public class OfferForm {

	// Attributes ------------------------------------------------------------- 

	private int		id;
	private String	country;
	private String	state;
	private String	province;
	private String	city;
	private String	zip_code;
	private Date	startMoment;
	private Double	price;
	private String	comment;
	private Integer	year;
	private Integer	month;
	private Integer	week;
	private Integer	day;
	private Animal	animal;
	private Request	request;


	// Constructor ------------------------------------------------------------
	public OfferForm() {
		super();
	}

	// Getters & setters ------------------------------------------------------

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getZip_code() {
		return this.zip_code;
	}

	public void setZip_code(final String zip_code) {
		this.zip_code = zip_code;
	}

	@NotNull
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@Min(0)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Min(0)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	@Min(0)
	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(final Integer month) {
		this.month = month;
	}

	@Min(0)
	public Integer getWeek() {
		return this.week;
	}

	public void setWeek(final Integer week) {
		this.week = week;
	}

	@Min(0)
	public Integer getDay() {
		return this.day;
	}

	public void setDay(final Integer day) {
		this.day = day;
	}

	@NotNull
	public Animal getAnimal() {
		return this.animal;
	}

	public void setAnimal(final Animal animal) {
		this.animal = animal;
	}

	@NotNull
	public Request getRequest() {
		return this.request;
	}

	public void setRequest(final Request request) {
		this.request = request;
	}

}
