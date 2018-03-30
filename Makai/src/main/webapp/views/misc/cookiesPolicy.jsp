<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort }${pageContext.request.contextPath }" var="url" />

<p>
	<spring:message code="cookiesPolicy.domain" />
	<jstl:out value="${url }" />
	<spring:message code="cookiesPolicy.info" />
<p>

<h4 style="margin-left:2%;"><spring:message code="cookiesPolicy.cookies" /></h4>
<p><spring:message code="cookiesPolicy.cookies.definition1" /><p>
<p><spring:message code="cookiesPolicy.cookies.definition2" /><p>

<h4 style="margin-left:2%;"><spring:message code="cookiesPolicy.type" /></h4>
<ul>
  <li><spring:message code="cookiesPolicy.type.definition" /></li>
</ul>

<h4 style="margin-left:2%;"><spring:message code="cookiesPolicy.consent" /></h4>

<p><spring:message code="cookiesPolicy.config" /><p>

<p><spring:message code="cookiesPolicy.acceptance" /><p>

