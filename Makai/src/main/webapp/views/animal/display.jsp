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
 <img src="${animalImage}" class="rounded-circle" alt="<spring:message code='animal.no.picture' />" width="200px" height="200px">
</div>
<br>
<div class="card-deck">

	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="animal.info" /></h3>
	  	<div class="card-body"> 	
			<p><b><spring:message code="animal.name" />:</b> <jstl:out
				value="${animal.name}" /></p>
	  	
	  		<p><b><spring:message code="animal.chipNumber" />:</b> <jstl:out
				value="${animal.chipNumber}" /></p>
					
			<p><b><spring:message code="animal.age" />:</b> <jstl:out
				value="${animal.age}" /></p>
				
			<p>
				<b><spring:message code="animal.sex" />:</b>
				<acme:imageSex sexName="${animal.sex.name}"/>
				<%-- <%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
				<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
				<jstl:if test="${lang == 'es'}">
					<jstl:out value="${animal.sex.spanishName}"  />
				</jstl:if>
				<jstl:if test="${lang == 'en'}">
					<jstl:out value="${animal.sex.name}"  />
				</jstl:if> --%>
			</p>
				
			<p><b><spring:message code="animal.specie" />:</b> <jstl:out
				value="${specie}" /></p>
				
			
			<jstl:forEach var="r" items="${animal.breeds}">
				<jstl:if test="${r != null}">
					<p><b><spring:message code="animal.breed" />:</b> <jstl:out
				value="${r.name}" /></p>
				</jstl:if>
			</jstl:forEach>
	  	
	  	</div>
	</div>
	
	<jstl:if test="${animal.customer!=null}">
		<div class="card">
		 	<br><h3 class="card-title"><spring:message code="animal.owner.info" /></h3>
		  	<div class="card-body"> 	
				<p><b><spring:message code="animal.name" />:</b> 
					<jstl:out value="${animal.customer.name}" /></p>
				
				<p><b><spring:message code="animal.owner.username" />:</b> 
					<jstl:out value="${animal.customer.userAccount.username}" /></p>
					
			<jstl:if test="${ratings!=null }">
				<p><b><spring:message code="profile.rating.stars" />:</b> <jstl:out
						value="${animal.customer.avgRating}" /></p>
			</jstl:if>
		  	
		  		<p><b><spring:message code="animal.owner.city" />:</b> <jstl:out
						value="${animal.customer.coordinates.city}" /></p>
						
		  		<acme:link href="profile/displayProfile.do?actorId=${animal.customer.id}" code="animal.view.profile"/>
		  	</div>
		</div>
	</jstl:if>
	<jstl:if test="${animal.animalShelter!=null}">
		<div class="card">
		 	<br><h3 class="card-title"><spring:message code="animal.owner.info" /></h3>
		  	<div class="card-body"> 	
				<p><b><spring:message code="animal.name" />:</b> 
					<jstl:out value="${animal.animalShelter.name}" /></p>
					
				<p><b><spring:message code="animal.owner.username" />:</b> 
					<jstl:out value="${animal.animalShelter.userAccount.username}" /></p>
		  	
		  		<p><b><spring:message code="animal.owner.phone" />:</b> <jstl:out
						value="${animal.animalShelter.phone}" /></p>
		  	
		  		<p><b><spring:message code="animal.owner.city" />:</b> <jstl:out
						value="${animal.animalShelter.coordinates.city}" /></p>
		  		
		  		<acme:link href="profile/displayProfile.do?actorId=${animal.animalShelter.id}" code="animal.view.profile"/>
		  	</div>
		</div>
	</jstl:if>
</div>
<br>

	
	<acme:link href="animal/list.do" code="animal.goBack"/>


