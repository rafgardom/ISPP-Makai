
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Vehicle;

public class TravelForm {

	// Attributes 

	private int		id;
	private String	countryOrigin;
	private String	stateOrigin;
	private String	provinceOrigin;
	private String	cityOrigin;
	private String	zip_codeOrigin;
	private String	countryDestination;
	private String	stateDestination;
	private String	provinceDestination;
	private String	cityDestination;
	private String	zip_codeDestination;
	private Date	startMoment;
	private Date	endMoment;
	private Integer	animalSeats;
	private Integer	humanSeats;
	private Vehicle	vehicle;


	//Constructor

	public TravelForm() {
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
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getCountryOrigin() {
		return this.countryOrigin;
	}
	public void setCountryOrigin(final String countryOrigin) {
		this.countryOrigin = countryOrigin;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getStateOrigin() {
		return this.stateOrigin;
	}
	public void setStateOrigin(final String stateOrigin) {
		this.stateOrigin = stateOrigin;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getProvinceOrigin() {
		return this.provinceOrigin;
	}
	public void setProvinceOrigin(final String provinceOrigin) {
		this.provinceOrigin = provinceOrigin;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getCityOrigin() {
		return this.cityOrigin;
	}
	public void setCityOrigin(final String cityOrigin) {
		this.cityOrigin = cityOrigin;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getZip_codeOrigin() {
		return this.zip_codeOrigin;
	}
	public void setZip_codeOrigin(final String zip_codeOrigin) {
		this.zip_codeOrigin = zip_codeOrigin;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getCountryDestination() {
		return this.countryDestination;
	}
	public void setCountryDestination(final String countryDestination) {
		this.countryDestination = countryDestination;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getStateDestination() {
		return this.stateDestination;
	}
	public void setStateDestination(final String stateDestination) {
		this.stateDestination = stateDestination;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getProvinceDestination() {
		return this.provinceDestination;
	}
	public void setProvinceDestination(final String provinceDestination) {
		this.provinceDestination = provinceDestination;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getCityDestination() {
		return this.cityDestination;
	}
	public void setCityDestination(final String cityDestination) {
		this.cityDestination = cityDestination;
	}

	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	@NotBlank
	public String getZip_codeDestination() {
		return this.zip_codeDestination;
	}
	public void setZip_codeDestination(final String zip_codeDestination) {
		this.zip_codeDestination = zip_codeDestination;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}
	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	@Min(0)
	public Integer getAnimalSeats() {
		return this.animalSeats;
	}
	public void setAnimalSeats(final Integer animalSeats) {
		this.animalSeats = animalSeats;
	}

	@Min(0)
	public Integer getHumanSeats() {
		return this.humanSeats;
	}
	public void setHumanSeats(final Integer humanSeats) {
		this.humanSeats = humanSeats;
	}

	public Vehicle getVehicle() {
		return this.vehicle;
	}
	public void setVehicle(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}
