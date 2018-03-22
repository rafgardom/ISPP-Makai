<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="offers" id="row" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="offer.startMoment" property="startMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
	<acme:column code="offer.coordinates.city" property="destination.city" />
	<acme:column code="offer.price" property="price" />
	<acme:column code="offer.animal" property="animal.name" />
	
	
	
	<display:column>
		<div class="btn-group">
			<acme:link href="offer/trainer/display.do?offerId=${row.id}" code="offer.display"/>
			<security:authorize access="hasRole('TRAINER')">
				<jstl:if test="${row.isAccepted==false}">
					<acme:link href="offer/trainer/edit.do?offerId=${row.id}" code="offer.edit"/>
				</jstl:if>
				<jstl:if test="${row.isAccepted==false}">
					<acme:link href="offer/trainer/delete.do?offerId=${row.id}" code="offer.delete" type="danger"/>
				</jstl:if>
			</security:authorize>
		</div>
	</display:column>
	
</display:table>

