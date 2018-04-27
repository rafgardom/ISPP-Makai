<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div id="privacyPolicy">

<div class="card">
	<div class="card-body p-5">
	<p><spring:message code="privacyPolicy.info" /></p>
	</div>
	
	<h4 class="card-title" style="margin-left:2%;"><spring:message code="privacyPolicy.object.title" /></h4>
	<p class="card-body px-5"><spring:message code="privacyPolicy.object.1" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.object.2" /></p>

	<h4 class="card-title" style="margin-left:2%;"><spring:message code="privacyPolicy.useTerms.title" /></h4>
	<p class="card-body px-5"><spring:message code="privacyPolicy.useTerms.veracity" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.useTerms.veracity2" /></p>
		<p class="card-body px-5"><spring:message code="privacyPolicy.legalPurposes" /></p>
		<ul class="mx-5">
			<li ><spring:message code="privacyPolicy.legalPurposes.1" /></li>
			<li ><spring:message code="privacyPolicy.legalPurposes.2" /></li>
			<li><spring:message code="privacyPolicy.legalPurposes.3" /></li>
			<li><spring:message code="privacyPolicy.legalPurposes.4" /></li>
			<li><spring:message code="privacyPolicy.legalPurposes.5" /></li>
			<li><spring:message code="privacyPolicy.legalPurposes.6" /></li>
			<li><spring:message code="privacyPolicy.legalPurposes.7" /></li>
			<li><spring:message code="privacyPolicy.legalPurposes.8" /></li>
		</ul>
	<p class="card-body px-5"><spring:message code="privacyPolicy.useTerms.links" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.useTerms.damages1" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.useTerms.damages2" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.useTerms.damages3" /></p>
	
	<h4 class="card-title" style="margin-left:2%;"><spring:message code="privacyPolicy.dataProtection.title" /></h4>
	<p class="card-body px-5"><spring:message code="privacyPolicy.dataProtection.1" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.dataProtection.2" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.dataProtection.3" /></p>
	<p class="card-body px-5"><spring:message code="privacyPolicy.dataProtection.4" /></p>
	
	<jstl:if test="${sc }">
		<h4 class="card-title" style="margin-left:2%;"><spring:message code="privacyPolicy.useCookies.title" /></h4>
		<p class="card-body px-5" id="prueba"><spring:message code="privacyPolicy.useCookies" />&nbsp;<a href="misc/cookiesPolicy.do"><spring:message code="master.page.here" /></a>.</p>
	</jstl:if>
	
	<h4 class="card-title" style="margin-left:2%;"><spring:message code="privacyPolicy.legislation.title" /></h4>
	<p class="card-body px-5"><spring:message code="privacyPolicy.legislation" /></p>
</div>
</div>
