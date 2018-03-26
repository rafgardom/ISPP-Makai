<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="animals" id="row" requestURI="${requestURI}" class="displaytag">
	
	<display:column>
		<img src="${row.stringImage}" alt="<spring:message code='profile.no.picture' />" width="125px" height="125px">
	</display:column>
	
	<acme:column code="animal.name" property="name" />
	<acme:column code="animal.chipNumber" property="chipNumber" />
	<acme:column code="animal.age" property="age" />
	<acme:column code="animal.sex" property="sex" />
	
	<display:column>
		<div class="btn-group" data-toggle="buttons">	
			<acme:link image="eye" href="animal/display.do?animalId=${row.id}"/>
		</div>
	</display:column>
		
</display:table>

