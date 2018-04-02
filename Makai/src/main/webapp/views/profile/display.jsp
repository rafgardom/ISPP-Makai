<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<div class="center-div">
	<img src="${pictureImage}" alt="<spring:message code='profile.no.picture' />" width="200px" height="200px"><br>
</div>
<br>
<div class="card-deck">

	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="profile.personal.info" /></h3>
	  	<div class="card-body"> 	
			<p><b><spring:message code="profile.name" />:</b> 
				<jstl:out value="${actor.name}" /></p>
		
	  	<security:authorize access="hasAnyRole('CUSTOMER', 'ADMIN', 'TRAINER')">
				<p><b><spring:message code="profile.surname" />:</b> 
					<jstl:out value="${actor.surname}" /></p>
	
				<p><b><spring:message code="profile.nid" />:</b> 
					<jstl:out value="${actor.nid}" /></p>
		</security:authorize>
	  	
	  		<p><b><spring:message code="profile.phone" />:</b> <jstl:out
					value="${actor.phone}" /></p>
					
			<p><b><spring:message code="profile.email" />:</b> <jstl:out
					value="${actor.email}" /></p>
	  	
	  	</div>
	</div>
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="profile.coordinates" /></h3>
	  	<div class="card-body"> 	
				<p><b><spring:message code="coordinates.country" />:</b> <jstl:out
				value="${actor.coordinates.country}" /></p>
		
				<p><b><spring:message code="coordinates.state" />:</b> <jstl:out
						value="${actor.coordinates.state}" /></p>
		
				<p><b><spring:message code="coordinates.province" />:</b> <jstl:out
						value="${actor.coordinates.province}" /></p>
		
				<p><b><spring:message code="coordinates.city" />:</b> <jstl:out
						value="${actor.coordinates.city}" /></p>
		
				<p><b><spring:message code="coordinates.zipCode" />:</b> <jstl:out
						value="${actor.coordinates.zip_code}" /></p>
	  	</div>
	</div>
</div>
<br>

<acme:link href="profile/edit.do" code="profile.edit" type="warning btn-block"/>


