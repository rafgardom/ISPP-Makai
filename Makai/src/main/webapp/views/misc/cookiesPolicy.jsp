<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:set value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort }${pageContext.request.contextPath }" var="url" />

<div class="card">
	<p class="card-body p-5">
		<spring:message code="cookiesPolicy.domain" />
		<jstl:out value="${url }" />
		<spring:message code="cookiesPolicy.info" />
	</p>
</div>
<div class="card mt-3">	
	<h2 class="card-title pt-5" style="margin-left:2%;"><spring:message code="cookiesPolicy.cookies" /></h2>
	<p class="card-body px-5"><spring:message code="cookiesPolicy.cookies.definition1" /></p>	
	<p class="card-body px-5"><spring:message code="cookiesPolicy.cookies.definition2" /></p>

	<h4 class="card-title pt-5" style="margin-left:2%;"><spring:message code="cookiesPolicy.type" /></h4>
	<p class="card-body px-5"><spring:message code="cookiesPolicy.type.definition" /></p>

</div>	
<div class="card mt-3">	
	<h2 class="card-title pt-5" style="margin-left:2%;"><spring:message code="cookiesPolicy.consent" /></h2>
	<p class="card-body px-5"><spring:message code="cookiesPolicy.config" /></p>
	<p  class="card-body px-5"><spring:message code="cookiesPolicy.acceptance" /></p>
</div>