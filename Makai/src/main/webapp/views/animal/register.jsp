<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="animal/customer/edit.do" modelAttribute="animal">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="breeds" />
	<form:hidden path="customer" />
	<form:hidden path="animalShelter" />
	<form:hidden path="sex" />
	<form:hidden path="isHidden" />

	<acme:input code="animal.name" path="name" />
	<acme:input code="animal.chipNumber" path="chipNumber"/>
	<acme:input code="animal.age" path="age" />

	<jstl:out value="${picture}"/>
					<form:input type="file" path="picture" id="picture" name="picture" mandatory="true"
					class="form:input-large" enctype="multipart/form-data" code="animal.picture"></form:input>
	<jstl:out value="${formats}"/>
	
	<acme:submit name="save" code="animal.save" />
	
	<acme:cancel url="/" code="animal.cancel" />
		
</form:form>
