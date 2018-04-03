<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authentication var="principalUserAccount" property="principal" />
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
		
		<jstl:if test="${ratings!=null }">
			<li><b><spring:message code="profile.rating.stars" />:</b> <jstl:out
					value="${actor.avgRating}" /></li>
		</jstl:if>
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

<jstl:if test="${ratings!=null }">
	<h3>
		<b><spring:message code="profile.ratings" />:</b>
	</h3>
	
	<display:table name="ratings" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">
		<acme:column code="profile.rating.moment" property="moment" />
		<acme:column code="profile.rating.customer" property="customer.name" />
		<acme:column code="profile.rating.stars" property="stars" />
		<acme:column code="profile.rating.comment" property="comment" />
	</display:table>
</jstl:if>
<br />

<jstl:if test="${principalUserAccount.id == actor.userAccount.id }">
	<acme:link href="profile/edit.do" code="profile.edit" type="warning btn-block"/>
</jstl:if>

