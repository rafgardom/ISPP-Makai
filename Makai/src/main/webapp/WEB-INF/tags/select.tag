<%--
 * select.tag
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
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemLabel" required="true" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="disabled" required="false" %>
<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="size" required="false" %>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>
<jstl:if test="${disabled == null}">
	<jstl:set var="disabled" value="true" />
</jstl:if>
<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>
<jstl:if test="${mandatory == true}">
	<jstl:set var="required" value="required" />
</jstl:if>

<%-- Definition --%>

<div class="form-group">
	<form:label path="${path}">
		<spring:message code="${code}" />
		<jstl:if test="${mandatory == true}">
				<img src="images/asterisk.png"	width="16"/> 
		</jstl:if>
	</form:label>	
	<form:select size="${size}" id="${id}" path="${path}" class="form-control" onchange="${onchange}" required="${required}">
		<form:option value="0" label="----" selected="selected" disabled="${disabled}"/>		
		<form:options items="${items}" itemValue="id" itemLabel="${itemLabel}" />
	</form:select>
	<form:errors path="${path}" cssClass="alert alert-danger form-control" />
</div>


