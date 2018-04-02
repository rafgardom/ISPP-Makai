<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasAnyRole('PROFESSIONAL','CUSTOMER')">
<display:table name="travels" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">

	<acme:column code="travel.origin" property="origin.city" sortable="true"/>
	<acme:column code="travel.destination" property="destination.city" sortable="true"/>
	<acme:column code="travel.startMoment" property="startMoment" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}"/>
	<acme:column code="travel.endMoment" property="endMoment" format="{0,date,dd/MM/yyyy HH:mm}"/>
	<acme:column code="travel.animalSeats" property="animalSeats" />
	<acme:column code="travel.humanSeats" property="humanSeats" />
	
	<display:column>
		<div class="btn-group">
			<acme:link href="travel/display.do?travelId=${row.id}" image="eye"/>
			<jstl:if test="${requestURI == 'travel/myList.do' && row.transporterOwner.id == principal.id}">
				<acme:link href="travel/edit.do?travelId=${row.id}" type="warning" image="edit"/>
				<acme:delete href="travel/delete.do?travelId=${row.id}" id="${row.id}"/>
			</jstl:if>
			<jstl:if test="${requestURI == 'travel/myPastList.do' && row.transporterOwner.id != principal.id}">
				<jstl:set var="show" value="${true}"/>
				<jstl:forEach var="r" items="${principalRatings}">
					<jstl:if test="${r.travel.id == row.id}">
						<jstl:set var="show" value="${false}"/>
					</jstl:if>
				</jstl:forEach>
				<jstl:if test="${show == true}">
					<acme:link href="rating/customer/createTravel.do?travelId=${row.id}" type="dark" image="star-32"/>
				</jstl:if>
			</jstl:if>
		</div>
	</display:column>
	
		
</display:table>
	<acme:link href="travel/create.do" code="travel.create" type="success"/>
</security:authorize>

