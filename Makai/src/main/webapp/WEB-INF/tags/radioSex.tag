<%--
 * textbox.tag
 *
 * Copyright (C) 2014 Universidad de Sevilla
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

<%-- <%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="link" required="false" %> --%>

<%-- Definition --%>

<input id="valueForm" type="hidden" value="${animalForm.sex}">
		
	<label>
		<spring:message code="animal.sex" />
		<img src="images/asterisk.png"	width="16"/> 
	</label>
    <div class="form-group d-flex">
	    <div class="custom-control custom-radio mx-2">
	     	<input type="radio" class="custom-control-input" name="sex" id="customRadio1" value="MALE">
			<label class="custom-control-label" for="customRadio1"><img src="images/male.png"/></label>
	   	</div>
	   	<div class="custom-control custom-radio mx-2">
	      <input type="radio" class="custom-control-input" name="sex" id="customRadio2" value="FEMALE">
	      <label class="custom-control-label" for="customRadio2"><img src="images/femenine.png"/></label>
	    </div>
    </div>