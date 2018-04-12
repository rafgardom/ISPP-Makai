
package forms;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.web.multipart.MultipartFile;

import domain.Breed;
import domain.Sex;
import domain.Specie;

public class AnimalForm {

	// Attributes -------------------------------------------------------------

	private int					id;
	private String				name;
	private String				chipNumber;
	private Integer				age;
	private Sex					sex;
	private byte[]				picture;
	private MultipartFile		animalImage;
	private String				stringImage;
	private Specie				specie;
	private Collection<Breed>	breeds;
	private Boolean				isHidden;


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

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getChipNumber() {
		return this.chipNumber;
	}

	public void setChipNumber(final String chipNumber) {
		this.chipNumber = chipNumber;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	@Valid
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

	@NotNull
	public MultipartFile getAnimalImage() {
		return this.animalImage;
	}

	public void setAnimalImage(final MultipartFile animalImage) {
		this.animalImage = animalImage;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getStringImage() {
		return this.stringImage;
	}

	public void setStringImage(final String stringImage) {
		this.stringImage = stringImage;
	}

	@NotNull
	public Specie getSpecie() {
		return this.specie;
	}

	public void setSpecie(final Specie specie) {
		this.specie = specie;
	}

	public Collection<Breed> getBreeds() {
		return this.breeds;
	}

	public void setBreeds(final Collection<Breed> breeds) {
		this.breeds = breeds;
	}

	public Boolean getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(final Boolean isHidden) {
		this.isHidden = isHidden;
	}

}
