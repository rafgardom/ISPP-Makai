<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="travels" id="row" requestURI="${requestURI}" class="displaytag">

	<acme:column code="travel.country" property="origin.country" />
	<acme:column code="travel.state" property="origin.state" />
	<acme:column code="travel.province" property="origin.province" />
	<acme:column code="travel.city" property="origin.city" />
	<acme:column code="travel.zipcode" property="origin.zip_code" />
	<acme:column code="travel.country" property="destination.country" />
	<acme:column code="travel.state" property="destination.state" />
	<acme:column code="travel.province" property="destination.province" />
	<acme:column code="travel.city" property="destination.city" />
	<acme:column code="travel.zipcode" property="destination.zip_code" />
	<acme:column code="travel.startMoment" property="startMoment" />
	<acme:column code="travel.endMoment" property="endMoment" />
	<acme:column code="travel.animalSeats" property="animalSeats" />
	<acme:column code="travel.humanSeats" property="humanSeats" />

	<display:column>
		<div class="btn-group" data-toggle="buttons">
			<acme:link href="travel/register.do?travelId=${row.id}" code="travel.register"/>
		</div>
	</display:column>
	
</display:table>
<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">
	<acme:link href="travel/create.do" code="travel.create"/>
</security:authorize>