<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="travels" id="row" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="travel.country" property="countryOrigin" />
	<acme:column code="travel.state" property="stateOrigin" />
	<acme:column code="travel.province" property="provinceOrigin" />
	<acme:column code="travel.city" property="cityOrigin" />
	<acme:column code="travel.zipcode" property="zip_codeOrigin" />
	<acme:column code="travel.country" property="countryDestination" />
	<acme:column code="travel.state" property="stateDestination" />
	<acme:column code="travel.province" property="provinceDestination" />
	<acme:column code="travel.city" property="cityDestination" />
	<acme:column code="travel.zipcode" property="zip_codeDestination" />
	<acme:column code="travel.startMoment" property="startMoment" />
	<acme:column code="travel.endMoment" property="endMoment" />
	<acme:column code="travel.animalSeats" property="animalSeats" />
	<acme:column code="travel.humanSeats" property="HumanSeats" />
	
	<display:column>
		<a href="travel/delete.do?travelId=${row.id}">
			<spring:message	code="travel.delete" />
		</a>
		<a href="travel/edit.do?travelId=${row.id}">
			<spring:message	code="travel.edit" />
		</a>
	</display:column>
	
</display:table>

<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">
	<a href="travel/create.do"><spring:message	code="travel.create" /></a>
</security:authorize>



