<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="table-responsive">
<display:table name="travels" id="row" pagesize="10" requestURI="${requestURI}" class="displaytag">

	<jstl:set var="estilo" value="normal" />
	<jstl:if test="${principal.id == row.transporterOwner.id}">
		<jstl:set var="estilo" value="propietario" />
	</jstl:if>
	<jstl:if test="${principal.travelPassengers.contains(row)}">
		<jstl:set var="estilo" value="participo" />
	</jstl:if>
	<jstl:forEach var="animal" items="${myAnimals}">
		<jstl:if test="${row.animals.contains(animal)}">
			<jstl:set var="estilo" value="participo" />
		</jstl:if>
	</jstl:forEach>
	

	<acme:column code="travel.origin" property="origin.city" sortable="true"/>
	<acme:column code="travel.destination" property="destination.city" sortable="true"/>
	<acme:column code="travel.startMoment" property="startMoment" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true"/>
	
	<jstl:set var="minutos" value="${row.duration%60}"/>
	<jstl:set var="horas" value="${((row.duration-minutos)/60).intValue()}"/>
	<spring:message code="travel.duration" var="durationHeader" />
	<display:column title="${durationHeader}" class="text-center">
		<jstl:if test="${horas > 0}">
			<jstl:out value="${horas} " />
			<jstl:if test="${horas == 1}">
				<spring:message code="travel.hour" />
			</jstl:if>
			<jstl:if test="${horas > 1}">
				<spring:message code="travel.hours" />
			</jstl:if>
		</jstl:if>
		<jstl:if test="${minutos > 0}">
			<jstl:out value="${minutos} " />
			<jstl:if test="${minutos == 1}">
				<spring:message code="travel.minute" />
			</jstl:if>
			<jstl:if test="${minutos > 1}">
				<spring:message code="travel.minutes" />
			</jstl:if>
		</jstl:if>
	</display:column>
	
	<acme:column code="travel.animalSeats" property="animalSeats" />
	<acme:column code="travel.humanSeats" property="humanSeats" />
	
	<spring:message code="travel.owner" var="ownerHeader" />
	<display:column title="${ownerHeader}" class="text-center">
		<a href="profile/displayProfile.do?actorId=${row.transporterOwner.id}"><jstl:out value="${row.transporterOwner.name}"/></a>
	</display:column>

	<display:column>
	<div class="btn-group">
		<acme:link href="travel/display.do?travelId=${row.id}" image="eye"/>
		<jstl:if test="${(row.transporterOwner.id != principal.id)}">
			<jstl:if test="${estilo == 'normal'&& row.animalSeats > 0}">
				<acme:link href="travel/register.do?travelId=${row.id}" code="travel.register" type="dark" image="map"/>
			</jstl:if>
			<jstl:if test="${estilo == 'participo'}">
				<acme:link href="travel/register.do?travelId=${row.id}" code="travel.modifyRegister" type="dark" image="map"/>
			</jstl:if>
		</jstl:if>
	</div>
	</display:column>
	
</display:table>
</div>

<div class="center-div">
	<acme:link href="travel/menu.do" code="notification.goBack" image="arrow_left"/>
	<acme:link href="travel/create.do" code="travel.create" type="success mx-2" image="add1" image2="car1"/>
</div>
