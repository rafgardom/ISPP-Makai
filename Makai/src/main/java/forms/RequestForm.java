
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Animal;
import domain.Category;

public class RequestForm {

	// Attributes -------------------------------------------------------------

	private int			id;
	private String		description;
	private String		tags;
	private Category	category;
	private Animal		animal;


	// Constructor ------------------------------------------------------------

	public RequestForm() {
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
	@Size(max = 2000)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Size(max = 100)
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	public String getTags() {
		return this.tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	@NotNull
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public Animal getAnimal() {
		return this.animal;
	}

	public void setAnimal(final Animal animal) {
		this.animal = animal;
	}

}
