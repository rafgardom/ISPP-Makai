<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<div class="center-div">
 <img src="${animalImage}" class="rounded-circle" alt="<spring:message code='animal.no.picture' />" width="200px" height="200px">
</div>
<br>

<jsp:useBean id="util" class="utilities.Utilities" scope="page" />
<div class="card-deck">

	<div class="card shadow">
	 	<h3 class="card-title mt-5"><spring:message code="animal.info" /></h3>
	  	<div class="card-body ml-3"> 	
			<p><b><spring:message code="animal.name" />:</b> <jstl:out
				value="${animal.name}" /></p>
	  	
	  		<jstl:if test="${animal.customer!=null}">
	  			<jstl:if test="${animal.customer.id == principal.id}">
	  				<p><b><spring:message code="animal.chipNumber" />:</b> <jstl:out
					value="${animal.chipNumber}" /></p>
	  			</jstl:if>
	  		</jstl:if>
	  		<jstl:if test="${animal.animalShelter!=null}">
	  			<jstl:if test="${animal.animalShelter.id == principal.id}">
	  				<p><b><spring:message code="animal.chipNumber" />:</b> <jstl:out
					value="${animal.chipNumber}" /></p>
	  			</jstl:if>
	  		
	  		</jstl:if>
					
			<p><b><spring:message code="animal.birthday" />:</b> <fmt:formatDate pattern = "dd-MM-yyyy" value="${animal.birthday}" /></p>
				
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
			
			<jstl:if test="${r != null}">
					<jstl:if test="${lang == 'es'}">
						<p><b><spring:message code="animal.specie" />:</b> <jstl:out value="${specie.typeSpa}" /></p>
					</jstl:if>
					<jstl:if test="${lang == 'en'}">
						<p><b><spring:message code="animal.specie" />:</b> <jstl:out value="${specie.typeEng}" /></p>
					</jstl:if>
			</jstl:if>
			
			<jstl:forEach var="r" items="${animal.breeds}">
				<jstl:if test="${r != null}">
					<jstl:if test="${lang == 'es'}">
						<p><b><spring:message code="animal.breed" />:</b> <jstl:out value="${r.nameSpa}" /></p>
					</jstl:if>
					<jstl:if test="${lang == 'en'}">
						<p><b><spring:message code="animal.breed" />:</b> <jstl:out value="${r.nameEng}" /></p>
					</jstl:if>
				</jstl:if>
			</jstl:forEach>
	  	
	  	</div>
	</div>
	
	<jstl:if test="${animal.customer!=null}">
		
		<div class="card shadow text-center">
		<div class="center-div">
			<img src="${util.showImage(animal.getCustomer().getPicture(),'user')} " class="rounded-circle" width="200px" height="200px">	
		</div>
		 	<br><h3 class="card-title m-3"><spring:message code="animal.owner.info" /></h3>
		  	<div class="card-body "> 	
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
						
		  		<acme:link href="profile/displayProfile.do?actorId=${animal.customer.id}" code="animal.view.profile" image="user-white"/>
		  	</div>
		</div>
	</jstl:if>
	<jstl:if test="${animal.animalShelter!=null}">
		
		<div class="card shadow">
			<div class="center-div">
				<img src="${util.showImage(animal.getAnimalShelter().getPicture(),'user')} " class="rounded-circle" width="200px" height="200px">	
			</div>
		 	<br><h3 class="card-title mt-3"><spring:message code="animal.owner.info.animalShelter" /></h3>
		  	<div class="card-body"> 		
				<p><b><spring:message code="animal.name" />:</b> 
					<jstl:out value="${animal.animalShelter.name}" /></p>
					
				<p><b><spring:message code="animal.owner.username" />:</b> 
					<jstl:out value="${animal.animalShelter.userAccount.username}" /></p>
		  	
		  		<p><b><spring:message code="animal.owner.phone" />:</b> <jstl:out
						value="${animal.animalShelter.phone}" /></p>
		  	
		  		<p><b><spring:message code="animal.owner.city" />:</b> <jstl:out
						value="${animal.animalShelter.coordinates.city}" /></p>
		  		
		  		<acme:link href="profile/displayProfile.do?actorId=${animal.animalShelter.id}" code="animal.view.profile" image="user-white" />
		  	</div>
		</div>
	</jstl:if>
</div>
<br>

<div class="center-div">
	<acme:link href="animal/list.do" code="animal.goBack" image="arrow_left"/>
</div>

