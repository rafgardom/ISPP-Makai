
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Animal;
import domain.Coordinates;
import domain.Duration;
import domain.Request;

public class OfferForm {

	// Attributes ------------------------------------------------------------- 

	private int			id;
	private Coordinates	destination;
	private Date		startMoment;
	private Double		price;
	private String		comment;
	private Duration	duration;
	private Animal		animal;
	private Request		request;


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

	@Valid
	public Coordinates getDestination() {
		return this.destination;
	}

	public void setDestination(final Coordinates destination) {
		this.destination = destination;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
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
	@Size(max = 2000)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Valid
	public Duration getDuration() {
		return this.duration;
	}

	public void setDuration(final Duration duration) {
		this.duration = duration;
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
