<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true"%>
<%@ attribute name="imageError" required="false"%>
<%@ attribute name="mandatory" required="false"%>


<%-- Definition --%>
<h3>
	<spring:message code="picture"/> 
	<jstl:if test="${mandatory == true}">
		<img src="images/asterisk.png" width="16"/>
	</jstl:if>
	<jstl:if test="${mandatory == false}">
		<small class="text-muted">(<spring:message code="optional"/>)</small>
	</jstl:if>
</h3>

<form:input type="file" path="${path}"></form:input>
<small class="form-text text-muted"><spring:message code="image.formats"/></small>
<form:errors path="${path}" cssClass="alert alert-danger form-control-sm d-block" />

<!-- Solo para actores -->
<jstl:if test="${inputError != null}">
	<acme:error code="${inputError}" type="danger"/>
</jstl:if>
