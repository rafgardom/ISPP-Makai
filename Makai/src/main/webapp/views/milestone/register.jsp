<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:if test="${successMessage != null}">
	<div class="alert alert-success"><spring:message code="${successMessage}" /></div>
</jstl:if>

<form:form action="${RequestURI}" modelAttribute="milestoneForm">
	<form:hidden path="id"/>
	<form:hidden path="realMoment"/>
	<form:hidden path="offerId"/>
	<br>
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
	<jstl:if test="${create }">
		<div class="col-md-5">
				<acme:textbox code="milestone.title" path="title" mandatory="true" />
				<br>
				<acme:textarea code="milestone.description" path="description" mandatory="true" rows="7" maxCharacters="500" maxlength="500"/>
		</div>
	</jstl:if>
		<div class="offset-md-1 col-md-5">
			<acme:input code="milestone.targetDate" path="targetDate" mandatory="true" image="calendar" placeholder="dd/MM/yyyy" id="datepicker"/>
			<acme:textarea code="milestone.comment" path="comment" mandatory="false" rows="5" maxCharacters="500" maxlength="500"/>
			<acme:textarea code="milestone.problem" path="problem" mandatory="false" maxCharacters="500" maxlength="500"/>
		</div>
	</div>		
		<jstl:if test="${create }">
			<div class="col-12 d-flex justify-content-center">
					<spring:message code="milestone.importance" />
					<div class="rating">
						<label>
							<input type="radio" name="stars" value="1" />
							<span class="icon-star-o"></span>
						</label>
						<label>
							<input type="radio" name="stars" value="2" />
							<span id="star1" class="star icon-star-o"></span>
							<span class="icon-star-o"></span>
						</label>
						<label>
							<input type="radio" name="stars" value="3" />
							<span class="icon-star-o"></span>
							<span id="star2" class="star icon-star-o"></span>
							<span class="icon-star-o"></span>   
						</label>
						<label>
							<input type="radio" name="stars" value="4" />
							<span class="icon-star-o"></span>
							<span class="icon-star-o"></span>
							<span id="star3" class="star icon-star-o"></span>
							<span class="icon-star-o"></span>
						</label>
						<label>
							<input type="radio" name="stars" value="5" />
							<span class="icon-star-o"></span>
							<span class="icon-star-o"></span>
							<span class="icon-star-o"></span>
							<span id="star4" class="star icon-star-o"></span>
							<span class="icon-star-o"></span>
						</label>
						<label>
							<input type="radio" name="stars" value="5" />
							<span class="icon-star-o"></span>
							<span class="icon-star-o"></span>
							<span class="icon-star-o"></span>
							<span class="icon-star-o"></span>
							<span id="star5" class="star icon-star-o"></span>
						</label>
						</div>
			
						
					<form:hidden path="importance" id="starsHidden" /><br />
					
	
				</div>
				<div class="offset-md-3 col-md-6">
						<form:errors path="importance" cssClass="alert alert-danger form-control" />
				</div>
			</jstl:if>	
			
	<br/>
	<br/>
	<acme:submit code="milestone.save" name="save" />
	<acme:cancel code="milestone.cancel" url="milestone/list.do?offerId=${offerId}" />
	
</form:form>

 <!-- Datepicker -->
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
<jstl:if test="${lang =='es'}">
	<script>
		$.datepicker.setDefaults($.datepicker.regional['es']);
	</script>
</jstl:if>

<script type="text/javascript">
	$(':radio').change(function(){
		$('.star').removeClass('icon-star').addClass('icon-star-o');
		
		for(var i=1;i<=this.value;i++){
			$('#star'+i).removeClass('icon-star-o').addClass('icon-star');
		}
		
		$("#starsHidden").val(this.value);
	});
	
	$( function() {
	    $( "#datepicker" ).datepicker({ 
	    	dateFormat: 'dd/mm/yy', 
	    	minDate: 0,
	    	firstDay: 1
	    	});
	  } );
	
</script>
