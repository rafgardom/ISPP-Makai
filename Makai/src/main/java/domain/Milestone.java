
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Milestone extends DomainEntity {

	//Attributes
	private Date	targetDate;	//fecha estimada cumplimiento del hito
	private Date	realMoment;	// Fecha real cumplimiento del hito (autogeneradda por el sistema)
	private String	title;			// titulo del hito
	private String	comment;		// comentario del hito
	private String	problem;		// problemas que ha habido durante el hito
	private int		importance;	// importancia del hito
	private String	description;	// descripcion del hito


	//Constructor
	public Milestone() {
		super();
	}

	//Getter & setter

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTargetDate() {
		return this.targetDate;
	}

	public void setTargetDate(final Date targetDate) {
		this.targetDate = targetDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRealMoment() {
		return this.realMoment;
	}

	public void setRealMoment(final Date realMoment) {
		this.realMoment = realMoment;
	}

	@NotBlank
	@Size(max = 100)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Size(max = 500)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Size(max = 500)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getProblem() {
		return this.problem;
	}

	public void setProblem(final String problem) {
		this.problem = problem;
	}

	@Range(min = 1, max = 5)
	public int getImportance() {
		return this.importance;
	}

	public void setImportance(final int importance) {
		this.importance = importance;
	}

	@NotBlank
	@Size(max = 500)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	//RelationShips

	private Offer	offer;


	@Valid
	@ManyToOne(optional = false)
	public Offer getOffer() {
		return this.offer;
	}

	public void setOffer(final Offer offer) {
		this.offer = offer;
	}

}
