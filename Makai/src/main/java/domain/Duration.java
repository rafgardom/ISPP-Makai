package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

@Embeddable
@Access(AccessType.PROPERTY)
public class Duration {
	// Constructors -----------------------------------------------------------
	public Duration(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Integer year;
	private Integer month;
	private Integer week;
	private Integer day;
	
	@Min(0)
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Min(0)
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Min(0)
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	
	@Min(0)
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	
	

}
