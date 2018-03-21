<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="requests" id="row" requestURI="${requestURI}" class="displaytag">

	<acme:column code="request.description" property="description" />
	<acme:column code="request.tags" property="tags" />
	<acme:column code="request.category" property="category.name" />
	<acme:column code="request.animal" property="animal.name" />
	
	<display:column>
		<a href="request/actor/display.do?requestId=${row.id}">
			<spring:message	code="request.display" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<%--
		<display:column>
			<a href="request/customer/edit.do?requestId=${row.id}">
				<spring:message	code="request.edit" />
			</a>
		</display:column>
		--%>
		<display:column>
			<acme:cancel url="request/customer/delete.do?requestId=${row.id}" code="request.delete"/>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('TRAINER')">
		<display:column>
			<acme:link href="offer/trainer/create.do?requestId=${row.id}" code="offer.create"/>
		</display:column>
	</security:authorize>

</display:table>
<security:authorize access="hasRole('CUSTOMER')">
	<acme:link href="request/customer/create.do" code="request.create"/>
</security:authorize>