<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- OFFER -->
<div class="row">
	<div class="col-md-4">
	<div class="card">
		<h2 class="card-header"><spring:message code="master.page.offer" /></h2>
		<h4 class="card-title pt-3"><spring:message code="faq.q1.offer" /></h4>
		<p  class="card-body"><spring:message code="faq.a1.offer" /></p>
		
		<h4 class="card-title"><spring:message code="faq.q2.offer" /></h4>
		<p  class="card-body"><spring:message code="faq.a2.offer" /></p>
	</div>
	</div>
	
	<div class="col-md-4">
	<div class="card">
		<!-- REQUEST -->
		<h2 class="card-header"><spring:message code="master.page.request" /></h2>
		<h4 class="card-title pt-3"><spring:message code="faq.q3.request" /></h4>
		<p class="card-body"><spring:message code="faq.a3.request" /></p>
		
		<h4 class="card-title"><spring:message code="faq.q4.request" /></h4>
		<p class="card-body"><spring:message code="faq.a4.request" /></p>
	</div>
	</div>
	
	<div class="col-md-4">
	<div class="card">
	<!-- TRAVEL -->
		<h2 class="card-header"><spring:message code="master.page.travel" /></h2>
		<h4 class="card-title pt-3"><spring:message code="faq.q5.travel" /></h4>
		<p class="card-body"><spring:message code="faq.a5.travel" /></p>
		
		<h4 class="card-title"><spring:message code="faq.q6.travel" /></h4>
		<p class="card-body"><spring:message code="faq.a6.travel" /></p>
	</div>
	</div>
	
</div>