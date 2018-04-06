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

<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">	

<div class="card-deck">
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="travel.origin" /></h3>
	  	<div class="card-body">
	  			<p>
			<b><spring:message code="travel.country" />:</b>
			<jstl:out value="${travel.origin.country}"  />
			</p>
			<p>
				<b><spring:message code="travel.state" />:</b>
				<jstl:out value="${travel.origin.state}"  />
			</p>
			<p>
				<b><spring:message code="travel.province" />:</b>
				<jstl:out value="${travel.origin.province}"  />
			</p>
			<p>
				<b><spring:message code="travel.city" />:</b>
				<jstl:out value="${travel.origin.city}"  />
			</p>
			<p>
				<b><spring:message code="travel.zipcode" />:</b>
				<jstl:out value="${travel.origin.zip_code}"  />
			</p>
			<p>
				<b><spring:message code="travel.startMoment" />:</b>
				<jstl:out value="${travel.startMoment}"  />
			</p>
	  	</div>
	</div>
	<div class="card">
	 	<br><h3 class="card-title"><spring:message code="travel.destination" /></h3>
	  	<div class="card-body">
	  		<p>
			<b><spring:message code="travel.country" />:</b>
			<jstl:out value="${travel.destination.country}"  />
		</p>
		<p>
			<b><spring:message code="travel.state" />:</b>
			<jstl:out value="${travel.destination.state}"  />
		</p>
		<p>
			<b><spring:message code="travel.province" />:</b>
			<jstl:out value="${travel.destination.province}"  />
		</p>
		<p>
			<b><spring:message code="travel.city" />:</b>
			<jstl:out value="${travel.destination.city}"  />
		</p>
		<p>
			<b><spring:message code="travel.zipcode" />:</b>
			<jstl:out value="${travel.destination.zip_code}"  />
		</p>
		<p>
			<b><spring:message code="travel.endMoment" />:</b>
			<jstl:out value="${travel.endMoment}"  />
		</p>
	  	</div>
	 </div>

</div>
	 <div class="card my-3">
	 	<br><h3 class="card-title"><spring:message code="travel.seats" /></h3>
	  	<div class="card-body">
		  	<p>
				<b><spring:message code="travel.animalSeats" />:</b>
				<jstl:out value="${travel.animalSeats}"  />
			</p>
			<p>
				<b><spring:message code="travel.humanSeats" />:</b>
				<jstl:out value="${travel.humanSeats}"  />
			</p>
	  	</div>
	 </div>
</security:authorize>