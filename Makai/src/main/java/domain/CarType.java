package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class CarType extends DomainEntity{
	// Constructors ----------------------------------------------------------
	public CarType(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String utilityCar;
	private String sedan;
	private String van;
	private String truck;
	private String mini_van;
	private String normal_car;
	
	
	public String getUtilityCar() {
		return utilityCar;
	}
	public void setUtilityCar(String utilityCar) {
		this.utilityCar = utilityCar;
	}
	public String getSedan() {
		return sedan;
	}
	public void setSedan(String sedan) {
		this.sedan = sedan;
	}
	public String getVan() {
		return van;
	}
	public void setVan(String van) {
		this.van = van;
	}
	public String getTruck() {
		return truck;
	}
	public void setTruck(String truck) {
		this.truck = truck;
	}
	public String getMini_van() {
		return mini_van;
	}
	public void setMini_van(String mini_van) {
		this.mini_van = mini_van;
	}
	public String getNormal_car() {
		return normal_car;
	}
	public void setNormal_car(String normal_car) {
		this.normal_car = normal_car;
	}
	
	

}
