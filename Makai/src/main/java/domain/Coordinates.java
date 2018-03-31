
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinates {

	// Constructors -----------------------------------------------------------
	public Coordinates() {
		super();
	}
	public Coordinates(final String country, final String state, final String province, final String city, final String zip_code) {
		super();
		this.country = country;
		this.state = state;
		this.province = province;
		this.city = city;
		this.zip_code = zip_code;
	}


	// Attributes -------------------------------------------------------------

	private String	country;
	private String	state;
	private String	province;
	private String	city;
	private String	zip_code;


	@NotBlank
	@Size(max = 100)
	@Pattern(regexp = "^[a-zñÑA-Z]+(?:[\\s-][a-zñÑA-Z]+)*$")
	public String getCountry() {
		return this.country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}

	@Size(max = 100)
	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}

	@Size(max = 100)
	public String getProvince() {
		return this.province;
	}
	public void setProvince(final String province) {
		this.province = province;
	}

	@NotBlank
	@Size(max = 100)
	@Pattern(regexp = "^[a-zñÑA-Z]+(?:[\\s-][a-zñÑA-Z]+)*$")
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

	@NotBlank
	@Pattern(regexp = "\\d{2,6}(\\w{1,4})?")
	public String getZip_code() {
		return this.zip_code;
	}
	public void setZip_code(final String zip_code) {
		this.zip_code = zip_code;
	}

}
