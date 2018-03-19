<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="animals" id="row" requestURI="${requestURI}" class="displaytag">
	
	<acme:column code="animal.name" property="name" />
	<acme:column code="animal.chipNumber" property="chipNumber" />
	<acme:column code="animal.age" property="age" />
	<acme:column code="animal.sex" property="sex" />
		
</display:table>

