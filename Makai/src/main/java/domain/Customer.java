
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Transporter {

	// Constructors ----------------------------------------------------------
	public Customer() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private String	nid;
	private String	surname;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNid() {
		return this.nid;
	}
	public void setNid(final String nid) {
		this.nid = nid;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

}
