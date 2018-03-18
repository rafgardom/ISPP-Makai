<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="animal/customer/edit.do" modelAttribute="animal">
	
	<form:hidden path="id" />

	<acme:input code="animal.name" path="name" />
	<acme:input code="animal.chipNumber" path="chipNumber"/>
	<acme:input code="animal.age" path="age" type="number" min="0" />
	
	<form:select path="sex">
		<form:option value="${null }" label="----" />    
		<jstl:forEach items="${sexs }" var="sex">
			<form:option value="${sex}" label="${sex}" />
		</jstl:forEach>
	</form:select>
	
	<jstl:out value="${picture}"/>
					<form:input type="file" path="picture" id="picture" name="picture" mandatory="true"
					class="form:input-large" enctype="multipart/form-data" code="animal.picture"></form:input>
	<jstl:out value="${formats}"/>
	
	<acme:submit name="save" code="animal.save" />
	
	<acme:cancel url="/" code="animal.cancel" />
		
</form:form>
