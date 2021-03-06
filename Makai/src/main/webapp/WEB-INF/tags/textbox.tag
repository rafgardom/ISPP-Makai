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

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="style" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="mandatory" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>


<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<jstl:if test="${mandatory == true}">
	<jstl:set var="required" value="required" />
</jstl:if>

<jstl:if test="${style == null}">
	<jstl:set var="style" value=" " />
</jstl:if>

<spring:message code="tag.textbox.title" var="textBoxTitle"/>
<spring:message code="tag.textbox.year" var="textBoxYear"/>

<%-- Definition --%>

<spring:bind path="${path}">
	<div class="form-group" style="${style}">
		<form:label path="${path}">
			<spring:message code="${code}" />
			<jstl:if test="${mandatory == true}">
				<img src="images/asterisk.png"	width="16"/> 
			</jstl:if>
			<jstl:if test="${mandatory == false}">
				<small class="text-muted">(<spring:message code="optional"/>)</small>
			</jstl:if>
		</form:label>
		<jstl:if test="${path == 'year' }">
			<form:input path="${path}" pattern="\d{4}$" readonly="${readonly}" class="form-control" placeholder="${placeholder}" maxlength="100" required="${required}" title="${textBoxYear }"/>	
		</jstl:if>
		<jstl:if test="${path != 'year' }">
			<form:input path="${path}" readonly="${readonly}" class="form-control" placeholder="${placeholder}" maxlength="100" required="${required}" title="${textBoxTitle }"/>	
		</jstl:if>
		<form:errors path="${path}" cssClass="alert alert-danger form-control-sm d-block" />
	</div>
</spring:bind>
