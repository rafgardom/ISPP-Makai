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
	<acme:column code="request.animal" property="animal" />
	
	<display:column>
		<a href="request/delete.do?requestId=${row.id}">
			<spring:message	code="request.delete" />
		</a>
	</display:column>

</display:table>
<security:authorize access="hasRole('CUSTOMER')">
	<a href="request/create.do"><spring:message	code="request.create" /></a>
</security:authorize>