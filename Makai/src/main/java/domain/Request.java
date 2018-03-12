
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	private Category	categorty;


	@NotBlank
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
	public Category getCategorty() {
		return this.categorty;
	}
	public void setCategorty(final Category categorty) {
		this.categorty = categorty;
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
