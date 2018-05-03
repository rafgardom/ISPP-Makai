<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div id="conditions">

<div class="card">
	
	<h4 class="card-title" style="margin-left:2%;"><spring:message code="conditions.title" /></h4>
	<p class="card-body px-5"><spring:message code="conditions.paragraph1" /></p>
	<p class="card-body px-5"><spring:message code="conditions.paragraph2" /></p>
	<p class="card-body px-5"><spring:message code="conditions.paragraph3" /></p>
	
	<ul class="mx-5">
		<li ><spring:message code="conditions.list1" /></li>
		<li ><spring:message code="conditions.list2" /></li>
		<li ><spring:message code="conditions.list3" /></li>
		<li ><spring:message code="conditions.list4" /></li>
	</ul>
	
	<p class="card-body px-5"><spring:message code="conditions.paragraph4" /></p>
	<p class="card-body px-5"><spring:message code="conditions.paragraph5" /></p>

</div>
</div>
