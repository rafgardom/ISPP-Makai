
package forms;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import domain.Breed;
import domain.Sex;

public class AnimalForm {

	// Attributes -------------------------------------------------------------

	private int					id;
	private String				name;
	private String				chipNumber;
	private Integer				age;
	private Sex					sex;
	private byte[]				picture;
	private MultipartFile		animalImage;
	private Collection<Breed>	breeds;


	// Constructor ------------------------------------------------------------

	public AnimalForm() {
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
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getChipNumber() {
		return this.chipNumber;
	}

	public void setChipNumber(final String chipNumber) {
		this.chipNumber = chipNumber;
	}

	@Min(0)
	public Integer getAge() {
		return this.age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(final Sex sex) {
		this.sex = sex;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}

	public MultipartFile getAnimalImage() {
		return this.animalImage;
	}

	public void setAnimalImage(final MultipartFile animalImage) {
		this.animalImage = animalImage;
	}

	@Valid
	public Collection<Breed> getBreeds() {
		return this.breeds;
	}

	public void setBreeds(final Collection<Breed> breeds) {
		this.breeds = breeds;
	}

}
