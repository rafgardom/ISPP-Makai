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
	<acme:column code="vehicle.color" property="color" />
	<acme:column code="vehicle.license" property="license" />
	
	<display:column>
	<img src="${picture}" alt="<spring:message code='vehicle.picture' />" width="100px" height="100px">
	</display:column>
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">
			<acme:link href="vehicle/edit.do?vehicleId=${row.id}" code="travel.edit"/>
			<acme:link href="vehicle/delete.do?vehicleId=${row.id}" code="travel.delete" type="danger"/>
		</div>
	</display:column>
	
</display:table>
<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">
	<acme:link href="vehicle/register.do" code="vehicle.register"/>
</security:authorize>



