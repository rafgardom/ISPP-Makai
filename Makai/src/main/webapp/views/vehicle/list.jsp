<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="vehicles" id="row" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="vehicle.brand" property="brand" />
	<acme:column code="vehicle.seats" property="seats" />
	<acme:column code="vehicle.carType" property="carType" />
	<acme:column code="vehicle.accommodation" property="accommodation" />
	<acme:column code="vehicle.year" property="year" />
	<acme:column code="vehicle.description" property="description" />
	<acme:column code="vehicle.state" property="state" />
	<acme:column code="vehicle.picture" property="picture" />
	<acme:column code="vehicle.color" property="color" />
	<acme:column code="vehicle.license" property="license" />
	
	<display:column>
		<a href="vehicle/delete.do?travelId=${row.id}">
			<spring:message	code="travel.delete" />
		</a>
		<a href="vehicle/edit.do?travelId=${row.id}">
			<spring:message	code="travel.edit" />
		</a>
	</display:column>
	
</display:table>

<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">
	<a href="vehicle/register.do"><spring:message	code="vehicle.register" /></a>
</security:authorize>



