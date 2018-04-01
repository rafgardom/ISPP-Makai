
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Embeddable
@Access(AccessType.PROPERTY)
public class Duration {

	// Constructors -----------------------------------------------------------
	public Duration() {
		super();
	}

	public Duration(final Integer year, final Integer month, final Integer day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}


	// Attributes -------------------------------------------------------------

	private Integer	year;
	private Integer	month;
	private Integer	day;


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
	public Integer getDay() {
		return this.day;
	}
	public void setDay(final Integer day) {
		this.day = day;
	}

}
