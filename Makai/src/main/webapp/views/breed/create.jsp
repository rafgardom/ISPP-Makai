<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<jstl:set var="lang" value="<%=LocaleContextHolder.getLocale()%>"/>

<form:form  action="${RequestURI}" modelAttribute="breed">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<jstl:if test="${errorMessage != null}">
		<acme:error code="${errorMessage}"/>
	</jstl:if>
	
	<div class="row">
		<div class="col-md-6">
			<jstl:if test="${lang == 'es'}">
				<acme:select items="${species }" itemLabel="typeSpa" code="breed.specie" path="specie"  mandatory="true" />
			</jstl:if>
			<jstl:if test="${lang == 'en'}">
				<acme:select items="${species }" itemLabel="typeEng" code="breed.specie" path="specie"  mandatory="true" />
			</jstl:if>
			<acme:input code="breed.nameSpa" path="nameSpa" mandatory="true" />
			<acme:input code="breed.nameEng" path="nameEng" mandatory="true" />
		</div>
	</div>
	
	<acme:submit name="save" code="breed.save" />
	<acme:cancel url="breed/admin/list.do" code="breed.cancel"/>
</form:form>
