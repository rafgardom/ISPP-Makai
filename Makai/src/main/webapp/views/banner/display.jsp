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
 <img src="${banner.stringImage}" class="rounded-circle" alt="<spring:message code='animal.no.picture' />" width="200px" height="200px">
</div>
<br>
<div class="card-deck">

	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="banner.info" /></h3>
	  	<div class="card-body"> 	
			<p><b><spring:message code="banner.totalViews" />:</b> <jstl:out
				value="${banner.totalViews}" /></p>
	  	
	  		<p><b><spring:message code="banner.currentViews" />:</b> <jstl:out
				value="${banner.currentViews}" /></p>
					
			<p><b><spring:message code="banner.price" />:</b> <jstl:out
				value="${banner.price}" /></p>
				
			<p>
				<b><spring:message code="banner.zone" />:</b>
				<spring:message code="banner.zone.${banner.zone}" />
			</p>
			
			<security:authorize access="hasRole('ADMIN')">
				<p>
					<a href="profile/displayProfile.do?actorId=${banner.actor.id}">${banner.actor.name}</a>
				</p>
			</security:authorize>
	  	
	  	</div>
	</div>
</div>
<br>

	
	<acme:link href="banner/actor/list.do" code="banner.goBack"/>


