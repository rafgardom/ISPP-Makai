<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h1 class="text-center">
	<jstl:if test="${!pastList}">
		<spring:message code="welcome.travel.my.travels"/>
	</jstl:if>
	<jstl:if test="${pastList}">
		<spring:message code="welcome.travel.past"/>
	</jstl:if>
</h1>

<div class="table-responsive">
<display:table name="travels" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">

	<acme:column code="travel.origin" property="origin.city" sortable="true"/>
	<acme:column code="travel.destination" property="destination.city" sortable="true"/>
	<acme:column code="travel.startMoment" property="startMoment" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	<acme:column code="travel.duration" property="duration"/>
	<acme:column code="travel.animalSeats" property="animalSeats" />
	<acme:column code="travel.humanSeats" property="humanSeats" />
	
	<spring:message code="travel.owner" var="ownerHeader" />
	<display:column class="text-center" title="${ownerHeader}" >
		<a href="profile/displayProfile.do?actorId=${row.transporterOwner.id}"><jstl:out value="${row.transporterOwner.name}"/></a>
	</display:column>
	
	<display:column>
		<div class="btn-group">
			<acme:link href="travel/display.do?travelId=${row.id}" image="eye"/>
			<jstl:if test="${requestURI == 'travel/myList.do'}">
				<jstl:if test="${row.transporterOwner.id == principal.id}">
					<acme:link href="travel/edit.do?travelId=${row.id}" type="warning" image="edit"/>
					<acme:delete href="travel/delete.do?travelId=${row.id}" id="${row.id}"/>
				</jstl:if>
				<jstl:if test="${row.transporterOwner.id != principal.id}">
					<acme:link href="travel/register.do?travelId=${row.id}" type="warning" image="edit"/>
				</jstl:if>
			</jstl:if>
			<jstl:if test="${requestURI == 'travel/myPastList.do' && row.transporterOwner.id != principal.id}">
				<jstl:set var="show" value="${true}"/>
				<jstl:forEach var="r" items="${principalRatings}">
					<jstl:if test="${r.travel.id == row.id}">
						<jstl:set var="show" value="${false}"/>
					</jstl:if>
				</jstl:forEach>
				<jstl:if test="${show == true}">
					<acme:link href="rating/customer/createTravel.do?travelId=${row.id}" type="dark" image="star-white-32"/>
				</jstl:if>
			</jstl:if>
		</div>
	</display:column>
	
		
</display:table>
</div>
	<br>
	<acme:link href="travel/create.do" code="travel.create" type="success"/>
	<acme:link href="travel/menu.do" code="notification.goBack"/>


