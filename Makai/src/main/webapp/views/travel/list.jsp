<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('CUSTOMER')">
<div class="table-responsive">
<display:table name="travels" id="row" pagesize="10" requestURI="${requestURI}" class="displaytag">

	<acme:column code="travel.origin" property="origin.city" sortable="true"/>
	<acme:column code="travel.destination" property="destination.city" sortable="true"/>
	<acme:column code="travel.startMoment" property="startMoment" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true"/>
	<acme:column code="travel.duration" property="duration"/>
	<acme:column code="travel.animalSeats" property="animalSeats" />
	<acme:column code="travel.humanSeats" property="humanSeats" />
	
	<spring:message code="travel.owner" var="ownerHeader" />
	<display:column title="${ownerHeader}" class="text-center">
		<a href="profile/displayProfile.do?actorId=${row.transporterOwner.id}"><jstl:out value="${row.transporterOwner.name}"/></a>
	</display:column>

	<display:column>
	<div class="btn-group">
		<acme:link href="travel/display.do?travelId=${row.id}" image="eye"/>
		<jstl:if test="${row.transporterOwner.id != principal.id && (row.humanSeats > 0 || row.humanSeats > 0)}">
		<jstl:set var="show" value="${true}"/>
			<jstl:forEach var="t" items="${principal.travelPassengers}">
				<jstl:if test="${t.id == row.id}">
					<jstl:set var="show" value="${false}"/>
				</jstl:if>
			</jstl:forEach>
			<jstl:if test="${show == true}">
				<acme:link href="travel/register.do?travelId=${row.id}" code="travel.register" type="dark" image="map"/>
			</jstl:if>
		</jstl:if>
	</div>
	</display:column>
	
</display:table>
</div>
	<acme:link href="travel/create.do" code="travel.create" type="success"/>
</security:authorize>
