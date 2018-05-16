<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authentication var="principalUserAccount" property="principal" />
<jstl:if test="${pictureImage != null}">
<div class="center-div">
	<img src="${pictureImage}" class="rounded-circle" alt="<spring:message code='profile.no.picture' />" width="200px" height="200px"><br>
</div>
</jstl:if>
<br>
<div class="card-deck">

	<div class="card shadow">
	 	<br><h3 class="card-title my-3"><spring:message code="profile.personal.info" /></h3>
	  	<div class="card-body"> 	
			<p><b><spring:message code="profile.name" />:</b> 
				<jstl:out value="${actor.name}" /></p>
		
		<jstl:if test="${fn:contains('ADMIN TRAINER',actor.userAccount.authorities[0].authority) }">
			<p><b><spring:message code="profile.surname" />:</b> 
					<jstl:out value="${actor.surname}" /></p>
	
			<p><b><spring:message code="profile.nid" />:</b> 
				<jstl:out value="${actor.nid}" /></p>
		</jstl:if>
		<jstl:if test="${fn:contains('TRAINER',actor.userAccount.authorities[0].authority) }">
			<p><b><spring:message code="trainer.certifyingCompany" />:</b> 
					<jstl:out value="${actor.certifyingCompany}" /></p>
		</jstl:if>
		
	  	<jstl:if test="${actor.userAccount.id==principalUserAccount.id || fn:contains('ADMIN',principalUserAccount.authorities[0].authority) }">
	  		<p><b><spring:message code="profile.phone" />:</b> <jstl:out
					value="${actor.phone}" /></p>
					
			<p><b><spring:message code="profile.email" />:</b> <jstl:out
					value="${actor.email}" /></p>
	  	</jstl:if>
	  	
	  	</div>
	</div>
	<div class="card shadow">
	 	<br><h3 class="card-title my-3"><spring:message code="profile.coordinates" /></h3>
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


<jstl:if test="${principalUserAccount.id == actor.userAccount.id }">
<div class="center-div pt-5">
	<acme:link href="profile/edit.do" code="profile.edit" type="warning" image="edit"/>
</div>
</jstl:if>


<jstl:if test="${!empty ratings }">
	<h3>
		<b><spring:message code="profile.ratings" /></b>
	</h3>
	
	<div class="table-responsive">
	<display:table name="ratings" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag w-100 d-table">
		<acme:column code="profile.rating.moment" property="moment" format="{0,date,dd/MM/yyyy}"/>
		<acme:column code="profile.rating.customer" property="customer.name" />
		
		<spring:message code="profile.rating.stars" var="starsHeader" />
		<display:column class="text-center" title="${starsHeader }" >
			<jstl:forEach var = "i" begin = "1" end = "${row.stars }" >
				<img src="images/star-32.png">
			</jstl:forEach>
		</display:column>
		<acme:column code="profile.rating.comment" property="comment" />
	</display:table>
	</div>
</jstl:if>
