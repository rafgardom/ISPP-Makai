<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="card shadow my-5 text-center offset-md-4 col-md-4">
 		<div class="card-body">
 		<p>
			<b><spring:message code="request.category" />:</b>
			<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
			<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>
			<jstl:if test="${lang == 'es'}">
				<jstl:out value="${request.category.spanishName}"  />
			</jstl:if>
			<jstl:if test="${lang == 'en'}">
				<jstl:out value="${request.category.name}"  />
			</jstl:if>
			
		</p>
 		
 		<jstl:if test="${request.tags != null}">
		<p>
			<b><spring:message code="request.tags" />:</b>
			<jstl:out value="${request.tags}"  />
		</p>
		</jstl:if>


	</div>
</div>

<div class="card-deck text-center">

	<jsp:useBean id="util" class="utilities.Utilities" scope="page" />
	
	<jstl:if test="${request.animal != null }">
	<div class="card shadow">
 		<br><h3 class="card-title my-3"><spring:message code="request.animal" /></h3>
  		<div class="card-body">
  		
  			<div class="center-div">
					<img src="${util.showImage(request.getAnimal().getPicture(),'noImage')} " class="rounded-circle" width="200px" height="200px">
			</div>
			<p>
				<b><spring:message code="request.animal.name" />:</b>
				<jstl:out value="${request.animal.name}"/>
			</p>
			<acme:link href="animal/display.do?animalId=${request.animal.id}" code="animal.view.profile" image="print" />
		</div>
	</div>
	</jstl:if>
	<div class="card shadow">
 		<br><h3 class="card-title my-3"><spring:message code="request.customer" /></h3>
  		<div class="card-body">
	  		
			<div class="center-div">
					<img src="${util.showImage(request.getCustomer().getPicture(),'user')} " class="rounded-circle" width="200px" height="200px">
			</div>
			<p>
				<b><spring:message code="request.animal.name" />:</b>
				<jstl:out value="${request.customer.name}"/>
			</p>
			<acme:link href="profile/displayProfile.do?actorId=${request.customer.id}" code="animal.view.profile" image="user-white" />
		
		</div>
	</div>

</div>

<div class="card text-center m-5 shadow">
<div class="card-body">
		<p>
			<b><spring:message code="request.description" />:</b>
			<jstl:out value="${request.description}"  />
		</p>
</div>
</div>
<div class="center-div my-3">
<security:authorize access="hasRole('TRAINER')">
	<acme:link href="request/trainer/list.do" code="notification.goBack" image="arrow_left"/>
</security:authorize>
<security:authorize access="hasRole('CUSTOMER')">
	<acme:link href="request/customer/menu.do" code="notification.goBack" image="arrow_left"/>
</security:authorize>
</div>
