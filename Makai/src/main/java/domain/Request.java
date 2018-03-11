package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity{
	// Constructors ----------------------------------------------------------
	public Request(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private String description;
	private String tags;
	private Category categorty;
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotBlank
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	@NotNull
	public Category getCategorty() {
		return categorty;
	}
	public void setCategorty(Category categorty) {
		this.categorty = categorty;
	}
	
	// Relationships ----------------------------------------------------------
	/**Completar**/
	private Customer customer;
	private Collection<Receipt> receipts;
	private Animal animal;
	
	

}
