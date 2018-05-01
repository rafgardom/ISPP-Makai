<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<form:form action="${RequestURI}" modelAttribute="rating" enctype="multipart/form-data">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="request" />
	<form:hidden path="trainer" />
	<form:hidden path="moment" />
	<form:hidden path="travel" />
		
	<fieldset>
		<acme:textarea code="rating.comment" path="comment" />
		<br />
		
		<div class="col-sm-3">
			<form:label path="stars">
				<spring:message code="rating.stars" />
				<img src="images/asterisk.png"	width="16"/> 
			</form:label><br />
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
			<form:hidden path="stars" id="starsHidden" /><br />
			<form:errors path="stars" cssClass="alert alert-danger form-control" />
		</div>
		
		
	</fieldset>
	
	<br/>
	
	<jstl:if test="${errorMessage != null}">
		<spring:message code="${errorMessage}" var="error" />
		<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
	</jstl:if>
	<br/>
	
		<acme:submit code="rating.save" name="save" />
		
		<jstl:if test="${RequestURI == 'rating/customer/createRequest.do'}">
			<acme:cancel code="rating.cancel" url="offer/customer/list.do?requestId=${rating.request.id }" />
		</jstl:if>
		
		<jstl:if test="${RequestURI == 'rating/customer/createTravel.do'}">
			<acme:cancel code="rating.cancel" url="travel/myPastList.do" />
		</jstl:if>
	<br/>
	
</form:form>

<script type="text/javascript">
	$(':radio').change(function(){
		$('.star').removeClass('icon-star').addClass('icon-star-o');
		
		for(var i=1;i<=this.value;i++){
			$('#star'+i).removeClass('icon-star-o').addClass('icon-star');
		}
		
		$("#starsHidden").val(this.value);
	});
	
</script>
