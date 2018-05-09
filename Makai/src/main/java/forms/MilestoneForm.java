
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

public class MilestoneForm {

	//Attributes
	private int		id;
	private Date	targetDate;	//fecha estimada cumplimiento del hito
	private Date	realMoment;	// Fecha real cumplimiento del hito (autogeneradda por el sistema)
	private String	title;			// titulo del hito
	private String	comment;		// comentario del hito
	private String	problem;		// problemas que ha habido durante el hito
	private int		importance;	// importancia del hito
	private int		offerId;
	private String	description;	// descripcion del hito


	//Constructor

	public MilestoneForm() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getTargetDate() {
		return this.targetDate;
	}

	public void setTargetDate(final Date targetDate) {
		this.targetDate = targetDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getRealMoment() {
		return this.realMoment;
	}

	public void setRealMoment(final Date realMoment) {
		this.realMoment = realMoment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getProblem() {
		return this.problem;
	}

	public void setProblem(final String problem) {
		this.problem = problem;
	}

	public int getImportance() {
		return this.importance;
	}

	public void setImportance(final int importance) {
		this.importance = importance;
	}

	public int getOfferId() {
		return this.offerId;
	}

	public void setOfferId(final int offerId) {
		this.offerId = offerId;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
