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
 
<%@ attribute name="path" required="true"%>
<%@ attribute name="code" required="true"%>

<%@ attribute name="type" required="false"%>
<%@ attribute name="placeholder" required="false"%>
<%@ attribute name="step" required="false"%>
<%@ attribute name="min" required="false"%>
<%@ attribute name="max" required="false"%>
<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="height" required="false" %>
<%@ attribute name="image" required="false" %>

<jstl:if test="${type == null}">
	<jstl:set var="type" value="text" />
</jstl:if>
<jstl:if test="${height == null}">
	<jstl:set var="height" value="32" />
</jstl:if>



<%-- Definition --%>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<div class="form-group">
	<form:label path="${path}">
		<spring:message code="${code}" />
		<jstl:if test="${mandatory == true}">
			<img src="images/asterisk.png"	width="16"/> 
		</jstl:if>
	</form:label>
	
	<jstl:if test="${image != null}">
	<div class="input-group"><!-- NO BORRAR -->
		<div class="input-group-prepend">
			<span class="input-group-text btn-light"><img src="images/${image}.png"/></span>
		</div>
	</jstl:if>
	
	<form:input class="form-control" path="${path}" type="${type }" placeholder="${placeholder }" step="${step }" min="${min }" max="${max }" />
	<form:errors cssClass="alert alert-danger form-control" path="${path}" />
	
	</div>
	
<jstl:if test="${image != null}">
	</div><!-- NO BORRAR -->
</jstl:if>
