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
<div>	
	<fieldset>
		<legend>
			<b><spring:message code="travel.origin" />:</b>
		</legend>
		<ul>
			<li>
			<b><spring:message code="travel.country" />:</b>
			<jstl:out value="${travel.origin.country}"  />
		</li>
		<li>
			<b><spring:message code="travel.state" />:</b>
			<jstl:out value="${travel.origin.state}"  />
		</li>
		<li>
			<b><spring:message code="travel.province" />:</b>
			<jstl:out value="${travel.origin.province}"  />
		</li>
		<li>
			<b><spring:message code="travel.city" />:</b>
			<jstl:out value="${travel.origin.city}"  />
		</li>
		<li>
			<b><spring:message code="travel.zipcode" />:</b>
			<jstl:out value="${travel.origin.zip_code}"  />
		</li>
		<li>
			<b><spring:message code="travel.startMoment" />:</b>
			<jstl:out value="${travel.startMoment}"  />
		</li>
		</ul>
	</fieldset>
		<fieldset>
		<legend>
			<b><spring:message code="travel.destination" />:</b>
		</legend>
		<ul>
			<li>
			<b><spring:message code="travel.country" />:</b>
			<jstl:out value="${travel.destination.country}"  />
		</li>
		<li>
			<b><spring:message code="travel.state" />:</b>
			<jstl:out value="${travel.destination.state}"  />
		</li>
		<li>
			<b><spring:message code="travel.province" />:</b>
			<jstl:out value="${travel.destination.province}"  />
		</li>
		<li>
			<b><spring:message code="travel.city" />:</b>
			<jstl:out value="${travel.destination.city}"  />
		</li>
		<li>
			<b><spring:message code="travel.zipcode" />:</b>
			<jstl:out value="${travel.destination.zip_code}"  />
		</li>
		<li>
			<b><spring:message code="travel.endMoment" />:</b>
			<jstl:out value="${travel.endMoment}"  />
		</li>
		</ul>
	</fieldset>
	<fieldset>
		<legend>
			<b><spring:message code="travel.seats" />:</b>
		</legend>
		<ul>
			<li>
			<b><spring:message code="travel.animalSeats" />:</b>
			<jstl:out value="${travel.animalSeats}"  />
		</li>
		<li>
			<b><spring:message code="travel.humanSeats" />:</b>
			<jstl:out value="${travel.humanSeats}"  />
		</li>
		</ul>
	</fieldset>
</div>
</security:authorize>