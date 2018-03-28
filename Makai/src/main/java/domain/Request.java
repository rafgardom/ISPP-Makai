
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Constructors ----------------------------------------------------------
	public Request() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String		description;
	private String		tags;
	private Category	category;
	private Boolean		isCancelled;


	@NotBlank
	@Size(max = 2000)
	@Column(name = "description", length = 2000)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
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

	@NotNull
	public Boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(final Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


	// Relationships ----------------------------------------------------------
	private Customer			customer;
	private Collection<Receipt>	receipts;
	private Animal				animal;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "request")
	public Collection<Receipt> getReceipts() {
		return this.receipts;
	}

	public void setReceipts(final Collection<Receipt> receipts) {
		this.receipts = receipts;
	}

	@Valid
	@OneToOne(optional = true)
	public Animal getAnimal() {
		return this.animal;
	}

	public void setAnimal(final Animal animal) {
		this.animal = animal;
	}

}
