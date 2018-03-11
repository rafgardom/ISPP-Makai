package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Animal extends DomainEntity{
	
	// Constructors ----------------------------------------------------------
	public Animal(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private String name;
	private String chipNumber;
	private Integer age;
	private Sex sex;
	private Byte[] picture;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getChipNumber() {
		return chipNumber;
	}
	public void setChipNumber(String chipNumber) {
		this.chipNumber = chipNumber;
	}
	
	@Min(0)
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@NotNull
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	
	public Byte[] getPicture() {
		return picture;
	}
	public void setPicture(Byte[] picture) {
		this.picture = picture;
	}
	
	// Relationships ----------------------------------------------------------
	/**Completar**/
	private AnimalShelter animalShelter;
	private Customer customer;
	//private Collection<Breed> breeds;
	
	

}
